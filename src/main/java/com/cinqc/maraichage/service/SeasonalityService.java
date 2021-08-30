package com.cinqc.maraichage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.SeasonalityDTO;
import com.cinqc.maraichage.model.ProductEntity;
import com.cinqc.maraichage.model.SeasonalityEntity;
import com.cinqc.maraichage.model.SeasonalityProductEntity;
import com.cinqc.maraichage.repository.SeasonalityRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class SeasonalityService {

	@Autowired
	SeasonalityRepository repository;
	

	public Iterable<SeasonalityDTO> findAllSeasons() {
		return MapperUtil.mapList(repository.findByOrderByNameAsc(), SeasonalityDTO.class);
	}
	
	public SeasonalityEntity findCurrentSeasonality(ProductEntity product) {
		Date now = new Date();
		for(SeasonalityProductEntity sp : product.getSeasonalityProducts()) {
			if(now.after(sp.getStartDate()) && now.before(sp.getEndDate())) {
				return sp.getSeasonality();
			}
		}
		return null;
	}
}
