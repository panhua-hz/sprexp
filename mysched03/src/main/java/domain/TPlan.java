package domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="tst_plan")
public class TPlan {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false, targetEntity=TCase.class)
	private TCase tcase;
	
	@Column(name="run_once_at")
	private Date runOnceAt;
	
	@Column(name="repeatable_expression")
	private String repeatableCronExp;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false, targetEntity=WebDriverEnv.class)
	private WebDriverEnv targetEnv;
	
	public TPlan(){
		
	}
	public TPlan(TCase tcase, Date runOnceAt, WebDriverEnv targetEnv) {
		super();
		this.tcase = tcase;
		this.runOnceAt = runOnceAt;
		this.targetEnv = targetEnv;
	}
	public TPlan(TCase tcase, String repeatableCronExp, WebDriverEnv targetEnv) {
		super();
		this.tcase = tcase;
		this.repeatableCronExp = repeatableCronExp;
		this.targetEnv = targetEnv;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TCase getTcase() {
		return tcase;
	}
	public void setTcase(TCase tcase) {
		this.tcase = tcase;
	}
	public Date getRunOnceAt() {
		return runOnceAt;
	}
	public void setRunOnceAt(Date runOnceAt) {
		this.runOnceAt = runOnceAt;
	}
	public String getRepeatableCronExp() {
		return repeatableCronExp;
	}
	public void setRepeatableCronExp(String repeatableCronExp) {
		this.repeatableCronExp = repeatableCronExp;
	}
	public WebDriverEnv getTargetEnv() {
		return targetEnv;
	}
	public void setTargetEnv(WebDriverEnv targetEnv) {
		this.targetEnv = targetEnv;
	}
	@Override
	public String toString() {
		return "TPlan [id=" + id + ", tcase=" + tcase + ", runOnceAt=" + runOnceAt + ", repeatableCronExp="
				+ repeatableCronExp + ", targetEnv=" + targetEnv + "]";
	}
}
