package com.cinqc.maraichage.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cinqc.maraichage.model.WeeklyProposalEntity;

public interface WeeklyProposalRepository extends CrudRepository<WeeklyProposalEntity, Long>{

	@Query(nativeQuery = true, value = "select wp.* from weekly_proposal wp where wp.producer_product_id = :ppId")
	List<WeeklyProposalEntity> findWeeklyProposal(@Param("ppId") Long ppId);
	
	WeeklyProposalEntity findByProducerProductIdAndDate(long ppId, Date date);
}
