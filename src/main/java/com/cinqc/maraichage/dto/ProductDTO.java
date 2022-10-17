package com.cinqc.maraichage.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cinqc.maraichage.model.PackagingEntity;
import com.cinqc.maraichage.model.ProducerEntity;
import com.cinqc.maraichage.model.ProducerProductEntity;
import com.cinqc.maraichage.model.ProductFamilyEntity;
import com.cinqc.maraichage.model.ProductLabelEntity;
import com.cinqc.maraichage.model.ProductOriginEntity;
import com.cinqc.maraichage.model.ProductTypeEntity;
import com.cinqc.maraichage.model.ProductUnitEntity;
import com.cinqc.maraichage.model.RealQuantityEntity;
import com.cinqc.maraichage.model.SeasonalityProductEntity;
import com.cinqc.maraichage.model.TheoreticalQuantityEntity;
import com.cinqc.maraichage.model.WeeklyProposalEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	private long id;

	private String name;

	private PackagingEntity packaging;

	private BigDecimal price;		

	private List<TheoreticalQuantityEntity> quantities =  new ArrayList<>();

	private List<RealQuantityEntity> realQuantities =  new ArrayList<>();
	
	private List<ProducerProductEntity> producerProducts = new ArrayList<>();
	


	private SeasonalityProductEntity seasonalityProduct;
	
	private List<ProducerEntity> producers = new ArrayList<>();

	private ProductTypeEntity productType;

	private ProductFamilyEntity productFamily;
	

	private ProductUnitEntity productUnit;
	
	
	private ProductLabelEntity productLabel;
	
	private String calibration;
	
	private String salesFormat;
	

	private BigDecimal margin;
	

	private Long nbByPackaging;
	
	
	private ProductOriginEntity productOrigin;
	

	private String barCode;
	

	private Boolean cap;
	
	private Boolean fragil;
	
	
	private String eshopId;
	

	private String remark;
	
	private RealQuantityEntity currentRealQuantity;
	
	private List<WeeklyProposalEntity> weeklyProposals;
	
	private List<SeasonalityDTO> seasonalities;
	
	private List<ProductLabelDTO> productLabels;
	
	private List<ProductTypeDTO> productTypes;
	
	private List<ProductOriginDTO> productOrigins;


}
