package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cinqc.maraichage.model.ProductEntity;




public interface ProductRepository extends CrudRepository<ProductEntity, Long>{ 
	List<ProductEntity> findByName(String name);

	List<ProductEntity> findByNameContainsIgnoreCase(String in);

	//List<ProductEntity> findByCertificateIdIgnoreCaseOrderByNameAsc(String certificateId);

	List<ProductEntity> findByOrderByNameAsc();
	
	@Query(nativeQuery = true,value = "select p.* from producer_product pp \n"
			+ "	inner join product p on p.id = pp.product_id where pp.producer_id = :producerId order by p.name")
	List<ProductEntity> findByProducer(@Param("producerId") Long producerId);




	
}
