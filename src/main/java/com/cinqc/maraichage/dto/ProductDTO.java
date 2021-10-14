package com.cinqc.maraichage.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cinqc.maraichage.model.PackagingEntity;
import com.cinqc.maraichage.model.ProducerEntity;
import com.cinqc.maraichage.model.RealQuantityEntity;
import com.cinqc.maraichage.model.SeasonalityEntity;
import com.cinqc.maraichage.model.SeasonalityProductEntity;
import com.cinqc.maraichage.model.TheoreticalQuantityEntity;

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

	
	private List<RealQuantityEntity> realQuantities = new ArrayList<>();

	private SeasonalityEntity currentSeason;
	
	private List<SeasonalityProductEntity> seasonInformation;
	
	private float[] seasons;

	private List<ProducerEntity> producers = new ArrayList<>();

	private String conditioning;

	private Integer nbByConditioning;

	private Float weight;

	private String unitForSale;

	private String conditioningByVegetable;

	private String percentBrutConditioning;

	private String workCostByConditioning;

	private String deliveryTimeLimit;

}
