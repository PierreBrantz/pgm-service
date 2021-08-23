package com.cinqc.maraichage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cinqc.maraichage.dto.RequestDTO;
import com.cinqc.maraichage.service.RequestService;
import com.google.common.base.Preconditions;

@RestController
@CrossOrigin
@RequestMapping("/requests")
public class RequestController {
	@Autowired
	RequestService service;
/*
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	//public Iterable<RequestDTO> findAllRequests() {
		return service.findAllRequests();
//	}
	*/
	@GetMapping("/{month}")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<RequestDTO> findRequestsByMonth(@PathVariable(required=true,name="month") int month) {
		return service.findRequestsByMonth(month);
	}

/*
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RequestDTO findRequestById(@PathVariable Long id) {
		return service.findRequestById(id);
	}
*/
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RequestDTO create(@RequestBody RequestDTO request) {
//		Preconditions.checkNotNull(resource);
		return service.create(request);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
//		Preconditions.checkNotNull(resource);
		service.delete(id);
	}
}
