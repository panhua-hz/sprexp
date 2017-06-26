package beanlife;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;

public class HelloBean1Impl implements HelloBean1, BeanNameAware, BeanFactoryAware {

	private String loginUser;
	
	public HelloBean1Impl(String login){
		this.loginUser = login;
	}
	
	public String getLoginUser() {
		System.out.println("getLoginUser: "+loginUser);
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		System.out.println("setLoginUser: "+loginUser);
		this.loginUser = loginUser;
	}

	@Override
	public void sayHello() {
		System.out.println("Hello "+loginUser);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("setBeanFactory: "+beanFactory.getClass().getName());
		
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("setBeanName: "+name);
		
	}

	
}
