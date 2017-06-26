package jvcore;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class AnnMain {

	public static void main(String[] args) {
		doTaskTest();
	}
	
	private static void test1(){
		AJob job = new AJobImpl();
		AutoLog.writeLog(job);
	}
	
	private static void doTaskTest(){
		AJob orginal = new AJobImpl();
		AJob job = (AJob)(Proxy.newProxyInstance(
				JVCoreMain1.class.getClassLoader(), 
				orginal.getClass().getInterfaces(), 
				(proxy, m, margs)->{
					System.out.println("Proxy call:");
					Method om = orginal.getClass().getMethod(m.getName(), m.getParameterTypes());
					ToLog tolog = om.getAnnotation(ToLog.class);
					if (tolog != null){
						System.out.println(tolog.level()+": "+m.getName()+Arrays.toString(margs));
					}
					return m.invoke(orginal, margs);}));
		job.doTask("sayhi");
		job.saySomething("ggg");
	}

}
