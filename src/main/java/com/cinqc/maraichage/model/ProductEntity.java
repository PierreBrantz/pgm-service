package com.cinqc.maraichage.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="product")
public class ProductEntity {
	
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToOne
    @JoinColumn(name = "packaging_id", referencedColumnName = "id")
	private PackagingEntity packaging;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="product_id")	
	private Set<TheoreticalQuantityEntity> quantities;

	@ManyToMany(mappedBy = "products")
    private Set<ProducerEntity> producers = new HashSet<>();
	
	@OneToMany(mappedBy = "product")
	private Set<SeasonalityProductEntity> seasonalityProducts;
	

	private transient Set<RealQuantityEntity> realQuantities;
	
	private transient long producerId;
	
	@Column(name = "conditioning")
	private String conditioning;
	
	@Column(name = "nb_by_conditioning")
	private Integer nbByConditioning;
	
	@Column(name = "weight")
	private Float weight;
	
	@Column(name = "unit_for_sale")
	private String unitForSale;
	
	@Column(name = "conditioning_by_vegetable")
	private String conditioningByVegetable;
	
	@Column(name = "percent_brut_conditioning")
	private String percentBrutConditioning;
	
	@Column(name = "work_cost_by_conditioning")
	private String workCostByConditioning;
	
	@Column(name = "delivery_time_limit")
	private String deliveryTimeLimit;
	
	
}
