package com.cinqc.maraichage.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.cinqc.maraichage.model.PackagingEntity;
import com.cinqc.maraichage.model.RealQuantityEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealProductDTO {
	
	private long id;
		
	private String name;
	
	private PackagingEntity packaging;

	private BigDecimal price;	
	
	private List<RealQuantityEntity> realQuantities =  new ArrayList<>();
	
	private long producerId;
	

}
