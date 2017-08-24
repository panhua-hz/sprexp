package c05.concurrency;

public class SingletonDCL {
	/*
	 * 使用了volatile变量后，就能保证先行发生关系（happens-before relationship）。
	 * 对于volatile变量_instance，所有的写（write）都将先行发生于读（read）
	 */
	private static volatile SingletonDCL instance;
	//private static SingletonDCL instance;
	private int member;
	private SingletonDCL(){
		String tn = Thread.currentThread().getName();
		System.out.println(tn+" Start to new singleton!");
		member = 1;
		try {
			Thread.sleep(5000l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		member = 2;
		System.out.println(tn+" End to new singleton!");
	}
	public int getMember(){
		return member;
	}
	public static SingletonDCL getInstance() throws InterruptedException{
		String tn = Thread.currentThread().getName();
		if (instance == null){	//volatile是为了保证这里吗?测试过好像没有什么用.
								//难道需要多核cpu?才能有问题?
			System.out.println(tn+" find instance is null.");
			//Thread.sleep(1000l);
			synchronized(SingletonDCL.class){
				if (instance == null){
					//Thread.sleep(1000l);
					instance = new SingletonDCL();
				}
			}
		}
		return instance;
	}
}
