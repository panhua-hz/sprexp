package com.ht.alphatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="test_steps")
public class TestStep {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="step_id")
	private Long stepId;
	
	@Column(name="case_track_id")
	private Long caseTrackId;
	
	@Column(name="step_seq")
	private Long stepSeq;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false, targetEntity=KeyWord.class)
	@JoinColumn(name="keyword_id", nullable=false, updatable=false)
	private KeyWord keyword;
	
	@Column(name="target_type")
	private String targetType;
	
	@Column(name="referenced_case_track_id")
	private Long referencedCaseTrackId;
	
	@Column(name="target_locator_by")
	private String targetLocatorBy;
	
	@Column(name="targetLocatorValue")
	private String targetLocatorValue;
	
	@Column(name="target_value")
	private String targetValue;
	
	@Column(name="default_data")
	private String defaultData;
	
	public TestStep() {
	
	}
	
	//keyword with target a locator
	public TestStep(Long caseTrackId, Long stepSeq, KeyWord keyword, String targetLocatorBy, String targetLocatorValue, String defaultData) {
		this.caseTrackId = caseTrackId;
		this.stepSeq = stepSeq;
		this.keyword = keyword;
		this.targetType = KWTargetType.LOCATOR.name();
		this.targetLocatorBy = targetLocatorBy;
		this.targetLocatorValue = targetLocatorValue;
		this.defaultData = defaultData;
	}
	
	//keyword with target a data
	public TestStep(Long caseTrackId, Long stepSeq, KeyWord keyword, String targetValue) {
		this.caseTrackId = caseTrackId;
		this.stepSeq = stepSeq;
		this.keyword = keyword;
		this.targetType = KWTargetType.DATA.name();
		this.targetValue = targetValue;
	}
	
	//keyword with target a data
	public TestStep(Long caseTrackId, Long stepSeq, KeyWord keyword, String targetValue, String defaultData) {
		this.caseTrackId = caseTrackId;
		this.stepSeq = stepSeq;
		this.keyword = keyword;
		this.targetType = KWTargetType.DATA.name();
		this.targetValue = targetValue;
		this.defaultData = defaultData;
	}
	
	//keyword is referenced
	public TestStep(Long caseTrackId, Long stepSeq, KeyWord keyword, Long referencedCaseTrackId) {
		this.caseTrackId = caseTrackId;
		this.stepSeq = stepSeq;
		this.keyword = keyword;
		this.targetType = KWTargetType.REFERENCED.name();
		this.referencedCaseTrackId = referencedCaseTrackId;
	}
	
	public TestStep(Long caseTrackId, Long stepSeq, KeyWord keyword, String targetType,
			Long referencedCaseTrackId, String targetLocatorBy, String targetLocatorValue, String targetValue,
			String defaultData) {
		this.caseTrackId = caseTrackId;
		this.stepSeq = stepSeq;
		this.keyword = keyword;
		this.targetType = targetType;
		this.referencedCaseTrackId = referencedCaseTrackId;
		this.targetLocatorBy = targetLocatorBy;
		this.targetLocatorValue = targetLocatorValue;
		this.targetValue = targetValue;
		this.defaultData = defaultData;
	}

	public TestStep(Long stepId, Long caseTrackId, Long stepSeq, KeyWord keyword, String targetType,
			Long referencedCaseTrackId, String targetLocatorBy, String targetLocatorValue, String targetValue,
			String defaultData) {
		this.stepId = stepId;
		this.caseTrackId = caseTrackId;
		this.stepSeq = stepSeq;
		this.keyword = keyword;
		this.targetType = targetType;
		this.referencedCaseTrackId = referencedCaseTrackId;
		this.targetLocatorBy = targetLocatorBy;
		this.targetLocatorValue = targetLocatorValue;
		this.targetValue = targetValue;
		this.defaultData = defaultData;
	}
	
	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public Long getCaseTrackId() {
		return caseTrackId;
	}

	public void setCaseTrackId(Long caseTrackId) {
		this.caseTrackId = caseTrackId;
	}

	public Long getStepSeq() {
		return stepSeq;
	}

	public void setStepSeq(Long stepSeq) {
		this.stepSeq = stepSeq;
	}

	public KeyWord getKeyword() {
		return keyword;
	}

	public void setKeyword(KeyWord keyword) {
		this.keyword = keyword;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Long getReferencedCaseTrackId() {
		return referencedCaseTrackId;
	}

	public void setReferencedCaseTrackId(Long referencedCaseTrackId) {
		this.referencedCaseTrackId = referencedCaseTrackId;
	}

	public String getTargetLocatorBy() {
		return targetLocatorBy;
	}

	public void setTargetLocatorBy(String targetLocatorBy) {
		this.targetLocatorBy = targetLocatorBy;
	}

	public String getTargetLocatorValue() {
		return targetLocatorValue;
	}

	public void setTargetLocatorValue(String targetLocatorValue) {
		this.targetLocatorValue = targetLocatorValue;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public String getDefaultData() {
		return defaultData;
	}

	public void setDefaultData(String defaultData) {
		this.defaultData = defaultData;
	}
	
	
}
