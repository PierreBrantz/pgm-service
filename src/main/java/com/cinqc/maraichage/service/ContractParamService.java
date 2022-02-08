package com.cinqc.maraichage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.model.ContractParamEntity;
import com.cinqc.maraichage.repository.ContractParamRepository;




@Service
public class ContractParamService {

	@Autowired
	ContractParamRepository repository;



	public ContractParamEntity findContractParams() {
		return repository.findById(1L).get();
	}

	public void updateContractParam(ContractParamEntity contractParam) {
		repository.save(contractParam);
	}
}
