package com.cinqc.maraichage.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.ProductLabelDTO;
import com.cinqc.maraichage.service.ProductLabelService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;



@RestController
@CrossOrigin
@RequestMapping("/productLabels")
public class ProductLabelController {

	@Autowired
	ProductLabelService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProductLabelDTO> findAllProductLabels() {
		return service.findAllProductLabels();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void updateProductLabels(@RequestBody String body) {
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type listOfMyClassObject = new TypeToken<ArrayList<ProductLabelDTO>>() {}.getType();
	    
	    List<ProductLabelDTO> productLabels = gson.fromJson(body, listOfMyClassObject);
	    
		service.updateProductLabels(productLabels);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProductLabel(@PathVariable String id) {	
		service.deleteProductLabel(Long.parseLong(id));
	}
	
	
}
