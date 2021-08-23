package com.cinqc.maraichage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.SeasonalityDTO;
import com.cinqc.maraichage.repository.SeasonalityRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class SeasonalityService {

	@Autowired
	SeasonalityRepository repository;
	

	public Iterable<SeasonalityDTO> findAllSeasons() {
		return MapperUtil.mapList(repository.findByOrderByNameAsc(), SeasonalityDTO.class);
	}
}
