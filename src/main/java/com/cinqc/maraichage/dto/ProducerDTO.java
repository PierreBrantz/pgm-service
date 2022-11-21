package com.cinqc.maraichage.dto;

import com.cinqc.maraichage.model.CertificateEntity;
import com.cinqc.maraichage.model.ProducerTypeEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProducerDTO {
	
	private long id;
		
	private String name;
	
	private String firstName;
	
	private String abr;
	
	private String company;

	private String road;
	
	private String number;

	private String postCode;

	private String town;

	private String phone;

	private String mail;

	private String account;

	private String tva;

	private String gsm;
	
	private CertificateEntity certificate;
	
	private ProducerTypeEntity producerType;
}
