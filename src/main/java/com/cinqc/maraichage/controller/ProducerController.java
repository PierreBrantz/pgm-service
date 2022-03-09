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
import com.cinqc.maraichage.service.ProducerProductService;
import com.cinqc.maraichage.service.ProducerService;
import com.cinqc.maraichage.util.MapperUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@CrossOrigin
@RequestMapping("/producers")
public class ProducerController {

	@Autowired
	ProducerService service;
	@Autowired
	ProducerProductService ppService;
	
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProducerDTO> findAllProducers() {
		return service.findAllProducers();
	}
	
	@GetMapping("/abr/{abr}")
	@ResponseStatus(HttpStatus.OK)
	public ProducerDTO findProducerByAbr(@PathVariable String abr) {
		return MapperUtil.getModelMapperInstance().map(service.findProducerByAbr(abr), ProducerDTO.class);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProducerDTO findProducerId(@PathVariable Long id) {
		return MapperUtil.getModelMapperInstance().map(service.findProducerById(id), ProducerDTO.class);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, 
	        produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ProducerDTO addProducer( @RequestParam Map<String, String> body) {	
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    ProducerDTO producer = gson.fromJson(body.keySet().iterator().next(), ProducerDTO.class);
	
		return service.addProducer(producer);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProducer(@PathVariable String id) {	
		  service.deleteById(Long.parseLong(id));		 
	}
	
	@RequestMapping(value = "/{producerId}/product/{productId}", method = RequestMethod.POST)
	public @ResponseBody ProducerDTO addProduct(@PathVariable String producerId, @PathVariable String productId) {	
		return service.addProduct(Long.parseLong(producerId), Long.parseLong(productId));
	}
	

	

	@RequestMapping(value = "/{producerId}/product/{productId}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String producerId, @PathVariable String productId) {	
		  ppService.deleteProduct(Long.parseLong(producerId), Long.parseLong(productId));		 
	}
}
