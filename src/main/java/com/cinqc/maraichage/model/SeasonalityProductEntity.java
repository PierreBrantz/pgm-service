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
	private Integer january;
	
	@Column(name="february")
	private Integer february;
	
	@Column(name="march")
	private Integer march;
	
	@Column(name="april")
	private Integer april;
	
	@Column(name="may")
	private Integer may;
	
	@Column(name="june")
	private Integer june;
	
	@Column(name="july")
	private Integer july;
	
	@Column(name="august")
	private Integer august;
	
	@Column(name="september")
	private Integer september;
	
	@Column(name="october")
	private Integer october;
	
	@Column(name="november")
	private Integer november;
	
	@Column(name="december")
	private Integer december;

	
	@JsonIgnore
	@OneToOne(mappedBy = "seasonalityProduct", cascade = CascadeType.ALL)
	private ProductEntity product;
	
	
	
}
