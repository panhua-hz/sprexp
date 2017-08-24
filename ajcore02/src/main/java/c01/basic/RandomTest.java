package c01.basic;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

public class RandomTest {
	@Test
	public void streamOrParallelTest(){
		Random generator = new Random();
		int[] rdsints = generator.ints(10, 1, 25).toArray();
		Arrays.stream(rdsints)
			.forEach(d->{
			Thread t = Thread.currentThread();
			System.out.println(t.getName()+": "+(d+1));
		});
		System.out.println("---------------------------");
		Arrays.stream(rdsints)
			.parallel()
			.forEach(d->{
			Thread t = Thread.currentThread();
			System.out.println(t.getName()+": "+(d+1));
		});
	}
}
