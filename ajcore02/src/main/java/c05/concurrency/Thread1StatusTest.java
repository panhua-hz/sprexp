package c05.concurrency;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/*
 * 注意junit test与main不一样,
 * junit线程完成后会直接退出java(jvm)的,这就导致所有子线程结束.
 * 如果使用main方法,虽然main结束了,但子线程依然可以执行,因为jvm还活着,直到所有线程结束jvm退出
 * 为了防止这种情况需要让junit主线程等待所有子线程完成再结束.
 * 可以通过这些手段: sleep/join/pool.awaitTermination.
 * 
 */
public class Thread1StatusTest {
	/*
	 * 1. 线程池和runnable
	 * */
	@Test
	public void runOnPoolTest() throws InterruptedException{
		Runnable r1 = ()->{
			for (int i = 0; i <= 2000; i++){
				System.out.println("Hello "+i);
			}
		};
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(r1); //
		pool.shutdown(); //这个方法不会立刻结束线程池,会执行完所有任务然后结束.
		pool.awaitTermination(60, TimeUnit.SECONDS);//让junit线程等待pool关闭时间,这个时间可以设置大一点
	}
	/*
	 * 2. Callable:
	 * a. 有返回值;
	 * b. 声明抛出Exception;
	 * c. 加入线程池用invokeAll/invokeAny/submit, 得到Future.
	 * d. Future get方法会等待线程返回值;阻塞
	 * e. cancel 试图取消,mayInterruptIfRunning=true表示会试图发一个中断信号到执行任务线程
	 * 	  cancel的返回值对于已经完成了的任务进行cancel,就返回false,否则基本上都是true.
	 */
	@Test
	public void callableTest() throws InterruptedException{
		Callable<String> c1 = ()->{
			//String threadName = Thread.currentThread().getName();
			System.out.println(threadname.get()+" starts!");
			for (int i = 0; i < 10000; i++){
				if (Thread.interrupted()){ //测试是否在执行时被打断,是就返回
					System.out.println(threadname.get()+" Interrupted during exe.");
					return "Interrupted during exe.";
				}
			}
			try{
				Thread.sleep(3000l); //sleep也可能被打断就抛出Exception
				System.out.println(threadname.get()+"ends done!");
				return "Done";
			}catch(InterruptedException e){
				System.out.println(threadname.get()+"ends Interrupted!");
				return "Interrupted";
			}
		};
		ExecutorService pool = Executors.newCachedThreadPool();
		Future<String> f1 = pool.submit(c1); //两个同样的Callable任务,返回的future是不同对象
		Future<String> f2 = pool.submit(c1);
		//Thread.sleep(100l);
		
		boolean isCancelled = f1.cancel(true); //尝试取消任务,会发送一个中断信号
		//boolean isCancelled = f1.cancel(false);//尝试取消任务,不会发送中断信号,除非任务没开始就取消,否则就不能取消了.
												 //只要cancel的时候任务没有完成,则认为取消,尽管任务依然在运行.
		System.out.println("f1Result isCancelled "+isCancelled+" at "+System.currentTimeMillis()+"ms");

		try {
			String f2Result = f2.get(); //阻塞获取结果或者Exception
			System.out.println("f2Result: "+f2Result+" at "+System.currentTimeMillis()+"ms");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.SECONDS);
	}
	/*
	 * 按完成顺序返回Future
	 */
	@Test
	public void callableGetInOrderTest2() throws IOException, InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newCachedThreadPool();
		ExecutorCompletionService<String> cmpPool = new ExecutorCompletionService<>(pool);
		int taskSize = 10;
		for (int i = 0; i < taskSize; i++){
			int k = i;
			cmpPool.submit(()->{
				System.out.println("task"+k+" has started!");
				Random rd = new Random();
				int x = rd.nextInt(10);
				long slptm = x*1000l;
				Thread.sleep(slptm); 
				String msg = "task"+k+" has slept for "+slptm;
				return msg;
			});
		}
		for (int i = 0; i < taskSize; i++){
			String result = cmpPool.take().get();//take会按顺序返回future!
			System.out.println(result);
		}
		pool.shutdown();
		pool.awaitTermination(60, TimeUnit.SECONDS);
	}
	
	@Test
	public void invokeAnyInterruptTest() throws InterruptedException, ExecutionException{
		ExecutorService executor = Executors.newCachedThreadPool();
        List<Callable<BigInteger>> tasks = new ArrayList<>();
        
        //得到10000000000~10000000100之间的随便一个素数
        for (int i = 0; i < 100; i++) {        
            BigInteger n = big("10000000000").add(big(i)); 
            tasks.add(() -> isPrime(n));
        }
        BigInteger result = executor.invokeAny(tasks); //会试图打断其他线程
        System.out.println(result + " is prime");
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
	}
	
	public static BigInteger big(long value) { return BigInteger.valueOf(value); }
    public static BigInteger big(String value) { return new BigInteger(value); }
    
    public static BigInteger isPrime(BigInteger n) {
        BigInteger m = n;
        BigInteger a = big(2);
        while (a.multiply(a).compareTo(m) <= 0) { 
            if (Thread.currentThread().isInterrupted()) {
                System.err.println("Interrupted!");
                return null;
            }
            if (m.remainder(a).equals(big(0))) 
                throw new RuntimeException();
            else
                a = a.add(big(1));            
        }
        return n;
    }
    
    /*
     * 定义ThreadLocal变量,
     * 使用xx.get()来获取变量的值
     */
    public static final ThreadLocal<String> threadname= ThreadLocal.withInitial(()->{
    	return Thread.currentThread().getName();
    });
    
    
	
}
