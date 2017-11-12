package com.ht.alphatest.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="test_execute_log")
public class TestExecuteLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="execute_id")
	private Long executeId;
	
	@Column(name="execute_plan_id")
	private Long executePlanId;
	
	@Column(name="case_track_id")
	private Long caseTrackId;
	
	@Column(name="start_time")
	private Date startTime;
	
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="status")
	private String status;
	
	@Column(name="execute_log")
	private String executeLog;

	public TestExecuteLog() {
	}
	public TestExecuteLog(Long executePlanId, Long caseTrackId, Date startTime, Date endTime, String status,
			String executeLog) {
		this.executePlanId = executePlanId;
		this.caseTrackId = caseTrackId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.executeLog = executeLog;
	}
	public TestExecuteLog(Long executeId, Long executePlanId, Long caseTrackId, Date startTime, Date endTime, String status,
			String executeLog) {
		super();
		this.executeId = executeId;
		this.executePlanId = executePlanId;
		this.caseTrackId = caseTrackId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.executeLog = executeLog;
	}
	public Long getExecuteId() {
		return executeId;
	}
	public void setExecuteId(Long executeId) {
		this.executeId = executeId;
	}
	public Long getCaseTrackId() {
		return caseTrackId;
	}
	public void setCaseTrackId(Long caseTrackId) {
		this.caseTrackId = caseTrackId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExecuteLog() {
		return executeLog;
	}
	public void setExecuteLog(String executeLog) {
		this.executeLog = executeLog;
	}
	public Long getExecutePlanId() {
		return executePlanId;
	}
	public void setExecutePlanId(Long executePlanId) {
		this.executePlanId = executePlanId;
	}
	
	
}
