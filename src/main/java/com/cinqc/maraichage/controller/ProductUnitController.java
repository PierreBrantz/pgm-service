package com.cinqc.maraichage.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.ProductUnitDTO;
import com.cinqc.maraichage.service.ProductUnitService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;



@RestController
@CrossOrigin
@RequestMapping("/productUnits")
public class ProductUnitController {

	@Autowired
	ProductUnitService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProductUnitDTO> findAllProductUnits() {
		return service.findAllProductUnits();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void updateProductUnits(@RequestBody String body) {
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type listOfMyClassObject = new TypeToken<ArrayList<ProductUnitDTO>>() {}.getType();
	    
	    List<ProductUnitDTO> productUnits = gson.fromJson(body, listOfMyClassObject);
	    
		service.updateProductUnits(productUnits);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> deleteProductUnit(@PathVariable String id) {	
		try {
			service.deleteProductUnit(Long.parseLong(id));			
		}
		catch (DataIntegrityViolationException ex) {
			return new ResponseEntity<>("ko", HttpStatus.FAILED_DEPENDENCY);
		}
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
	
	@GetMapping("/sequencecurrVal")
	@ResponseStatus(HttpStatus.OK)
	public Integer findSequenceCurrVal() {
		return service.findSequenceCurrVal();
	}
}
