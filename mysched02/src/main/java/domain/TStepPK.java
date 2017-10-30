package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public final class TStepPK implements Serializable {
	private static final long serialVersionUID = -684112285455122172L;
	
	@Column(name="tcid")
	private Long tcid;
	
	@Column
	private Integer stepNum;
	
	public TStepPK(){
		
	}
	public TStepPK(Long tcid, Integer stepNum){
		this.tcid = tcid;
		this.stepNum = stepNum;
	}
	public Long getTcid() {
		return tcid;
	}
	public void setTcid(Long tcid) {
		this.tcid = tcid;
	}
	public Integer getStepNum() {
		return stepNum;
	}
	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stepNum == null) ? 0 : stepNum.hashCode());
		result = prime * result + ((tcid == null) ? 0 : tcid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TStepPK other = (TStepPK) obj;
		if (stepNum == null) {
			if (other.stepNum != null)
				return false;
		} else if (!stepNum.equals(other.stepNum))
			return false;
		if (tcid == null) {
			if (other.tcid != null)
				return false;
		} else if (!tcid.equals(other.tcid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TStepPK [tcid=" + tcid + ", stepNum=" + stepNum + "]";
	}
	
	
}
