package jvcore;

public interface AJob {
	String doTask(String taskName);
	
	default void saySomething(String hi){
		System.out.println("hello "+hi);
	};
}
