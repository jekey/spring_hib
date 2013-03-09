package com.tbone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 2570971705194842387L;

	private Integer userId;
	private String username;
	private String createdBy;
	private Date createdDate;
	private Set<UserCountry> _countries = new HashSet<UserCountry>(0);
	private UserAddress _userAddress;
	private Set<Meeting> _meetings = new HashSet<Meeting>(0);

	public User() {
	}

	public User(String username, String createdBy, Date createdDate) {
		this.username = username;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}

	public String toString() {
		return "userId=" + userId + ", username=" + username + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", countries="
				+ _countries + " ,userAddress=" + _userAddress + ", meetings="
				+ _meetings;
	}

	@Id
	// by default takes auto
	@GeneratedValue
	@Column(name = "USER_ID", unique = true, nullable = false, precision = 5, scale = 0)
	// NOTE: the return type has to be same as field type, autoboxing/'int'
	// wouldn't work even, else getter exception thrown
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "USERNAME", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "CREATED_BY", nullable = false, length = 20)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DATE", nullable = false, length = 7)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	// 1) If EAGER used then objects for UserCountry not fetched later but
	// fetched in the same query
	// for fetching DBUser itself using left outer join
	// 2) give getter method name in mappedBy which is used in UserCountry to
	// map this DBUser class instance
	// which is being referred by _user field or getUser() method currently in
	// UserCountry
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	public Set<UserCountry> getCountries() {
		return _countries;
	}

	public void setCountries(Set<UserCountry> iCountries) {
		_countries = iCountries;
	}

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public UserAddress getUserAddress() {
		return _userAddress;
	}

	public void setUserAddress(UserAddress iUserAddress) {
		_userAddress = iUserAddress;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_MEETING", joinColumns = { @JoinColumn(name = "user_ID") }, inverseJoinColumns = { @JoinColumn(name = "MEETING_ID") })
	public Set<Meeting> getMeetings() {
		return _meetings;
	}

	public void setMeetings(Set<Meeting> iMeetings) {
		_meetings = iMeetings;
	}

}