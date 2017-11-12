package com.ht.alphatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="key_words")
public class KeyWord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="keyword_id")
	private Long keyWordId;
	
	@Column(name="operation")
	private String operation;
	
	@Column(name="keyword_name")
	private String keyWordName;
	
	@Column(name="param_layout")
	private String param_layout;

	public Long getKeyWordId() {
		return keyWordId;
	}

	public void setKeyWordId(Long keyWordId) {
		this.keyWordId = keyWordId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getKeyWordName() {
		return keyWordName;
	}

	public void setKeyWordName(String keyWordName) {
		this.keyWordName = keyWordName;
	}

	public String getParam_layout() {
		return param_layout;
	}

	public void setParam_layout(String param_layout) {
		this.param_layout = param_layout;
	}
	
	
}
