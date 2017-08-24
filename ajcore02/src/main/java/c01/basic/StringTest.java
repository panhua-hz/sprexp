package c01.basic;

import java.util.Arrays;

import org.junit.Test;

public class StringTest {
	@Test
	public void joinTest(){
		String names = String.join(", ", "Peter", "Paul", "Mary");
        System.out.println(names); //Peter, Paul, Mary
	}
	@Test
	public void strNumConvertTest(){
		int n = 42;
		String str = Integer.toString(n); 
		System.out.println(str); //42
		str = Integer.toString(n, 2); 
		System.out.println(str); //101010
		
		int m = Integer.parseInt(str, 2);
		System.out.println(m); //101010
	}
	
	@Test
	public void unicodeTest(){
		// Unicode
        String javatm = "Java\u2122";
        System.out.println(javatm);
        System.out.println(Arrays.toString(javatm.codePoints().toArray()));
        System.out.println(javatm.length());
        
        String octonions = "\ud835\udd46";
        System.out.println(octonions);
        System.out.println(Arrays.toString(octonions.codePoints().toArray()));
        System.out.println(octonions.length()); // Counts code units, not Unicode code points
	}
}
