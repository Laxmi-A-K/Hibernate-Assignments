package com.te.hibernate.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
//@Table(name="Emp")
public class Employee implements Serializable {
	@Id
//	@Column(name="emp-id")
	private Integer id;
//	@Column(name="Emp_name")
	private String name;
//	@Column(name="Emp_sal")
	private double salary;
//	@Column(name="Emp_design")
	private String design;
}
