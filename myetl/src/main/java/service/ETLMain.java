package service;

public class ETLMain {

	public static void main(String[] args) {
		System.out.println("Main started!");
		PrintService printService = new PrintService();
		printService.start();
		
		printService.doService("Hi1");
		printService.doService("Hi2");
		printService.doService("Hi3");
		printService.doService("Hi4");
		printService.doService("Hi5");
		printService.doService("Hi6");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		printService.doService("Hello1");
		printService.doService("Hello2");
		printService.doService("Hello3");
		printService.doService("Hello4");
		printService.doService("Hello5");
		printService.doService("Hello6");
		
		printService.shutdown();
		System.out.println("Main done!");
	}

}
