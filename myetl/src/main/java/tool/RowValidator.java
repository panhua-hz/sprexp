package tool;

public interface RowValidator <T>{
	boolean vbIgnore(T row);  
	boolean vbColCount(T row);
	boolean vbNotNull(T row);
	boolean vbDataType(T row);	
}
