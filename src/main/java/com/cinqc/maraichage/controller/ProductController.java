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

import com.cinqc.maraichage.dto.ProductDTO;
import com.cinqc.maraichage.dto.RealProductDTO;
import com.cinqc.maraichage.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProductDTO> findAllProducts() {
		return service.findAllProducts();
	}

	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductDTO findProductById(@PathVariable Long id) {
		return service.findProductById(id);
	}
	
	@GetMapping("/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProductDTO> findProductsByName(@PathVariable String name) {
		return service.findProductsByName(name);
	}
	
	@GetMapping("/producer/{producerId}")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<RealProductDTO> findProductsByProducer(@PathVariable Long producerId){

		return service.findProductsByProducer(producerId);	
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, 
	        produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ProductDTO updateProduct( @RequestParam Map<String, String> body, @PathVariable Long id) {	
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    ProductDTO product = gson.fromJson(body.keySet().iterator().next(), ProductDTO.class);
	
		return service.updateProduct(id, product);
	}
	
	@RequestMapping(value = "/{productId}/producer/{producerId}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, 
	        produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody RealProductDTO updateRealProduct( @RequestParam Map<String, String> body, @PathVariable Long productId, @PathVariable Long producerId) {	
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    RealProductDTO product = gson.fromJson(body.keySet().iterator().next(), RealProductDTO.class);
	
		return service.updateRealProduct(productId, producerId, product);
	}
}
