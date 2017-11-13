package service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintService {
	private BlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(1000, true);
	private ExecutorService executor = Executors.newCachedThreadPool();
	public void start(){
		executor.execute(()->{
			for (;true;){
				String message;
				try {
					message = this.messageQueue.take();
					if ("ShutDown".equalsIgnoreCase(message)){
						Thread.sleep(3000);
						System.out.println("Ok, let me finished the work.");
						break;
					}
					System.out.println("Consumed!"+message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
	public void shutdown(){
		try {
			this.messageQueue.put("ShutDown");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}
	public void doService(String message){
		try {
			messageQueue.put(message);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
