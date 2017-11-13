package service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vo.RowVO;

public class HoldingsService implements QBaseService<RowVO> {
	
	private BlockingQueue<RowVO> messageQueue = new ArrayBlockingQueue<>(20000, true); //FIFO
	private ExecutorService executor = Executors.newCachedThreadPool();
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	private void batchMerge(){
		//get 5000 count or get end flag do batch insert
		//get merge lock //optimal lock
		//do merge
		//release lock.
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doService(RowVO datavo) {
		// TODO Auto-generated method stub
		
	}

}
