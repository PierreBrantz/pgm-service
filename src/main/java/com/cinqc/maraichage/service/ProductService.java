package com.cinqc.maraichage.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductDTO;
import com.cinqc.maraichage.dto.RealProductDTO;
import com.cinqc.maraichage.exception.ProductNotFoundException;
import com.cinqc.maraichage.model.ProductEntity;
import com.cinqc.maraichage.model.RealQuantityEntity;
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

	public Iterable<ProductDTO> findAllProducts() {
		List<ProductDTO> products = new ArrayList<>();
		List<ProductEntity> productEntities = repository.findByOrderByNameAsc();
		for(ProductEntity productEntity : productEntities) {			
			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(productEntity, ProductDTO.class);
			productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(productDTO.getId()));
			productDTO.setSeason(seasonalityService.findCurrentSeasonality(productEntity));
			products.add(productDTO);
		}
		return products;
	}

	public Iterable<ProductDTO> findProductsByName(String in){
		return MapperUtil.mapList(repository.findByNameContainsIgnoreCase(in), ProductDTO.class);
	}

	public ProductDTO findProductById(Long id) {

		return MapperUtil.getModelMapperInstance().map(
				repository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException(
						String.format("Could not find any product with identifier => '%d'", id))),
				ProductDTO.class);
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
		product.setQuantities(newProduct.getQuantities());
		return MapperUtil.getModelMapperInstance().map(repository.save(MapperUtil.getModelMapperInstance().map(product, ProductEntity.class)),ProductDTO.class);
	}

	public RealProductDTO updateRealProduct(Long productId, Long producerId, RealProductDTO newProduct) {
		RealQuantityEntity realQuantity = newProduct.getRealQuantities().get(0);
		realQuantity.setProducerProductId(producerProductRepository.findByProducerIdAndProductId(producerId,productId).getId());
		return MapperUtil.getModelMapperInstance().map(realQuantityRepository.save(realQuantity),RealProductDTO.class);

	}

}
