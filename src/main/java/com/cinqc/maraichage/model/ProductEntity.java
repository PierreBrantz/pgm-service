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
import javax.persistence.ManyToOne;
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
	
	@ManyToOne
    @JoinColumn(name = "packaging_id", referencedColumnName = "id")
	private PackagingEntity packaging;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="product_id")	
	private Set<TheoreticalQuantityEntity> quantities;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="product_id")	
	private Set<ProducerProductEntity> producerProducts;
	

	@ManyToMany(mappedBy = "products")
    private Set<ProducerEntity> producers = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "seasonality_product_id") 
	private SeasonalityProductEntity seasonalityProduct;
	
	@ManyToOne
    @JoinColumn(name = "product_type_id", referencedColumnName = "id")
	private ProductTypeEntity productType;
	
	@ManyToOne
    @JoinColumn(name = "product_family_id", referencedColumnName = "id")
	private ProductFamilyEntity productFamily;
	
	@ManyToOne
    @JoinColumn(name = "product_unit_id", referencedColumnName = "id")
	private ProductUnitEntity productUnit;
	
	@ManyToOne
    @JoinColumn(name = "product_label_id", referencedColumnName = "id")
	private ProductLabelEntity productLabel;
	
	@Column(name = "calibration")
	private String calibration;
	
	@Column(name = "sales_format")
	private String salesFormat;
	
	@Column(name = "margin")
	private BigDecimal margin;
	
	@Column(name = "nbbypackaging")
	private Long nbByPackaging;
	
	@ManyToOne
    @JoinColumn(name = "product_origin_id", referencedColumnName = "id")
	private ProductOriginEntity productOrigin;
	
	@Column(name = "bar_code")
	private String barCode;
	
	@Column(name = "cap")
	private Boolean cap;
	
	@Column(name = "fragil")
	private Boolean fragil;
	
	@Column(name = "eshop_id")
	private Long eshopId;
	
	@Column(name = "remark")
	private String remark;
	

	
	
}
