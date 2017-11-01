package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="wd_envs")
public class WebDriverEnv {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String url;
	
	@Column
	private String type;
	
	public WebDriverEnv(){
		
	}
	public WebDriverEnv(String url, String type) {
		this.url = url;
		this.type = type;
	}
	public WebDriverEnv(Long id, String url, String type) {
		this.id = id;
		this.url = url;
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "WebDriverEnv [id=" + id + ", url=" + url + ", type=" + type + "]";
	}
}
