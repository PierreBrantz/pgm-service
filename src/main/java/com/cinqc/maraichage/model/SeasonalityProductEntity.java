package com.cinqc.maraichage.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@Column(name = "product_id")
	private long productId;	
	
	@Column(name="january")
	private Integer january = 1;
	
	@Column(name="february")
	private Integer february = 1;
	
	@Column(name="march")
	private Integer march = 1;
	
	@Column(name="april")
	private Integer april = 1;
	
	@Column(name="may")
	private Integer may = 1;
	
	@Column(name="june")
	private Integer june = 1;
	
	@Column(name="july")
	private Integer july = 1;
	
	@Column(name="august")
	private Integer august = 1;
	
	@Column(name="september")
	private Integer september = 1;
	
	@Column(name="october")
	private Integer october = 1;
	
	@Column(name="november")
	private Integer november = 1;
	
	@Column(name="december")
	private Integer december = 1;

	
	@JsonIgnore
	@OneToOne(mappedBy = "seasonalityProduct", cascade = CascadeType.ALL)
	private ProductEntity product;
	
	
	
}
