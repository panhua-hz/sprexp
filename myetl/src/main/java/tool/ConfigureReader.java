package tool;

import java.util.ResourceBundle;

public class ConfigureReader {
	private static ResourceBundle resourceConfiger = ResourceBundle.getBundle("resource");
	public static String getResourceProperty(String key){
		return resourceConfiger.getString(key);
	}
	
	public static void main(String[] args){
		System.out.println(getResourceProperty("holdings.resource.local.home"));
	}
}
