package com.cinqc.maraichage.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="real_quantity")
public class RealQuantityEntity {

	
	public RealQuantityEntity() {};
	public RealQuantityEntity(int yearOfReference, long producerProductId, BigDecimal quantity1, BigDecimal quantity2,
			BigDecimal quantity3, BigDecimal quantity4, BigDecimal quantity5, BigDecimal quantity6,
			BigDecimal quantity7, BigDecimal quantity8, BigDecimal quantity9, BigDecimal quantity10,
			BigDecimal quantity11, BigDecimal quantity12) {
		super();
		this.yearOfReference = yearOfReference;
		this.producerProductId = producerProductId;
		this.quantity1 = quantity1;
		this.quantity2 = quantity2;
		this.quantity3 = quantity3;
		this.quantity4 = quantity4;
		this.quantity5 = quantity5;
		this.quantity6 = quantity6;
		this.quantity7 = quantity7;
		this.quantity8 = quantity8;
		this.quantity9 = quantity9;
		this.quantity10 = quantity10;
		this.quantity11 = quantity11;
		this.quantity12 = quantity12;
	}

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "year_of_reference")
	private int yearOfReference;
	
	@Column(name = "producer_product_id")
	private long producerProductId;
	
	@Column
	private BigDecimal quantity1; 
	@Column
	private BigDecimal quantity2; 
	@Column
	private BigDecimal quantity3; 
	@Column
	private BigDecimal quantity4; 
	@Column
	private BigDecimal quantity5; 
	@Column
	private BigDecimal quantity6; 
	@Column
	private BigDecimal quantity7; 
	@Column
	private BigDecimal quantity8; 
	@Column
	private BigDecimal quantity9; 
	@Column
	private BigDecimal quantity10;
	@Column
	private BigDecimal quantity11; 
	@Column
	private BigDecimal quantity12; 
	
	@Transient
	@JsonInclude()
	private long producerId;
	
	
}
