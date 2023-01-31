package com.cinqc.maraichage.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="producer")
public class ProducerEntity {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;	

	@Column(name = "firstname")
	private String firstName;	

	@Column(name = "abr")
	private String abr;	
	
	@Column(name = "company")
	private String company;
	
	@Column(name = "road")
	private String road;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "post_code")
	private String postCode;
	
	@Column(name = "town")
	private String town;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "mail")
	private String mail;
	
	@Column(name = "account")
	private String account;
	
	@Column(name = "tva")
	private String tva;
	
	@Column(name = "gsm")
	private String gsm;
	
	@JsonIgnore
	@ManyToMany(cascade= {CascadeType.PERSIST})
	@JoinTable(
	        name = "producer_product", 
	        joinColumns = { @JoinColumn(name = "producer_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "product_id") }
	    )
	 private Set<ProductEntity> products = new HashSet<>();
	
	@ManyToOne
    @JoinColumn(name = "certificate_id", referencedColumnName = "id")
	private CertificateEntity certificate;
	
	@ManyToOne
    @JoinColumn(name = "producer_type_id", referencedColumnName = "id")
	private ProducerTypeEntity producerType;
	
	@ManyToOne
    @JoinColumn(name = "producer_origin_id", referencedColumnName = "id")
	private ProducerOriginEntity producerOrigin;
	
	@JsonInclude()
	@Transient
	private RealQuantityEntity realQuantity;

}
