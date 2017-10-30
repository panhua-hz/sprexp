package domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name="tst_step")
public class TStep {
	
	@EmbeddedId
	private TStepPK pk;
	
	@Column
	private String command;
	
	@Column
	private String target;
	
	@Column
	private String value;
	
	public TStep(){
		
	}

	public TStep(Long tcid, Integer stepNum, String command, String target, String value) {
		TStepPK pk = new TStepPK(tcid, stepNum);
		this.pk = pk;
		this.command = command;
		this.target = target;
		this.value = value;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getStepNo(){
		return pk.getStepNum();
	}
	@Override
	public String toString() {
		return "TStep [tcid=" + pk.getTcid() + ", stepNum=" + pk.getStepNum() + ", command=" + command + ", target=" + target
				+ ", value=" + value + "]";
	}
	
}
