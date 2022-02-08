package com.cinqc.maraichage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinqc.maraichage.dto.ProducerDTO;
import com.cinqc.maraichage.model.ProducerEntity;
import com.cinqc.maraichage.model.ProductEntity;
import com.cinqc.maraichage.repository.ProducerRepository;
import com.cinqc.maraichage.util.MapperUtil;




@Service
public class ProducerService {

	@Autowired
	ProducerRepository repository;
	
	@Autowired
	ProductService productService;

	public ProducerEntity findProducerById(Long id) {
		return repository.findById(id).get();
	}
	public Iterable<ProducerDTO> findAllProducers() {
		return MapperUtil.mapList(repository.findByOrderByAbrAsc(), ProducerDTO.class);	
	}	
	
	public ProducerDTO addProducer(ProducerDTO producer) {
		return MapperUtil.getModelMapperInstance().map(repository.save(MapperUtil.getModelMapperInstance().map(producer, ProducerEntity.class)),ProducerDTO.class);
	}
	
	public ProducerDTO addProduct(Long producerID, Long productId) {
		ProductEntity product = MapperUtil.getModelMapperInstance().map(productService.findProductById(productId),ProductEntity.class);
		ProducerEntity producer = findProducerById(producerID);
		
		producer.getProducts().add(product);
		
		return MapperUtil.getModelMapperInstance().map(repository.save(producer),ProducerDTO.class);
	}
	

	
	public void deleteById(long producerId) {
		repository.deleteById(producerId);
	}
	
	public ProducerEntity findProducerByAbr(String abr) {
		return repository.findByAbr(abr);
	}
	
	

	
	

}
