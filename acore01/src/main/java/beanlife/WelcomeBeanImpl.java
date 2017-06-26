package beanlife;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WelcomeBeanImpl implements WelcomeBean {

	String firstName;
	String lastName;
	public WelcomeBeanImpl(@Value("${fName}") String fName, @Value("${lName}") String lName){
		this.firstName = fName;
		this.lastName = lName;
	}
	@Override
	public void sayHello() {
		System.out.println(firstName+" "+lastName);
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
