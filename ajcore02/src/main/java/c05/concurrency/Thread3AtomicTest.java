package c05.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class Thread3AtomicTest {
	public static AtomicLong count1 = new AtomicLong();
	
	@Test
	public void atomicAccumulateTest() throws InterruptedException{
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 1000; i++) {
            //int taskId = i;
            Runnable task = () -> {
                for (int k = 1; k <= 100000; k++)
                    count1.incrementAndGet(); //原子加1
                //System.out.println(taskId + ": " + count1);
            };
            executor.execute(task);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("Final value: " + count1);//1000*10000
        long end = System.currentTimeMillis();
        System.out.println("cost: "+(end-start));
	}
	public static LongAdder count2 = new LongAdder();
	@Test
	public void addAccumulateTest() throws InterruptedException{
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 1000; i++) {
            //int taskId = i;
            Runnable task = () -> {
                for (int k = 1; k <= 100000; k++)
                    count2.increment(); //分片+1,先不合并 比上面效率高
                //System.out.println(taskId + ": " + count);
            };
            executor.execute(task);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("Final value: " + count2);//1000*10000 //最终合并
        long end = System.currentTimeMillis();
        System.out.println("cost: "+(end-start));
	}
	
	@Test
	public void accumulatorFunctionTest(){
		//函数式更新
		LongAccumulator largest = new LongAccumulator(Long::max, 0);
        largest.accumulate(42L); //update with given value
        largest.accumulate(23l);
        long max = largest.get();
        System.out.println(max);
	}
	@Test
	public void safeValueInCHM(){
		//注意CHM值更新时候的竞争情况.
		ConcurrentHashMap<String,LongAdder> counts = new ConcurrentHashMap<>();
        for (String key : "Row, row, row a boat".split("\\PL+"))
            counts.computeIfAbsent(key, k -> new LongAdder()).increment();
        System.out.println(counts);
	}
	
	public static int count;
    public static Lock countLock = new ReentrantLock();	
	@Test
	public void lockTest() throws InterruptedException{
		ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 100; i++) {
            Runnable task = () -> {
                for (int k = 1; k <= 1000; k++) {
                    countLock.lock();
                    try {
                        count++; // Critical section
                    } finally {
                        countLock.unlock(); // Make sure the lock is unlocked
                    }                    
                }
            };
            executor.execute(task);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println("Final value: " + count);
	}
	
	/*
	 * 通过不同方式进行累加,并得到累加结果以及累加所花费的时间.
	 * 对于高并发情况下:
	 * 速度: LongAdder>AtomicLong>Synchronized
	 * unsynchronized结果不正确,最快,但没有意义
	 * 
	 * CyclicBarrier是一个同步屏障.
	 * 构造函数定义了同步的线程数量;
	 * 当所有调用同一个cb,到达时,通过计数器=线程数量,就可以继续下去
	 */
	@Test
	public void costOfAccumularTest(){
		final int THREADS = 100;
	      final int ITERATIONS = 1_000_000;      

	      System.out.println("Synchronized");
	      
	      class Counter {
	         private long count;
	         synchronized void increment() { count++; }
	         synchronized long get() { return count; }
	      };

	      Counter counter = new Counter();

	      double elapsedTime = run(THREADS, ITERATIONS, () -> {
	            counter.increment();
	         });
	         
	      System.out.println(counter.get());
	      System.out.println(elapsedTime + " seconds");

	      System.out.println("AtomicLong");

	      AtomicLong atomic = new AtomicLong();
	      elapsedTime = run(THREADS, ITERATIONS, () -> {
	            atomic.incrementAndGet();
	         });
	         
	      System.out.println(atomic.get());
	      System.out.println(elapsedTime + " seconds");

	      System.out.println("LongAdder");

	      LongAdder adder = new LongAdder();
	      elapsedTime = run(THREADS, ITERATIONS, () -> {
	            adder.increment();
	         });
	         
	      System.out.println(adder.sum());
	      System.out.println(elapsedTime + " seconds");     

	      System.out.println("Unsynchronized");

	      long[] badCounter = new long[1];

	      elapsedTime = run(THREADS, ITERATIONS, () -> {
	            badCounter[0]++;
	         });
	         
	      System.out.println(badCounter[0]);
	      System.out.println(elapsedTime + " seconds");
	}
	
	private static void await(CyclicBarrier barrier) {
	      try {
	         barrier.await();
	      } catch (InterruptedException | BrokenBarrierException ex) {
	         ex.printStackTrace();
	         // Won't happen in this application
	      }
	   }

	   public static double run(int nthreads, int iterations, Runnable action) {      
	      Thread[] threads = new Thread[nthreads];      
	      CyclicBarrier barrier = new CyclicBarrier(nthreads + 1);

	      for (int t = 0; t < nthreads; t++) {
	         threads[t] = new Thread(() -> {
	               await(barrier);
	               for (int i = 0;  i < iterations; i++) {
	                  action.run();
	               }
	               await(barrier);
	            });
	         threads[t].start();
	      }
	      await(barrier);
	      long start = System.nanoTime();
	      await(barrier);
	      long end = System.nanoTime();
	      return (end - start) * 1E-9;
	   }
	   
	   
	   
}
