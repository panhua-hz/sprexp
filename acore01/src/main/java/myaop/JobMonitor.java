package myaop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import jvcore.ToLog;

@Component
@Aspect
public class JobMonitor {
	@Pointcut("execution(* myaop.MyJob.exeJob(..))")
	public void doJobPC(){}
	
	@Before("doJobPC()")
	public void doJobStart(){
		System.out.println("----You are start a job----");
	}
	
	@After("doJobPC()")
	public void doJobEnd(){
		System.out.println("----You are finished a job----");
	}
	
	@Pointcut("execution(String myaop.MyJob.exeJob(String)) && args(who)")
	public void doJobPC2(String who){}
	
	@Before("doJobPC2(who)")
	public void whoislogin(String who){
		System.out.println("----I know "+who+" is logined.");
	}
	
	//@Around("execution(* * (..)) && within(myaop.*) && @annotation(jvcore.ToLog)")
	public Object logAll(ProceedingJoinPoint jp){
		Object result = null;
		try {
			Object target = jp.getTarget(); //target instance
			System.out.println(target.getClass().getName()); 
			Signature sg = jp.getSignature(); //method signature
			System.out.println(sg);
			System.out.println("method: "+sg.getName());
			Object[] args = jp.getArgs();
			System.out.println("Parameters: "+Arrays.toString(args));
			result = jp.proceed();
			System.out.println("Returned: "+result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//@Around("execution(* * (..)) && within(myaop.*) && @annotation(tolog)")
	public Object logAll(ProceedingJoinPoint jp, ToLog tolog){
		Object result = null;
		try {
			Object target = jp.getTarget(); //target instance
			System.out.println(target.getClass().getName()); 
			Signature sg = jp.getSignature(); //method signature
			System.out.println(sg);
			System.out.println("method: "+sg.getName());
			Object[] args = jp.getArgs();
			System.out.println("Parameters: "+Arrays.toString(args));
			
			System.out.println("LogLevel: "+tolog.level());			
			result = jp.proceed();
			System.out.println("Returned: "+result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Around("execution(* * (..)) && within(myaop.*) && @annotation(tolog)")
	public Object logAll2(ProceedingJoinPoint jp, ToLog tolog){
		Object result = null;
		try {
			Object target = jp.getTarget(); //target instance
			System.out.println(target.getClass().getName()); 
			Signature sg = jp.getSignature(); //method signature
			System.out.println(sg);
			System.out.println("method: "+sg.getName());
			Object[] args = jp.getArgs();
			System.out.println("Parameters: "+Arrays.toString(args));
			
			System.out.println("LogLevel: "+tolog.level());			
			result = jp.proceed();
			System.out.println("Returned: "+result);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@DeclareParents(value="myaop.MyJob+", defaultImpl=DefaultEncoreable.class)
	public static Encoreable encoreable;
}
