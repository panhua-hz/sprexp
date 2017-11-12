package com.ht.alphatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="test_case")
public class TestCase {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="case_id")
	private Long caseId;
	
	@Column(name="case_version")
	private Long caseVersion;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false, targetEntity=Project.class)
	@JoinColumn(name="project_id", nullable=false, updatable=false)
	private Project project;
	
	@OneToOne(fetch=FetchType.EAGER, optional=true, targetEntity=TestCaseTrack.class)
	@JoinColumn(name="case_track_id", unique=false, nullable=true, updatable=true)
	private TestCaseTrack caseTrack;
	
	@Column(name="referenced")
	private int referenced;

	public TestCase() {
		
	}

	public TestCase(Project project, Long caseVersion, TestCaseTrack caseTrack) {
		this.project = project;
		this.caseVersion = caseVersion;
		this.caseTrack = caseTrack;
	}
	
	public TestCase(Long caseId, Project project, Long caseVersion, TestCaseTrack caseTrack) {
		this.caseId = caseId;
		this.project = project;
		this.caseVersion = caseVersion;
		this.caseTrack = caseTrack;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getCaseVersion() {
		return caseVersion;
	}

	public void setCaseVersion(Long caseVersion) {
		this.caseVersion = caseVersion;
	}

	public TestCaseTrack getCaseTrack() {
		return caseTrack;
	}

	public void setCaseTrack(TestCaseTrack caseTrack) {
		this.caseTrack = caseTrack;
	}

	public int getReferenced() {
		return referenced;
	}

	public void setReferenced(int referenced) {
		this.referenced = referenced;
	}
	
}
