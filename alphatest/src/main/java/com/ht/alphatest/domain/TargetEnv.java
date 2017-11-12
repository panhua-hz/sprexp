package com.ht.alphatest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="target_env")
public class TargetEnv {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="env_id")
	private Long envId;
	
	@Column(name="env_name")
	private String envName;
	
	@Column(name="env_description")
	private String envDescription;
	
	@Column(name="platform")
	private String platform;
	
	@Column(name="url_forcall")
	private String urlForCall;
	
	@Column(name="equipment_id")
	private String equipmentId;
	
	@Column(name="equipment_name")
	private String equipmentName;
	
	@Column(name="os_version")
	private String osVersion;
	
	@Column(name="capability")
	private String capability;

	public TargetEnv() {

	}
	
	public TargetEnv(String envName, String envDescription, String platform, String urlForCall,
			String equipmentId, String equipmentName, String osVersion, String capability) {
		this.envName = envName;
		this.envDescription = envDescription;
		this.platform = platform;
		this.urlForCall = urlForCall;
		this.equipmentId = equipmentId;
		this.equipmentName = equipmentName;
		this.osVersion = osVersion;
		this.capability = capability;
	}	

	public TargetEnv(Long envId, String envName, String envDescription, String platform, String urlForCall,
			String equipmentId, String equipmentName, String osVersion, String capability) {
		this.envId = envId;
		this.envName = envName;
		this.envDescription = envDescription;
		this.platform = platform;
		this.urlForCall = urlForCall;
		this.equipmentId = equipmentId;
		this.equipmentName = equipmentName;
		this.osVersion = osVersion;
		this.capability = capability;
	}

	public Long getEnvId() {
		return envId;
	}

	public void setEnvId(Long envId) {
		this.envId = envId;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public String getEnvDescription() {
		return envDescription;
	}

	public void setEnvDescription(String envDescription) {
		this.envDescription = envDescription;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getUrlForCall() {
		return urlForCall;
	}

	public void setUrlForCall(String urlForCall) {
		this.urlForCall = urlForCall;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getCapability() {
		return capability;
	}

	public void setCapability(String capability) {
		this.capability = capability;
	}
}
