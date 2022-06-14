package com.cinqc.maraichage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProductDTO;
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
@Transactional
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
	@Autowired
	ProducerService producerService; 
	@Autowired
	ProductTypeService productTypeService;
	@Autowired
	ProductOriginService productOriginService;
	@Autowired
	ProductLabelService productLabelService;


	public Iterable<ProductDTO> findAllProducts() {	
		List<ProductDTO> ret = new ArrayList<>();
		List<ProductEntity> products = repository.findByOrderByNameAsc();
		for(ProductEntity product : products) {
			Set<ProducerEntity> producers =new HashSet<>();			
			for(ProducerEntity producer : product.getProducers()) {				
				producer.setRealQuantity(realQuantityRepository.findRealQuantity(producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producer.getId(), product.getId()).getId()));
				producers.add(producer);
			}
			product.setProducers(producers);

			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(product, ProductDTO.class);
			productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(product.getId()));	
			productDTO.setSeasonalities(seasonalityService.findAllSeasons());
			productDTO.setProductLabels(productLabelService.findAllProductLabels());
			productDTO.setProductOrigins(productOriginService.findAllProductOrigins());
			productDTO.setProductTypes(productTypeService.findAllProductTypes());
			
			ret.add(productDTO);		
		}
		
		
	
	//return	MapperUtil.mapList(products, ProductDTO.class);
		return ret;
	}

	public Iterable<ProductDTO> findProductsByName(String in){
		return MapperUtil.mapList(repository.findByNameContainsIgnoreCase(in), ProductDTO.class);
	}

	public ProductDTO findProductById(Long id) {
		ProductEntity productEntity = repository.findById(id).get();
		ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(productEntity, ProductDTO.class);
		productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(productDTO.getId()));

		//	productDTO.setCurrentSeason(seasonalityService.findCurrentSeasonality(productEntity));

		List<ProducerEntity> producers = new ArrayList<>();
		for(ProducerEntity producer : productDTO.getProducers()) {
			ProducerProductEntity pp = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producer.getId(),productDTO.getId());
			producer.setRealQuantity(realQuantityRepository.findRealQuantity(pp.getId()));
			producers.add(producer);
		}
		Collections.sort(producers, new Comparator<ProducerEntity>() {
			@Override
			public int compare(ProducerEntity p1, ProducerEntity p2) {
				return p1.getAbr().compareTo(p2.getAbr());
			}
		});


		productDTO.setProducers(producers);
		//	productDTO.setSeasons(seasonalityService.getSeasonalityByMonth(productEntity));

		return productDTO;
	}


	public Iterable<ProductDTO> findProductsByProducer(Long producerId){

		List<ProductEntity> products = repository.findByProducer(producerId);
		List<ProductDTO> realProducts = new ArrayList<>();

		for(ProductEntity product : products) {					
			RealQuantityEntity realQuantity = realQuantityRepository.findRealQuantity(producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producerId, product.getId()).getId());
			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(product, ProductDTO.class);
			productDTO.setCurrentRealQuantity(realQuantity);			
			realProducts.add(productDTO);

		}
		return realProducts;
	}

	public Iterable<ProductDTO> findProductsByProducerAbr(String abr){
		return  findProductsByProducer(producerService.findProducerByAbr(abr).getId());
	}





	public ProductDTO updateProduct(long id, ProductDTO newProduct){
		ProductDTO product = findProductById(id);
		if(newProduct.getQuantities() != null) {		
			newProduct.getQuantities().get(0).setProductId(id);
			product.setQuantities(newProduct.getQuantities());

		}



		ProductEntity productEntity = MapperUtil.getModelMapperInstance().map(product, ProductEntity.class);

		return MapperUtil.getModelMapperInstance().map(repository.save(productEntity),ProductDTO.class);
	}

	public RealQuantityEntity updateRealProduct(Long productId, Long producerId, ProductDTO newProduct) {


		ProducerProductEntity pp = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producerId, productId);
		RealQuantityEntity rqe = realQuantityRepository.findRealQuantity(pp.getId());
		if(rqe != null) {
			rqe.setQuantity1(newProduct.getCurrentRealQuantity().getQuantity1());
			rqe.setQuantity2(newProduct.getCurrentRealQuantity().getQuantity2());
			rqe.setQuantity3(newProduct.getCurrentRealQuantity().getQuantity3());
			rqe.setQuantity4(newProduct.getCurrentRealQuantity().getQuantity4());
			rqe.setQuantity5(newProduct.getCurrentRealQuantity().getQuantity5());
			rqe.setQuantity6(newProduct.getCurrentRealQuantity().getQuantity6());
			rqe.setQuantity7(newProduct.getCurrentRealQuantity().getQuantity7());
			rqe.setQuantity8(newProduct.getCurrentRealQuantity().getQuantity8());
			rqe.setQuantity9(newProduct.getCurrentRealQuantity().getQuantity9());
			rqe.setQuantity10(newProduct.getCurrentRealQuantity().getQuantity10());
			rqe.setQuantity11(newProduct.getCurrentRealQuantity().getQuantity11());
			rqe.setQuantity12(newProduct.getCurrentRealQuantity().getQuantity12());			
			return realQuantityRepository.save(rqe);
		}
		else {

			RealQuantityEntity realProduct = newProduct.getCurrentRealQuantity();
			realProduct.setProducerProductId(pp.getId());
			return realQuantityRepository.save(realProduct);
		}


	}

	public void updateProducts(List<ProductDTO> products) {
		for(ProductDTO p : products) {
			if(p.getSeasonalityProduct() == null) {
				p.setSeasonalityProduct(new SeasonalityProductEntity());
			}
		}

		repository.saveAll(MapperUtil.mapList(products, ProductEntity.class));
	}


	public static <T> List<T> toList(final Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false)
				.collect(Collectors.toList());
	}

	public ProductDTO addProduct(ProductDTO product) {
		return MapperUtil.getModelMapperInstance().map(repository.save(MapperUtil.getModelMapperInstance().map(product, ProductEntity.class)),ProductDTO.class);
	}

	public void deleteById(long productId) {
		repository.deleteById(productId);
	}

	public ProductEntity addProductWithName(String productName) {
		ProductEntity product = new ProductEntity();
		product.setName(productName);
		return repository.save(product);

	}

}
