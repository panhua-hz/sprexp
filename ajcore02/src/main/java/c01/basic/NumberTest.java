package c01.basic;

import java.math.BigDecimal;

import org.junit.Test;

public class NumberTest {
	@Test
	public void numberLiteralTest(){
		//Will convert to decimal:
		
        System.out.println(4000000000L); // long literal
        System.out.println(0xCAFEBABE); // hex literal
        System.out.println(0b1001); // binary literal
        System.out.println(011); // octal literal
        
        // Underscores in literals   
        System.out.println(1_000_000_000); 
        System.out.println(0b1111_0100_0010_0100_0000);
        
        System.out.println(0x1.0p-3); // hex double literal
	}
	
	@Test
	public void unsignedTest(){
		System.out.println(Byte.toUnsignedInt((byte )-127)); //129
		System.out.println(Byte.toUnsignedInt((byte )-1));	 //255
		System.out.println(Byte.toUnsignedInt((byte )0 ));
		System.out.println(Byte.toUnsignedInt((byte )80 ));
	}
	
	@Test
	public void infinityNanTest(){
		System.out.println(1.0 / 0.0); //Infinity
        System.out.println(-1.0 / 0.0); //-Infinity
        System.out.println(0.0 / 0.0); //NaN
        
        System.out.println(1.0 / 0.0 == Double.POSITIVE_INFINITY); //true
        System.out.println(-1.0 / 0.0 == Double.NEGATIVE_INFINITY);	//true
        System.out.println(0.0 / 0.0 == Double.NaN);	//false

        System.out.println(Double.isInfinite(1.0 / 0.0)); //true
        System.out.println(Double.isNaN(0.0 / 0.0));	//true
        System.out.println(Double.isFinite(0.0 / 0.0));	//false
	}
	
	@Test
	public void roundTest(){
		double big = 2.0;
		double small = 1.1;
		double diff = big-small;
		
		System.out.println(diff); //0.8999999999999999
		
		String bigStr = Double.toString(big);
		String smallStr = Double.toString(small);
		System.out.println(bigStr);
		System.out.println(smallStr);
		
		BigDecimal d1 = new BigDecimal(bigStr);
		BigDecimal d2 = new BigDecimal(smallStr);
		
		BigDecimal d3 = d1.subtract(d2);
		System.out.println(d3); //0.9
	}
	
	@Test
	public void charTest(){
		System.out.println('J' == 74); 	//true
        System.out.println('\u004A'); 	//J
        System.out.println('\u263A');	//:)
	}
	
	@Test
	public void modTest(){
		System.out.println(-17 / 5);
        System.out.println(-17 % 5);
        System.out.println(Math.floorMod(-17, 5));
	}
}
