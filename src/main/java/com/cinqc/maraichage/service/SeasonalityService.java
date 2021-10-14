package com.cinqc.maraichage.service;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.SeasonalityDTO;
import com.cinqc.maraichage.model.ProductEntity;
import com.cinqc.maraichage.model.SeasonalityEntity;
import com.cinqc.maraichage.model.SeasonalityProductEntity;
import com.cinqc.maraichage.repository.SeasonalityRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class SeasonalityService {

	@Autowired
	SeasonalityRepository repository;
	@Autowired
	SeasonalityProductService  seasonalityProductService;


	public Iterable<SeasonalityDTO> findAllSeasons() {
		return MapperUtil.mapList(repository.findByOrderByIdAsc(), SeasonalityDTO.class);
	}

	public List<SeasonalityEntity> findSeasonsByProduct(ProductEntity product){
		List<SeasonalityEntity> seasonalities = new ArrayList<>();
		for(SeasonalityProductEntity sp : product.getSeasonalityProducts()) {
			seasonalities.add(sp.getSeasonality());
		}
		return seasonalities;
	}

	public SeasonalityEntity findCurrentSeasonality(ProductEntity product) {
		Date now = new Date();
		for(SeasonalityProductEntity sp : product.getSeasonalityProducts()) {
			if(now.after(sp.getStartDate()) && now.before(sp.getEndDate())) {
				return sp.getSeasonality();
			}
		}
		return null;
	}

	public float[] getSeasonalityByMonth(ProductEntity product) {
		float[] seasons = {1,1,1,1,1,1,1,1,1,1,1,1};
		List<SeasonalityProductEntity> spEntities = seasonalityProductService.findSeasonalityProductsByProduct(product);
		for(SeasonalityProductEntity season : spEntities) {
			YearMonth startMonth = YearMonth.from(season.getStartDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate());
			YearMonth endMonth = YearMonth.from(season.getEndDate().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate());
			for(int i = startMonth.getMonthValue(); i <= endMonth.getMonthValue(); i++ ) {
				seasons[i - 1]=season.getSeasonality().getPercent();
			}
		}

		return seasons;

	}
}
