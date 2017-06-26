package jvcore;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JVCoreMain1 {

	public static void main(String[] args) {
		AJobImpl orginal = new AJobImpl();
		AJob job = (AJob)(Proxy.newProxyInstance(
				JVCoreMain1.class.getClassLoader(), 
				orginal.getClass().getInterfaces(), 
				(proxy, m, margs)->{
					System.out.println("call "+m.getName()+Arrays.toString(margs));
					return m.invoke(orginal, margs);}));
		job.doTask("sayhi");
	}

}
