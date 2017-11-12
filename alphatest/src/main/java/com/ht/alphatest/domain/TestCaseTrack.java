package com.ht.alphatest.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="test_case_track")
public class TestCaseTrack {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="case_track_id")
	private Long caseTrackId;
	
	@Column(name="case_id")
	private Long caseId;
	
	@Column(name="case_version")
	private Long caseVersion;
	
	@Column(name="project_id")
	private Long projectId;
	
	@Column(name="case_name")
	private String caseName;
	
	@Column(name="case_description")
	private String caseDescription;
	
	@Column(name="target_platform")
	private String target_platform;
	
	@Column(name="referenced")
	private int referenced;
	
	@Column(name="created_by")
	private String createdBy;
	
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@OneToMany(cascade=CascadeType.ALL, 
			targetEntity=TestStep.class, 
			//fetch=FetchType.EAGER, 
			mappedBy="caseTrackId")
	private List<TestStep> testSteps;
	
	@OneToMany(cascade=CascadeType.ALL, 
			targetEntity=TestRoundData.class, 
			//fetch=FetchType.EAGER, 
			mappedBy="caseTrackId")
	private List<TestRoundData> testDatas;

	public TestCaseTrack() {
		
	}
	public TestCaseTrack(Long caseId, Long caseVersion, Long projectId, String caseName,
			String caseDescription, String target_platform, String createdBy, Date createdAt) {
		this.caseId = caseId;
		this.caseVersion = caseVersion;
		this.projectId = projectId;
		this.caseName = caseName;
		this.caseDescription = caseDescription;
		this.target_platform = target_platform;
		this.referenced = 0;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}
	
	public TestCaseTrack(Long caseId, Long caseVersion, Long projectId, String caseName,
			String caseDescription, String target_platform, int referenced, String createdBy, Date createdAt,
			String updatedBy, Date updatedAt, List<TestStep> testSteps, List<TestRoundData> testDatas) {
		this.caseId = caseId;
		this.caseVersion = caseVersion;
		this.projectId = projectId;
		this.caseName = caseName;
		this.caseDescription = caseDescription;
		this.target_platform = target_platform;
		this.referenced = referenced;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.testSteps = testSteps;
		this.testDatas = testDatas;
	}

	public TestCaseTrack(Long caseTrackId, Long caseId, Long caseVersion, Long projectId, String caseName,
			String caseDescription, String target_platform, int referenced, String createdBy, Date createdAt,
			String updatedBy, Date updatedAt, List<TestStep> testSteps, List<TestRoundData> testDatas) {
		this.caseTrackId = caseTrackId;
		this.caseId = caseId;
		this.caseVersion = caseVersion;
		this.projectId = projectId;
		this.caseName = caseName;
		this.caseDescription = caseDescription;
		this.target_platform = target_platform;
		this.referenced = referenced;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedBy = updatedBy;
		this.updatedAt = updatedAt;
		this.testSteps = testSteps;
		this.testDatas = testDatas;
	}
	
	public Long getCaseTrackId() {
		return caseTrackId;
	}
	public void setCaseTrackId(Long caseTrackId) {
		this.caseTrackId = caseTrackId;
	}
	public Long getCaseId() {
		return caseId;
	}
	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	public Long getCaseVersion() {
		return caseVersion;
	}
	public void setCaseVersion(Long caseVersion) {
		this.caseVersion = caseVersion;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseDescription() {
		return caseDescription;
	}
	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}
	public String getTarget_platform() {
		return target_platform;
	}
	public void setTarget_platform(String target_platform) {
		this.target_platform = target_platform;
	}
	public int getReferenced() {
		return referenced;
	}
	public void setReferenced(int referenced) {
		this.referenced = referenced;
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
	public List<TestStep> getTestSteps() {
		return testSteps;
	}
	public void setTestSteps(List<TestStep> testSteps) {
		this.testSteps = testSteps;
	}
	public List<TestRoundData> getTestDatas() {
		return testDatas;
	}
	public void setTestDatas(List<TestRoundData> testDatas) {
		this.testDatas = testDatas;
	}
	
	
	
}
