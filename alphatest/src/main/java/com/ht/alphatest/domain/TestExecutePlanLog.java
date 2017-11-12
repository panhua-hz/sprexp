package com.ht.alphatest.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="test_execute_plan_log")
public class TestExecutePlanLog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="execute_plan_id")
	private Long executePlanId;
	
	@Column(name="plan_id")
	private Long planId;
	
	@Column(name="plan_name_stamp")
	private String planNameStamp;
	
	@Column(name="start_time")
	private Date startTime;
	
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="status")
	private String status;
	
	@Column(name="execute_summary_log")
	private String executeSummaryLog;

	public TestExecutePlanLog() {
		
	}
	public TestExecutePlanLog(Long planId, String planNameStamp, Date startTime,
			String status, String executeSummaryLog) {
		this.planId = planId;
		this.planNameStamp = planNameStamp;
		this.startTime = startTime;
		this.status = status;
		this.executeSummaryLog = executeSummaryLog;
	}

	public TestExecutePlanLog(Long planId, String planNameStamp, Date startTime, Date endTime,
			String status, String executeSummaryLog) {
		this.planId = planId;
		this.planNameStamp = planNameStamp;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.executeSummaryLog = executeSummaryLog;
	}
	public TestExecutePlanLog(Long executePlanId, Long planId, String planNameStamp, Date startTime, Date endTime,
			String status, String executeSummaryLog) {
		super();
		this.executePlanId = executePlanId;
		this.planId = planId;
		this.planNameStamp = planNameStamp;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.executeSummaryLog = executeSummaryLog;
	}
	public Long getExecutePlanId() {
		return executePlanId;
	}
	public void setExecutePlanId(Long executePlanId) {
		this.executePlanId = executePlanId;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public String getPlanNameStamp() {
		return planNameStamp;
	}
	public void setPlanNameStamp(String planNameStamp) {
		this.planNameStamp = planNameStamp;
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
	public String getExecuteSummaryLog() {
		return executeSummaryLog;
	}
	public void setExecuteSummaryLog(String executeSummaryLog) {
		this.executeSummaryLog = executeSummaryLog;
	}
	
	
}
