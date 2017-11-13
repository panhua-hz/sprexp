package com.ht.alphatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="project")
public class Project {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private Long projectId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="shortname")
	private String shortname;
	
	public Project() {
		
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", name=" + name + ", shortname=" + shortname + "]";
	}
}
