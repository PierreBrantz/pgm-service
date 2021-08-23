package com.cinqc.maraichage.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cinqc.maraichage.model.PackagingEntity;
import com.cinqc.maraichage.model.RealQuantityEntity;
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
	


}
