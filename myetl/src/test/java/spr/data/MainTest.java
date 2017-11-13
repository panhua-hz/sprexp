package spr.data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import db.dao.LockerDao;

public class MainTest {

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		//List<Runnable> jobs = new ArrayList<>();
		for (int i=0; i<100; i++){
			int k=i;
			Runnable r = ()->{
				String tn = Thread.currentThread().getName()+"_"+k;
				System.out.println(tn+" is running.");
				LockerDao ld = new LockerDao();
				if (ld.acquireKey("load_file1", tn)){
					System.out.println("Successful get key for: "+tn);
				}
			};
			executor.execute(r);
		}
		executor.shutdown();		
	}

}
