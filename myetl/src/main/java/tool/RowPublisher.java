package tool;

public interface RowPublisher {
	void publish(long lineNo, String line, String[] row);
	//<T> void publish(long lineNo, T valueobj);
}
