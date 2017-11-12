package com.ht.alphatest.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="test_plan")
public class TestPlan {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="plan_id")
	private Long planId;
	
	@Column(name="plan_name")
	private String planName;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false, targetEntity=TargetEnv.class)
	@JoinColumn(name="env_id", nullable=false, updatable=false)
	private TargetEnv targetEnv;
	
	@Column(name="schedule_config")
	private String scheduleConfig;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false, targetEntity=Project.class)
	@JoinColumn(name="project_id", nullable=false, updatable=false)
	private Project project;
	
	@Column(name="platform")
	private String platform;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=TestCase.class)
	@JoinTable(name="test_plan_suite", joinColumns=
            @JoinColumn(name="plan_id", referencedColumnName="plan_id"),
        inverseJoinColumns=
            @JoinColumn(name="case_id", referencedColumnName="case_id"))
	private List<TestCase> testcases;

	public TestPlan() {
		
	}
	public TestPlan(String planName, TargetEnv targetEnv, String scheduleConfig, Project project,
			String platform, String createdBy, Date createdAt,
			List<TestCase> testcases) {
		this.planName = planName;
		this.targetEnv = targetEnv;
		this.scheduleConfig = scheduleConfig;
		this.project = project;
		this.platform = platform;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.testcases = testcases;
	}
	
	public TestPlan(String planName, TargetEnv targetEnv, String scheduleConfig, Project project,
			String platform, String createdBy, Date createdAt, String updatedBy, Date updatedAt,
			List<TestCase> testcases) {
		this.planName = planName;
		this.targetEnv = targetEnv;
		this.scheduleConfig = scheduleConfig;
		this.project = project;
		this.platform = platform;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.testcases = testcases;
	}
	public TestPlan(Long planId, String planName, TargetEnv targetEnv, String scheduleConfig, Project project,
			String platform, String createdBy, Date createdAt, String updatedBy, Date updatedAt,
			List<TestCase> testcases) {
		this.planId = planId;
		this.planName = planName;
		this.targetEnv = targetEnv;
		this.scheduleConfig = scheduleConfig;
		this.project = project;
		this.platform = platform;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.testcases = testcases;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public TargetEnv getTargetEnv() {
		return targetEnv;
	}
	public void setTargetEnv(TargetEnv targetEnv) {
		this.targetEnv = targetEnv;
	}
	public String getScheduleConfig() {
		return scheduleConfig;
	}
	public void setScheduleConfig(String scheduleConfig) {
		this.scheduleConfig = scheduleConfig;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public List<TestCase> getTestcases() {
		return testcases;
	}
	public void setTestcases(List<TestCase> testcases) {
		this.testcases = testcases;
	}
	
	
}
