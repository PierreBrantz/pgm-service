package com.cinqc.maraichage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductDTO;
import com.cinqc.maraichage.dto.RealProductDTO;
import com.cinqc.maraichage.model.ProducerEntity;
import com.cinqc.maraichage.model.ProducerProductEntity;
import com.cinqc.maraichage.model.ProductEntity;
import com.cinqc.maraichage.model.RealQuantityEntity;
import com.cinqc.maraichage.model.SeasonalityProductEntity;
import com.cinqc.maraichage.repository.ProducerProductRepository;
import com.cinqc.maraichage.repository.ProductRepository;
import com.cinqc.maraichage.repository.RealQuantityRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProductService {

	@Autowired
	ProductRepository repository;
	@Autowired
	RealQuantityRepository realQuantityRepository;
	@Autowired
	ProducerProductRepository producerProductRepository;
	@Autowired
	SeasonalityService seasonalityService;
	@Autowired
	SeasonalityProductService  seasonalityProductService;


	public Iterable<ProductDTO> findAllProducts() {
		List<ProductDTO> products = new ArrayList<>();
		List<ProductEntity> productEntities = repository.findByOrderByNameAsc();
		for(ProductEntity productEntity : productEntities) {			
			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(productEntity, ProductDTO.class);
			productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(productDTO.getId()));
			productDTO.setCurrentSeason(seasonalityService.findCurrentSeasonality(productEntity));
						products.add(productDTO);
		}
		return products;
	}

	public Iterable<ProductDTO> findProductsByName(String in){
		return MapperUtil.mapList(repository.findByNameContainsIgnoreCase(in), ProductDTO.class);
	}

	public ProductDTO findProductById(Long id) {
		ProductEntity productEntity = repository.findById(id).get();
		ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(productEntity, ProductDTO.class);
		productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(productDTO.getId()));

		productDTO.setCurrentSeason(seasonalityService.findCurrentSeasonality(productEntity));

		List<ProducerEntity> producers = new ArrayList<>();
		for(ProducerEntity producer : productDTO.getProducers()) {
			ProducerProductEntity pp = producerProductRepository.findByProducerIdAndProductId(producer.getId(),productDTO.getId());
			producer.setRealQuantities(realQuantityRepository.findRealQuantity(pp.getId()));
			producers.add(producer);
		}
		Collections.sort(producers, new Comparator<ProducerEntity>() {
			@Override
			public int compare(ProducerEntity p1, ProducerEntity p2) {
				return p1.getAbr().compareTo(p2.getAbr());
			}
		});



		List<SeasonalityProductEntity> seasonalityProductEntities = seasonalityProductService.findSeasonalityProductsByProduct(productEntity);
		Collections.sort(seasonalityProductEntities, new Comparator<SeasonalityProductEntity>() {
			@Override
			public int compare(SeasonalityProductEntity p1, SeasonalityProductEntity p2) {

				return Long.valueOf(p1.getSeasonalityId()).compareTo(Long.valueOf(p2.getSeasonalityId()));
			}
		});
		productDTO.setSeasonInformation(seasonalityProductEntities);

		productDTO.setProducers(producers);
		productDTO.setSeasons(seasonalityService.getSeasonalityByMonth(productEntity));

		return productDTO;
	}


	public Iterable<RealProductDTO> findProductsByProducer(Long producerId){

		List<ProductEntity> products = repository.findByProducer(producerId);
		List<ProductEntity> realProducts = new ArrayList<>();


		for(ProductEntity product : products) {			
			List<RealQuantityEntity> realQuantity = realQuantityRepository.findRealQuantity(producerProductRepository.findByProducerIdAndProductId(producerId, product.getId()).getId());
			product.setRealQuantities(new HashSet<>(realQuantity));
			product.setProducerId(producerId);
			realProducts.add(product);


		}
		return MapperUtil.mapList(realProducts,RealProductDTO.class);
	}



	public ProductDTO updateProduct(long id, ProductDTO newProduct){
		ProductDTO product = findProductById(id);
		if(newProduct.getQuantities() != null) {		
			newProduct.getQuantities().get(0).setProductId(product.getId());
			product.setQuantities(newProduct.getQuantities());

		}



		ProductEntity productEntity = MapperUtil.getModelMapperInstance().map(product, ProductEntity.class);
		Set<SeasonalityProductEntity> spEntitiesToChange = new HashSet<>();
		if(newProduct.getSeasonInformation() != null && newProduct.getSeasonInformation().size()>0) {

			List<SeasonalityProductEntity> spEntities = seasonalityProductService.findSeasonalityProductsByProduct(productEntity);
			if(spEntities != null && spEntities.size()>0) {
				for(SeasonalityProductEntity spEntity : spEntities) {
					if(spEntity.getSeasonalityId() == 1) {
						spEntity.setStartDate(newProduct.getSeasonInformation().get(0).getStartDate());
						spEntity.setEndDate(newProduct.getSeasonInformation().get(0).getEndDate());
					}
					if(spEntity.getSeasonalityId() == 2) {
						spEntity.setStartDate(newProduct.getSeasonInformation().get(1).getStartDate() );
						spEntity.setEndDate(newProduct.getSeasonInformation().get(1).getEndDate() );
					}
					if(spEntity.getSeasonalityId() == 3) {
						spEntity.setStartDate(newProduct.getSeasonInformation().get(2).getStartDate());
						spEntity.setEndDate(newProduct.getSeasonInformation().get(2).getEndDate());
					}
					spEntitiesToChange.add(spEntity);
				}
			}

		}
		productEntity = MapperUtil.getModelMapperInstance().map(product, ProductEntity.class);
		productEntity.setSeasonalityProducts(spEntitiesToChange);

		return MapperUtil.getModelMapperInstance().map(repository.save(productEntity),ProductDTO.class);
	}

	public RealProductDTO updateRealProduct(Long productId, Long producerId, RealProductDTO newProduct) {
		RealQuantityEntity realQuantity = newProduct.getRealQuantities().get(0);
		realQuantity.setProducerProductId(producerProductRepository.findByProducerIdAndProductId(producerId,productId).getId());

		RealProductDTO realProductDTO = MapperUtil.getModelMapperInstance().map(realQuantityRepository.save(realQuantity),RealProductDTO.class);
		realProductDTO.setSeasons(seasonalityService.getSeasonalityByMonth(repository.findById(productId).get()));
		return realProductDTO;

	}


	public static <T> List<T> toList(final Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false)
				.collect(Collectors.toList());
	}

}
