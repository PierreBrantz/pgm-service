package com.cinqc.maraichage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.ProducerTypeDTO;
import com.cinqc.maraichage.service.ProducerTypeService;



@RestController
@CrossOrigin
@RequestMapping("/producerTypes")
public class ProducerTypeController {

	@Autowired
	ProducerTypeService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<ProducerTypeDTO> findAllProducerTypes() {
		return service.findAllProducerTypes();
	}

	/*
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void updateProductLabels(@RequestBody String body) {
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type listOfMyClassObject = new TypeToken<ArrayList<ProductLabelDTO>>() {}.getType();
	    
	    List<ProductLabelDTO> productLabels = gson.fromJson(body, listOfMyClassObject);
	    
		service.updateProductLabels(productLabels);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> deleteProductLabel(@PathVariable String id) {	
		try {
			service.deleteProductLabel(Long.parseLong(id));			
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
	*/
	
}
