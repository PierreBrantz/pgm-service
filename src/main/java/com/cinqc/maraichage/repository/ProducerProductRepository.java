package com.cinqc.maraichage.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cinqc.maraichage.model.ProducerProductEntity;

public interface ProducerProductRepository extends CrudRepository<ProducerProductEntity, Long>{
	ProducerProductEntity findTopByProducerIdAndProductIdOrderByIdDesc(Long producerId, Long productId);
	
	List<ProducerProductEntity> findByProductId(Long productId);
	
	
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(nativeQuery = true,value = "delete from producer_product pp where product_id = :productId and producer_id = :producerId")
	void deleteProduct(long producerId, long productId);

	
}
