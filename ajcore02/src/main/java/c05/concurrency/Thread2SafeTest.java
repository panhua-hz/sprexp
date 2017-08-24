package c05.concurrency;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class Thread2SafeTest {
	/*
	 * 线程安全的数据结构
	 */
	/*
	 * 安全并发的策略:
	 * --a. 限制/隔离: 不共享;
	 * --b. 必须共享的情况下保证:
	 * ----1) 可见性;
	 * ------final变量初始化后可见
	 * ------static变量初始化后可见(但改变不一定可见)
	 * ------volatile使得改变可见
	 * ------lock对于请求同一个锁的不同线程,更新对象可见
	 * ----2) 原子性;
	 * --可以使用如下方法：
	 * ----1) 共享变量为不可变类;
	 * ------不可变类对象要求:
	 * ------1)成员变量声明为final或者不可泄露可变状态.
	 * ------2)类声明为final,不可继承.
	 * ------3)任何修改将设置得到新对象;
	 * ------4)构造方法里面的this引用要注意控制.(比较抽象,比如观察者模型+多线程,没研究,随便列在这里)
	 * ----2) 通过锁来保证可见与原子操作.包括乐观悲观锁.
	 */
	
	/*
	 * 3. volatile作用
	 * a. 变量修改的可见;
	 * b. 通过内存屏障,防止指令重排
	 * 如何使用?
	 * 由于可见并不表示原子操作
	 * a. 可以用在一个线程修改值,其他线程读取值的情形
	 *    大多读取值的情况是用在判断条件里: if/while/for(;;)
	 * b. 防止指令重排,
	 *    我猜想指令重排是把一个流程当作单线程来思考优化,而volatile可以防止这种单线程无害但多线程有害的优化.
	 */
	
	@Test
	public void singletonTest() throws InterruptedException{
		ExecutorService pool = Executors.newCachedThreadPool();
		Runnable r0 = ()->{
			long start = System.currentTimeMillis();
			String tn = Thread.currentThread().getName();
			int m=0;
			try {
				m = SingletonDCL.getInstance().getMember();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			long cost = end - start;
			System.out.println(tn+"~"+0+" Get value "+m+" cost "+cost+"ms");
		};
		pool.execute(r0);
		Thread.sleep(100);
		
		for (int i = 1; i < 1000; i++){
			int k = i;
			Runnable r = ()->{
				long start = System.currentTimeMillis();
				String tn = Thread.currentThread().getName();
				int m=0;
				try {
					m = SingletonDCL.getInstance().getMember();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long end = System.currentTimeMillis();
				long cost = end - start;
				System.out.println(tn+"~"+k+" Get value "+m+" cost "+cost+"ms");
			};
			
			pool.execute(r);
		}
		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.SECONDS);
	}
	
	private static volatile boolean done = false; 
	//private static boolean done = false; 
	@Test
	public void volatileTest() throws InterruptedException{
		ExecutorService pool = Executors.newCachedThreadPool();
		Runnable r1 = ()->{
			System.out.println("Start!");
			try {
				Thread.sleep(3000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			done = true;
			System.out.println("End!");
		};
		
		Runnable r2 = ()->{
			long i = 0;
			while (!done){
				i++;
			}
			System.out.println("accural: "+i);
		};
		pool.execute(r2);
		pool.execute(r1);
		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.SECONDS);
	}
	
	/*
	 * 乐观锁:
	 * 1. 非阻塞操作;
	 * 2. 需要CAS保证更新的原子性;
	 * 3. 注意: 需要循环重试来代替阻塞;
	 * 见AtomicLong.updateAndGet实现
	 * 缺点:
	 * 高并发太多重试也会降低性能.如果能够减少共享更佳.
	 * 比如LongAdder/LongAccumulator通过减少共享以提高效率.
	 * 我猜想:
	 * 对于并发包中的Concurrent开头的数据结构通常都有乐观锁来实现.
	 */
	
	/*
	 * 悲观锁:
	 * 使用锁来阻塞操作以保证唯一线程操作.比如lock或者同步应该都算.
	 * 缺点:
	 * 阻塞导致性能问题.
	 * 我猜想:
	 * 对于并发包里的Blocking之类的数据结构估计是用悲观锁实现的.
	 * 
	 */
///*************************************************************************************/	
	/*
	 * 线程安全的数据结构
	 */
	/*
	 * 1. ConcurrentHashMap
	 * Map的基本操作包括:
	 * 增: put/putAll/putIfAbsent
	 * 删: remove/clear
	 * 改: replace->replaceAll/computeIfAbsent/computeIfPresent/compute/merge
	 * 查: get/size/isEmpty/containsKey/containsValue/keySet/values/entrySet/getOrDefault
	 * 	
	 * 增强:
	 * size->mappingCount
	 * reduce/reduceEntries/reduceKeys/
	 * search/searchEntries/searchKeys/searchValues
	 * 
	 * 注意: 
	 * 对于获取操作,如size/isEmpty等等如果在多线程修改的情况下,可能得到的是不稳定的值可以当作近似值.
	 * 基于此, CHM可能适合多线程修改最后再汇总,当然汇总可以并行. 就是说写和读分开比较好.它们分别都可以多线程.
	 * 
	 * 注意: 对于value进行叠加的操作在多线程下需要考虑原子性问题.
	 * 1. 在循环中使用replace(原子)操作,对数值进行更新. 乐观锁, 多次重试;
	 * 2. value使用AtomicXX/XXAdder来保证叠加操作;如map.putIfAbsent(word, new LongAdder()).increment()
	 * 3. 函数式修改value, CHM会保证传人的函数被互斥访问,请确保不要通过函数修改map其他内容(除value外):
	 * --如:map.merge(word, 1L, (existv,inv)->existv+inv)
	 * 
	 * 利用CHM得到线程安全的Set,注意这个set是可以修改的:
	 * 有两种方式:
	 * 1. Set<String> words = ConcurrentHashMap.<String>newKeySet();
	 * 
	 * 2. 
	 * ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
	 * words = map.keySet(1L); //设置默认value值,注意CHM不接受null的value.
	 * 
	 */
	@Test
	public void chmTest() throws IOException, InterruptedException{
		int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        Path pathToRoot = Paths.get("tmp");
        System.out.println(pathToRoot.toAbsolutePath().toString());
        for (Path p : descendants(pathToRoot)) {
            executor.execute(() -> process(p));
        }        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println(map);
		
		
	}
	public static ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
    public static Set<Path> descendants(Path p) throws IOException {        
        try (Stream<Path> entries = Files.walk(p)) {
        	return entries.filter(t->Files.isRegularFile(t)&&t.toString().endsWith(".txt")).collect(Collectors.toSet());
            //return entries.filter(Files::isRegularFile).collect(Collectors.toSet());
        }
    }
    public static void process(Path path) {
        try {
            String contents = new String(Files.readAllBytes(path),
                StandardCharsets.UTF_8);
            for (String word : contents.split("\\PL+")) {
                map.merge(word, 1L, Long::sum);
                // or map.compute(word, (k, v) -> v == null ? 1 : v + 1);
                /* or
                map.putIfAbsent(word, 0L);
                Long oldValue, newValue;
                do {
                    oldValue = map.get(word);
                    newValue = oldValue + 1;
                } while (!map.replace(word, oldValue, newValue));                                
                */
                /* but not
                Long oldValue = map.get(word);
                Long newValue = oldValue == null ? 1 : oldValue + 1;
                map.put(word, newValue); // Error might not replace oldValue                                
                */
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	/*
	 * BlockingQueue
	 * 插入删除检查-->Exception/null/block/timeout&null
	 * add/remove/element->exception
	 * offer/poll/peek->null
	 * put/take->block
	 */
	public static BlockingQueue<String> blkq = new LinkedBlockingQueue<>(); //默认容量Integer.MAX_VALUE
	@Test
	public void prdcsmTest() throws IOException, InterruptedException{
		String endFlag = "~~end~~"; //用作结束标记
		Path file = Paths.get("tmp","alice.txt");
		String content = new String(Files.readAllBytes(file),StandardCharsets.UTF_8);
		String[] words = content.split("\\PL+");
		Runnable prd = ()->{
			for (int i = 0; i < words.length; i++){
				try {
					blkq.put(words[i]);
					Thread.sleep(1); //哪怕sleep一毫秒导致页面切换的时间也要多很多
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				blkq.put(endFlag);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
		
		Runnable csm = ()->{
			String word = null;
			for (;;){
				try {
					word = blkq.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (endFlag.equals(word)){
					break;
				}
				System.out.println(word);
			}
		};
		
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(csm);
		pool.execute(prd);
		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.MINUTES);
	}
	
	
}
