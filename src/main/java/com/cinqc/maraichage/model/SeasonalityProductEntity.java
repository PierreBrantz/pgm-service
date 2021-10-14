package com.cinqc.maraichage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="seasonality_product")
public class SeasonalityProductEntity {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "seasonality_id")
	private long seasonalityId;	

	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id", insertable=false, updatable =false) 
	private ProductEntity product;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "seasonality_id", insertable=false, updatable =false) 
	private SeasonalityEntity seasonality;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone="Europe/Zagreb" )
	@Column(name = "start_date")
	private Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Zagreb" )
	@Column(name = "end_date")
	private Date endDate;
}
