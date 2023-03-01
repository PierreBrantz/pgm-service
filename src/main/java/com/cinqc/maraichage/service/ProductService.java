package com.cinqc.maraichage.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
import com.cinqc.maraichage.model.WeeklyProposalEntity;
import com.cinqc.maraichage.repository.ProducerProductRepository;
import com.cinqc.maraichage.repository.ProductRepository;
import com.cinqc.maraichage.repository.RealQuantityRepository;
import com.cinqc.maraichage.repository.WeeklyProposalRepository;
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
	@Autowired
	WeeklyProposalRepository weeklyProposalRepository;

	public List<ProductDTO> findAllProducts(){
		/*
		List<ProductDTO> ret = new ArrayList<>();

		for(ProducerEntity producerEntity : producerService.findAllProducerEntities()) {
			ret = Stream.concat(ret.stream(),findAllProducts(producerEntity.getId()).stream())
                    .collect(Collectors.toList());
		}
		 */
		return MapperUtil.mapList(findAllProducts(null), ProductDTO.class);
	}
	/*
	public List<ProductDTO> findAllProducts(Long producerId){
		List<ProductDTO> ret = new ArrayList<>();
		Iterable<ProductEntity> products = repository.findAll();
		ProducerEntity producer = producerService.findProducerById(producerId);
		for(ProductEntity product : products) {
			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(product, ProductDTO.class);
			ProducerProductEntity ppe = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producerId, product.getId());
			RealQuantityEntity r ;
			if(ppe != null) {
				r = realQuantityRepository.findRealQuantity(ppe.getId());	
				producer.setRealQuantity(r);
				productDTO.getProducers().stream().filter(p->p.getId() == producerId).findAny().ifPresent(p->p.setRealQuantity(r));
				if(productDTO.getProducers().stream().filter(p->p.getId() == producerId).count() == 0 )
				{
					productDTO.getProducers().add(producer);
				}
				productDTO.setCurrentRealQuantity(r);
			}
			else {
				ppe = new ProducerProductEntity();
				ppe.setProductId(product.getId());
				ppe.setProducerId(producer.getId());
				producerProductRepository.save(ppe);
				productDTO.setCurrentRealQuantity(new RealQuantityEntity());
				productDTO.getProducers().stream().filter(p->p.getId() == producerId).findAny().ifPresent(p->p.setRealQuantity(new RealQuantityEntity()));
			}

			productDTO.setSeasonalities(seasonalityService.findAllSeasons());
			productDTO.setSeasonalityProduct(seasonalityProductService.findSeasonalityProductsByProduct(product).size() > 0 ? seasonalityProductService.findSeasonalityProductsByProduct(product).get(0) : new SeasonalityProductEntity());
			productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(product.getId()));	
			ret.add(productDTO);
		}
		return ret;


	}
	 */

	/*
	public Iterable<ProductDTO> findAllProducts(Long producerId) {	
		List<ProductDTO> ret = new ArrayList<>();
		List<ProductEntity> products = new ArrayList<>();
		products = repository.findByOrderByNameAsc();
		if(producerId != null) {	
			ProducerEntity producer = producerService.findProducerById(producerId);
			products = products.stream().filter(o -> (o.getProductLabel() != null ?  o.getProductLabel().getName() : "").equalsIgnoreCase(producer.getCertificate() != null ? producer.getCertificate().getName() : "")).collect(Collectors.toList());
			products = products.stream().filter(o -> (o.getProductType() != null ?  o.getProductType().getName() : "").equalsIgnoreCase(producer.getProducerType() != null ? producer.getProducerType().getName() : "")).collect(Collectors.toList());
			products = products.stream().filter(o -> (o.getProductOrigin() != null ?  o.getProductOrigin().getName() : "").equalsIgnoreCase(producer.getProducerOrigin() != null ? producer.getProducerOrigin().getName() : "")).collect(Collectors.toList());}

		RealQuantityEntity r = null;
		for(ProductEntity product : products) {


			Set<ProducerEntity> producers =new HashSet<>();			
			for(ProducerEntity producer : product.getProducers()) {	
				ProducerProductEntity ppe = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producer.getId(), product.getId());
				if(ppe == null) {
					ppe = new ProducerProductEntity();
					ppe.setProductId(product.getId());
					ppe.setProducerId(producer.getId());
					producerProductRepository.save(ppe);

				}

				r = realQuantityRepository.findRealQuantity(ppe.getId());

				if(r != null) {
					r.setProducerId(producer.getId());
					producer.setRealQuantity(r);


				}

				else {
					r = new RealQuantityEntity();
					r.setYearOfReference(0);
					r.setProducerId(producer.getId());
					r.setProducerProductId(ppe.getId());
					realQuantityRepository.save(r);
					producer.setRealQuantity(r);
				}


				producers.add(producer);
			}
			product.setProducers(producers);

			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(product, ProductDTO.class);
			productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(product.getId()));	
			productDTO.setSeasonalities(seasonalityService.findAllSeasons());
			productDTO.setProductLabels(productLabelService.findAllProductLabels());
			productDTO.setProductOrigins(productOriginService.findAllProductOrigins());
			productDTO.setProductTypes(productTypeService.findAllProductTypes());
			productDTO.setCurrentRealQuantity(r);
			productDTO.setSeasonalityProduct(seasonalityProductService.findSeasonalityProductsByProduct(product).size() > 0 ? seasonalityProductService.findSeasonalityProductsByProduct(product).get(0) : new SeasonalityProductEntity());
			ret.add(productDTO);		
		}



		//return	MapperUtil.mapList(products, ProductDTO.class);
		return ret;
	}
	 */

	public List<ProductDTO> findAllProducts(Long producerId) {	
		List<ProductDTO> ret = new ArrayList<>();
		List<ProductEntity> products = new ArrayList<>();
		products = repository.findByOrderByNameAsc();
		if(producerId != null) {	
			ProducerEntity producer = producerService.findProducerById(producerId);
			products = products.stream().filter(o -> (o.getProductLabel() != null ?  o.getProductLabel().getName() : "").equalsIgnoreCase(producer.getCertificate() != null ? producer.getCertificate().getName() : "")).collect(Collectors.toList());
			products = products.stream().filter(o -> (o.getProductType() != null ?  o.getProductType().getName() : "").equalsIgnoreCase(producer.getProducerType() != null ? producer.getProducerType().getName() : "")).collect(Collectors.toList());
			products = products.stream().filter(o -> (o.getProductOrigin() != null ?  o.getProductOrigin().getName() : "").equalsIgnoreCase(producer.getProducerOrigin() != null ? producer.getProducerOrigin().getName() : "")).collect(Collectors.toList());}
		RealQuantityEntity r = null;
		for(ProductEntity product : products) {
			Set<ProducerEntity> producers =new HashSet<>();			
			for(ProducerEntity producer : product.getProducers()) {	
				r = realQuantityRepository.findRealQuantity(producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producer.getId(), product.getId()).getId());
				if(r != null) {
					r.setProducerId(producer.getId());
					producer.setRealQuantity(r);


				}

				else {
					r = new RealQuantityEntity();
					r.setYearOfReference(0);
					r.setProducerId(producer.getId());
					r.setProducerProductId(producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producer.getId(), product.getId()).getId());
					realQuantityRepository.save(r);
					producer.setRealQuantity(r);
				}

				producers.add(producer);
			}
			product.setProducers(producers);

			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(product, ProductDTO.class);
			productDTO.setRealQuantities(realQuantityRepository.findAllRealQuantityByProductId(product.getId()));	
			productDTO.setSeasonalities(seasonalityService.findAllSeasons());
			productDTO.setProductLabels(productLabelService.findAllProductLabels());
			productDTO.setProductOrigins(productOriginService.findAllProductOrigins());
			productDTO.setProductTypes(productTypeService.findAllProductTypes());
			productDTO.setCurrentRealQuantity(r);
			productDTO.setSeasonalityProduct(seasonalityProductService.findSeasonalityProductsByProduct(product).size() > 0 ? seasonalityProductService.findSeasonalityProductsByProduct(product).get(0) : new SeasonalityProductEntity());
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
		productDTO.setSeasonalities(seasonalityService.findAllSeasons());
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
			List<WeeklyProposalEntity> weeklyProposalEntities = weeklyProposalRepository.findWeeklyProposal(producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producerId, product.getId()).getId());
			ProductDTO productDTO = MapperUtil.getModelMapperInstance().map(product, ProductDTO.class);
			productDTO.setCurrentRealQuantity(realQuantity);	
			productDTO.setWeeklyProposals(weeklyProposalEntities);
			productDTO.setSeasonalities(seasonalityService.findAllSeasons());
			productDTO.setSeasonalityProduct(seasonalityProductService.findSeasonalityProductsByProduct(product).get(0));
			realProducts.add(productDTO);

		}
		return realProducts;
	}

	public Iterable<ProductDTO> findProductsByProducerAbr(String abr){
		return  findProductsByProducer(producerService.findProducerByAbr(abr).getId());
	}





	public ProductDTO updateProduct(long id, ProductDTO newProduct){
	/*
		ProductDTO product = findProductById(id);
		if(newProduct.getQuantities() != null && newProduct.getQuantities().size() > 0) {		
			newProduct.getQuantities().get(0).setProductId(id);
			product.setQuantities(newProduct.getQuantities());

		}

*/

		ProductEntity productEntity = MapperUtil.getModelMapperInstance().map(newProduct, ProductEntity.class);

		return MapperUtil.getModelMapperInstance().map(repository.save(productEntity),ProductDTO.class);
	}

	public List<ProductDTO> updateRealProduct(Long producerId, ProductDTO product) {		
		ProducerProductEntity ppe = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producerId, product.getId());
		if(ppe == null) {
			ppe = new ProducerProductEntity();
			ppe.setProducerId(producerId);
			ppe.setProductId(product.getId());
			producerProductRepository.save(ppe);
		}


		RealQuantityEntity realQuantity = realQuantityRepository.findRealQuantity(ppe.getId());

		Optional<RealQuantityEntity> rqe = product.getRealQuantities().stream().filter(o -> o.getProducerId() == producerId).findFirst();
		if(rqe.get() == null) {
			return null;
		}
		if(realQuantity == null) {

			realQuantity = new RealQuantityEntity(0, ppe.getId(),rqe.get().getQuantity1(), rqe.get().getQuantity2(), rqe.get().getQuantity3(), rqe.get().getQuantity4(),rqe.get().getQuantity5(),rqe.get().getQuantity6(),rqe.get().getQuantity7()
					,rqe.get().getQuantity8(),rqe.get().getQuantity9(),rqe.get().getQuantity10(),rqe.get().getQuantity11(),rqe.get().getQuantity12());

		}
		else {
			realQuantity = rqe.get();
		}
		 realQuantityRepository.save(realQuantity);
		 return findAllProducts();
	}	


	public List<WeeklyProposalEntity> updateWeeklyProposal(Long productId, Long producerId, ProductDTO newProduct) {
		ProducerProductEntity pp = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producerId, productId);

		List<WeeklyProposalEntity> wpes = newProduct.getWeeklyProposals();
		for(WeeklyProposalEntity wpe : wpes) {
			if(wpe != null) {

				if(weeklyProposalRepository.findByProducerProductIdAndDate(pp.getId(), wpe.getDate()) != null) {
					WeeklyProposalEntity change = weeklyProposalRepository.findByProducerProductIdAndDate(pp.getId(), wpe.getDate());
					change.setQuantity(wpe.getQuantity());
					change.setRealQuantity(wpe.getRealQuantity());
					weeklyProposalRepository.save(change);
				}

				else {
					wpe.setProducerProductId(pp.getId());
					weeklyProposalRepository.save(wpe);

				}
			}
		}
		return new ArrayList<WeeklyProposalEntity>();
	}

	public void updateProducts(List<ProductDTO> products) {
		for(ProductDTO product : products) {
			if(product.getSeasonalityProduct() != null) {
				seasonalityProductService.save(product.getSeasonalityProduct());
			}
			for(ProducerEntity producer : product.getProducers()) {
				updateRealProduct(producer.getId(), product);
				
			}
			updateProduct(product.getId(), product);
		}

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
		product = repository.save(product);
		SeasonalityProductEntity sp = new SeasonalityProductEntity();
		sp.setProductId(product.getId());
		seasonalityProductService.save(sp);
		return product;

	}

}
