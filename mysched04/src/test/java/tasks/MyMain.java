package tasks;

import org.testng.TestNG;

public class MyMain {

	public static void main(String[] args) {
		//String[] argss = new String[]{"D:/myproject/stsgrd/sprexp/mysched/src/test/java/tasks/mytestng.xml"};
		String[] argss = new String[]{"D:/myproject/stsgrd/sprexp/mysched/src/main/java/tasks/baidutest.xml"};
		TestNG.main(argss);
		//TestNG testng = TestNG.privateMain(new String[]{"D:/myproject/stsgrd/sprexp/mysched/src/test/java/tasks/mytestng.xml"}, null);
		//System.out.println("Return result: "+testng.getStatus());
	}

}
