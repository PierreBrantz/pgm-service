package com.cinqc.maraichage.controller;

import java.lang.reflect.Type;

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

import com.cinqc.maraichage.model.ContractParamEntity;
import com.cinqc.maraichage.service.ContractParamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;



@RestController
@CrossOrigin
@RequestMapping("/contractParams")
public class ContractParamController {

	@Autowired
	ContractParamService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ContractParamEntity findContractParams() {
		return service.findContractParams();
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody void updateContractParams(@RequestBody String body) {
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    Type object = new TypeToken<ContractParamEntity>() {}.getType();
	    
	    ContractParamEntity contractParam = gson.fromJson(body, object);
	    
		service.updateContractParam(contractParam);
	}
	
	
}
