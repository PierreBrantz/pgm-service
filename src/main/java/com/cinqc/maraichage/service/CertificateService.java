package com.cinqc.maraichage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.CertificateDTO;
import com.cinqc.maraichage.repository.CertificateRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class CertificateService {

	@Autowired
	CertificateRepository repository;



	public List<CertificateDTO> findAllCertificates() {
		return MapperUtil.mapList(repository.findByOrderByNameAsc(), CertificateDTO.class);
	}

	/*
	public void updateProductLabels(List<ProductLabelDTO> productLabels) {
		repository.saveAll(MapperUtil.mapList(productLabels, ProductLabelEntity.class));
	}
	
	public void deleteProductLabel(Long id) {
		repository.deleteById(id);
	}
	
	public Integer findSequenceCurrVal() {
		return repository.findSequenceCurrVal();
	}
	*/
}
