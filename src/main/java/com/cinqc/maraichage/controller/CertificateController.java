package com.cinqc.maraichage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.CertificateDTO;
import com.cinqc.maraichage.service.CertificateService;



@RestController
@CrossOrigin
@RequestMapping("/certificates")
public class CertificateController {

	@Autowired
	CertificateService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<CertificateDTO> findAllCertificates() {
		return service.findAllCertificates();
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
