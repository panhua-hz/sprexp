package tool;

import java.util.Arrays;

public class PrintRowPublish implements RowPublisher {

	
	@Override
	public void publish(long lineNo, String line, String[] row) {
		System.out.println(lineNo+": "+line);
		System.out.println(Arrays.toString(row));

	}

}
