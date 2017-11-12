package com.ht.alphatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="test_round_data")
public class TestRoundData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="round_data_id")
	private Long roundDataId;
	
	@Column(name="case_track_id")
	private Long caseTrackId;
	
	@Column(name="round_num")
	private Long roundNum;
	
	@Column(name="step_id")
	private Long stepId; 
	
	@Column(name="round_data")
	private String roundData;

	public TestRoundData() {
		
	}

	public TestRoundData(Long caseTrackId, Long roundNum, Long stepId, String roundData) {
		this.caseTrackId = caseTrackId;
		this.roundNum = roundNum;
		this.stepId = stepId;
		this.roundData = roundData;
	}
	
	public TestRoundData(Long roundDataId, Long caseTrackId, Long roundNum, Long stepId, String roundData) {
		this.roundDataId = roundDataId;
		this.caseTrackId = caseTrackId;
		this.roundNum = roundNum;
		this.stepId = stepId;
		this.roundData = roundData;
	}

	public Long getRoundDataId() {
		return roundDataId;
	}

	public void setRoundDataId(Long roundDataId) {
		this.roundDataId = roundDataId;
	}

	public Long getCaseTrackId() {
		return caseTrackId;
	}

	public void setCaseTrackId(Long caseTrackId) {
		this.caseTrackId = caseTrackId;
	}

	public Long getRoundNum() {
		return roundNum;
	}

	public void setRoundNum(Long roundNum) {
		this.roundNum = roundNum;
	}

	public Long getStepId() {
		return stepId;
	}

	public void setStepId(Long stepId) {
		this.stepId = stepId;
	}

	public String getRoundData() {
		return roundData;
	}

	public void setRoundData(String roundData) {
		this.roundData = roundData;
	}
	
}
