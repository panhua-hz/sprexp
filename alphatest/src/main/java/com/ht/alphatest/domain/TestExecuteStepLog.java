package com.ht.alphatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="test_execute_step_log")
public class TestExecuteStepLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="execute_step_id")
	private Long executeStepId;
	
	@Column(name="execute_id")
	private Long executeId;
	
	@Column(name="round_data_id")
	private Long roundDataId;
	
	@Column(name="snapshot_location")
	private String snapShotLocation;
	
	@Column(name="step_log")
	private String stepLog;

	public TestExecuteStepLog() {
		
	}
	public TestExecuteStepLog(Long executeId, Long roundDataId, String snapShotLocation,
			String stepLog) {
		this.executeId = executeId;
		this.roundDataId = roundDataId;
		this.snapShotLocation = snapShotLocation;
		this.stepLog = stepLog;
	}
	public TestExecuteStepLog(Long executeStepId, Long executeId, Long roundDataId, String snapShotLocation,
			String stepLog) {
		this.executeStepId = executeStepId;
		this.executeId = executeId;
		this.roundDataId = roundDataId;
		this.snapShotLocation = snapShotLocation;
		this.stepLog = stepLog;
	}
	public Long getExecuteStepId() {
		return executeStepId;
	}
	public void setExecuteStepId(Long executeStepId) {
		this.executeStepId = executeStepId;
	}
	public Long getExecuteId() {
		return executeId;
	}
	public void setExecuteId(Long executeId) {
		this.executeId = executeId;
	}
	public Long getRoundDataId() {
		return roundDataId;
	}
	public void setRoundDataId(Long roundDataId) {
		this.roundDataId = roundDataId;
	}
	public String getSnapShotLocation() {
		return snapShotLocation;
	}
	public void setSnapShotLocation(String snapShotLocation) {
		this.snapShotLocation = snapShotLocation;
	}
	public String getStepLog() {
		return stepLog;
	}
	public void setStepLog(String stepLog) {
		this.stepLog = stepLog;
	}
	
}
