package com.cinqc.maraichage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="packaging")
public class PackagingEntity {
	@Column(name = "id")
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "name")
	private String name;

}
