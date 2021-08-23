package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cinqc.maraichage.model.RequestEntity;

public interface RequestRepository extends CrudRepository<RequestEntity, Long>{ 
//	List<RequestEntity> findByName(String name);
	
	List<RequestEntity> findByNameContainsIgnoreCase(String in);
	
	RequestEntity findByName(String name);
	
	
	@Query(
			  value = "SELECT * FROM request r WHERE extract(month from r.start_period) = :month + 1", 
			  nativeQuery = true)
	List<RequestEntity> findByMonth(@Param("month") int month);
		
	
	

}
