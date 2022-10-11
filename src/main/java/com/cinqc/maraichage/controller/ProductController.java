package com.cinqc.maraichage.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
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

import com.cinqc.maraichage.dto.ProductDTO;
import com.cinqc.maraichage.model.RealQuantityEntity;
import com.cinqc.maraichage.model.WeeklyProposalEntity;
import com.cinqc.maraichage.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;



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
	public ProductDTO findProductId(@PathVariable Long id) {
		return service.findProductById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String id) {	
		  service.deleteById(Long.parseLong(id));		 
	}
	
	@GetMapping("/name/{name}")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProductDTO> findProductsByName(@PathVariable String name) {
		return service.findProductsByName(name);
	}
	
	@GetMapping("/producer/{producerId}")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProductDTO> findProductsByProducer(@PathVariable Long producerId){

		return service.findProductsByProducer(producerId);	
	}
	
	@GetMapping("/producer/abr/{abr}")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProductDTO> findProductsByProducer(@PathVariable String abr){

		return service.findProductsByProducerAbr(abr);	
	}

	@RequestMapping(value = "/{productId}/producer/{producerId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody RealQuantityEntity updateRealProduct( @RequestBody String body, @PathVariable Long productId, @PathVariable Long producerId) {	
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type listOfMyClassObject = new TypeToken<ProductDTO>() {}.getType();
	    ProductDTO product = gson.fromJson(body, listOfMyClassObject);
	
		return service.updateRealProduct(productId, producerId, product);
	}
	
	@RequestMapping(value = "/{productId}/producer/{producerId}/date/{date}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<WeeklyProposalEntity> updateWeeklyProposal( @RequestBody String body, @PathVariable Long productId, @PathVariable Long producerId, @PathVariable Date date) {	
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type listOfMyClassObject = new TypeToken<ProductDTO>() {}.getType();
	    ProductDTO product = gson.fromJson(body, listOfMyClassObject);
	
		return service.updateWeeklyProposal(productId, producerId, product);
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void updateProducts(@RequestBody String body) {
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type listOfMyClassObject = new TypeToken<ArrayList<ProductDTO>>() {}.getType();
	    
	    List<ProductDTO> products = gson.fromJson(body, listOfMyClassObject);
	    
		service.updateProducts(products);
	}
	
	@RequestMapping(value = "/name/{productName}", method = RequestMethod.POST)
	public @ResponseBody Long addNewProduct(@PathVariable String productName) {	
		return service.addProductWithName(productName).getId();
	}
	
	
}
