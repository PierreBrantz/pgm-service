package com.cinqc.maraichage.model;

import java.math.BigDecimal;
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
@Table(name="weekly_proposal")
public class WeeklyProposalEntity {
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "date")
	private Date date;
	
	@Column(name = "producer_product_id")
	private long producerProductId;
	
	@Column
	private BigDecimal quantity; 
	
	@Column(name = "real_quantity")
	private BigDecimal realQuantity;
}
