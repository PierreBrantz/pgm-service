package com.cinqc.maraichage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="request")
public class RequestEntity {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "start_period")
	private Date startPeriod;
	
	@Column(name = "end_period")
	private Date endPeriod;
	
	@Column(name = "quantity")
	private long quantity;
	
	@Column(name = "unit")
	private String unit;
	
}
