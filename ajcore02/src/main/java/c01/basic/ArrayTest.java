package c01.basic;

import java.util.Arrays;

import org.junit.Test;

public class ArrayTest {
	@Test
	public void arrayCpTest(){
		//Arrays.copyOf
		//Arrays.toString
		String[] nameList = new String[]{"Andrew","Andy","Mike","Jack", "Aron","Bob"};
		String[] nameCpList = Arrays.copyOf(nameList, 3);
		nameList[1] = "Bill";
		System.out.println(Arrays.toString(nameList));
		System.out.println(Arrays.toString(nameCpList));
	}
	
	
}
