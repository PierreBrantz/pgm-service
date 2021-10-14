package com.cinqc.maraichage.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeasonalityProductDTO {
	
	private long id;
	
	private long productId;


	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Zagreb" )
	private Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Zagreb")
	private Date endDate;

}
