package com.cinqc.maraichage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cinqc.maraichage.model.RealQuantityEntity;

public interface RealQuantityRepository extends CrudRepository<RealQuantityEntity, Long>{ 

		
	@Query(nativeQuery = true, value = "select rq.* from real_quantity rq where rq.producer_product_id = :ppId")
	RealQuantityEntity findRealQuantity(@Param("ppId") Long ppId);
	
	@Query(nativeQuery = true, value = "select rq.* from real_quantity rq inner join producer_product pp on rq.producer_product_id = pp.id where pp.product_id = :productId")
	List<RealQuantityEntity> findAllRealQuantityByProductId(@Param("productId") Long productId);
	
	
}
