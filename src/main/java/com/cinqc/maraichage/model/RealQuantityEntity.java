package com.cinqc.maraichage.model;

import java.math.BigDecimal;

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
@Table(name="real_quantity")
public class RealQuantityEntity {

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
	
	private transient long producerId;
	
	
}
