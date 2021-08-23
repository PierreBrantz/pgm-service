package com.cinqc.maraichage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.SeasonalityDTO;
import com.cinqc.maraichage.service.SeasonalityService;



@RestController
@CrossOrigin
@RequestMapping("/seasons")
public class SeasonalityController {

	@Autowired
	SeasonalityService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<SeasonalityDTO> findAllSeasons() {
		return service.findAllSeasons();
	}

	
	
}
