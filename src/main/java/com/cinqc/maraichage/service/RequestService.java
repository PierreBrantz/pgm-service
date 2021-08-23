package com.cinqc.maraichage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.RequestDTO;
import com.cinqc.maraichage.exception.RequestNotFoundException;
import com.cinqc.maraichage.model.RequestEntity;
import com.cinqc.maraichage.repository.RequestRepository;
import com.cinqc.maraichage.util.MapperUtil;

@Service
public class RequestService {
	@Autowired
	RequestRepository repository;

	public Iterable<RequestDTO> findAllRequests() {
		return MapperUtil.mapList(repository.findAll(), RequestDTO.class);
	}

	
	public RequestEntity findByName(String name) {
		return repository.findByName(name);
	}

	public RequestDTO findRequestById(Long id) {
	
		return MapperUtil.getModelMapperInstance().map(
				repository.findById(id)
						.orElseThrow(() -> new RequestNotFoundException(
								String.format("Could not find any request with identifier => '%d'", id))),
						RequestDTO.class);
	}
	
	public RequestDTO create(RequestDTO request) {
		return MapperUtil.getModelMapperInstance().map(repository.save(MapperUtil.getModelMapperInstance().map(request, RequestEntity.class)),RequestDTO.class);
	}
	
	public void delete(long id) {		
		repository.delete(repository.findById(id).get());
	}
	
	public Iterable<RequestDTO> findRequestsByMonth(int month) {
		return MapperUtil.mapList(repository.findByMonth(month), RequestDTO.class);
	}
	
}
