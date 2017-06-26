package jvcore;

public class AJobImpl implements AJob {

	@Override
	@ToLog
	public String doTask(String taskName) {
		System.out.println("Do task "+taskName);
		return "success";
	}

}
