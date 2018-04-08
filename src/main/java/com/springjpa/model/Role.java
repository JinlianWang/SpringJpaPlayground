package com.springjpa.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "roles")
@XmlAccessorType(XmlAccessType.FIELD)
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7983141855304184538L;
	
	@Id
	@Column(name="role_id")
	@XmlElement
	private int roleId;
	
	@Column(name="nm")
	@XmlElement
	private String name;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	@Override
	public String toString() {
		return String.format("Role[id=%s, name='%s']",this.roleId, this.name);
	}
}
