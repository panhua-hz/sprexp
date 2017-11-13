package service;

public interface QBaseService <T> {
	public void start();
	public void shutdown();
	public void doService(T datavo);
}
