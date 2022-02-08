package com.cinqc.maraichage.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.SeasonalityDTO;
import com.cinqc.maraichage.service.SeasonalityService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;



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
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void updateSeasons(@RequestBody String body) {
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type listOfMyClassObject = new TypeToken<ArrayList<SeasonalityDTO>>() {}.getType();
	    
	    List<SeasonalityDTO> seasons = gson.fromJson(body, listOfMyClassObject);
	    
		service.updateSeasons(seasons);
	}

	
	
}
