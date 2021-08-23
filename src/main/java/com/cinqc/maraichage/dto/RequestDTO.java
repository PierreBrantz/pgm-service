package com.cinqc.maraichage.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {
	
	private long id;

	private String name;
		
	private Date startPeriod;
		
	private Date endPeriod;
	
	private long quantity;
		
	private String unit;
}
