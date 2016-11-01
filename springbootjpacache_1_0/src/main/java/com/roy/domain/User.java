/**
 * 
 */
package com.roy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Roy
 *
 */
@Entity
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2486561438877160598L;

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String ip;

	@Column(nullable = false)
	private String password;

	public User() {
	}

	public User(int id, String name, String ip, String password) {
		this.id = id;
		this.name = name;
		this.ip = ip;
		this.password = password;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
