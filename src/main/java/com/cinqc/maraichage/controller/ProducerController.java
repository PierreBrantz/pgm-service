package com.cinqc.maraichage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.ProducerDTO;
import com.cinqc.maraichage.service.ProducerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@CrossOrigin
@RequestMapping("/producers")
public class ProducerController {

	@Autowired
	ProducerService service;
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProducerDTO> findAllProducers() {
		return service.findAllProducers();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, 
	        produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ProducerDTO addProducer( @RequestParam Map<String, String> body) {	
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    ProducerDTO producer = gson.fromJson(body.keySet().iterator().next(), ProducerDTO.class);
	
		return service.addProducer(producer);
	}
	
	@RequestMapping(value = "/{producerId}/product/{productId}", method = RequestMethod.POST)
	public @ResponseBody ProducerDTO addProduct(@PathVariable String producerId, @PathVariable String productId) {	
		return service.addProduct(Long.parseLong(producerId), Long.parseLong(productId));
	}
}
