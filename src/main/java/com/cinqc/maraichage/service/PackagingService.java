package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.PackagingDTO;
import com.cinqc.maraichage.model.PackagingEntity;
import com.cinqc.maraichage.repository.PackagingRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class PackagingService {

	@Autowired
	PackagingRepository repository;



	public Iterable<PackagingDTO> findAllPackagings() {
		return MapperUtil.mapList(repository.findByOrderByIdAsc(), PackagingDTO.class);
	}

	public void updatePackagings(List<PackagingDTO> packagings) {
		repository.saveAll(MapperUtil.mapList(packagings, PackagingEntity.class));
	}
	
}
