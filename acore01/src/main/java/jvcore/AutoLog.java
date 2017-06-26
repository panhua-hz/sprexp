package jvcore;

import java.lang.reflect.Method;

public class AutoLog {
	public static void writeLog(Object obj){
		if (obj==null){
			System.out.println("obj is null");
			return;
		}
		
		Class<?> cl = obj.getClass();
		for (Method m: cl.getMethods()){
			ToLog tolog = m.getAnnotation(ToLog.class);
			if (tolog != null){
				System.out.println(tolog.level()+": "+m.getName());
			}
		}
	}
}
