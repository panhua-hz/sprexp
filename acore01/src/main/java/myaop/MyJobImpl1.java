package myaop;

import org.springframework.stereotype.Component;

import jvcore.ToLog;

@Component
public class MyJobImpl1 implements MyJob {

	@Override
	public void exeJob() {
		System.out.println("exeJob1 with no paramter");
	}

	@Override
	public String exeJob(String param) {
		System.out.println("hello "+param);
		return param;
	}

	@Override
	public String exeJob2(String param) {
		System.out.println("hello2 "+param);
		return param;
	}

	@ToLog
	@Override
	public String exeAnnJob3(String firstName, String lastName) {
		System.out.println("Welcome "+firstName+" . "+lastName);
		return "success";
	}

}
