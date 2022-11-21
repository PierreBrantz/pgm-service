package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.CertificateEntity;

public interface CertificateRepository extends CrudRepository<CertificateEntity, Long>{ 
	List<CertificateEntity> findByOrderByNameAsc();
	
	/*
	@Query(nativeQuery = true, value = "select last_value from product_label_sequence")
	Integer findSequenceCurrVal();
	*/
}
