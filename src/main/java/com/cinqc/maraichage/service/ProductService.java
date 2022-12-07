package com.cinqc.maraichage.service;

import java.math.BigDecimal;
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
			productDTO.setSeasonalityProduct(seasonalityProductService.findSeasonalityProductsByProduct(product).size() > 0 ? seasonalityProductService.findSeasonalityProductsByProduct(product).get(0) : null);
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
		ProductDTO product = findProductById(id);
		if(newProduct.getQuantities() != null) {		
			newProduct.getQuantities().get(0).setProductId(id);
			product.setQuantities(newProduct.getQuantities());

		}



		ProductEntity productEntity = MapperUtil.getModelMapperInstance().map(product, ProductEntity.class);

		return MapperUtil.getModelMapperInstance().map(repository.save(productEntity),ProductDTO.class);
	}

	public RealQuantityEntity updateRealProduct(Long productId, Long producerId, ProductDTO newProduct) {
		

		 ProducerProductEntity ppTemp = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producerId, productId);
		if(ppTemp == null) {
			ppTemp = new ProducerProductEntity();
			ppTemp.setProducerId(producerId);
			ppTemp.setProductId(productId);
			producerProductRepository.save(ppTemp);
		}
		ProducerProductEntity pp = ppTemp;
		RealQuantityEntity rqe = realQuantityRepository.findRealQuantity(ppTemp.getId());
		if(rqe != null && pp != null) {
			/*
			rqe.setQuantity1((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity1() == null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity1()) : newProduct.getCurrentRealQuantity().getQuantity1());
			rqe.setQuantity2((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity2()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity2()): newProduct.getCurrentRealQuantity().getQuantity2());
			rqe.setQuantity3((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity3()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity3()) : newProduct.getCurrentRealQuantity().getQuantity3());
			rqe.setQuantity4((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity4()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity4()) : newProduct.getCurrentRealQuantity().getQuantity4());
			rqe.setQuantity5((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity5()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity5()) : newProduct.getCurrentRealQuantity().getQuantity5());
			rqe.setQuantity6((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity6()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity6()) : newProduct.getCurrentRealQuantity().getQuantity6());
			rqe.setQuantity7((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity7()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity7()) : newProduct.getCurrentRealQuantity().getQuantity7());
			rqe.setQuantity8((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity8()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity8()) : newProduct.getCurrentRealQuantity().getQuantity8());
			rqe.setQuantity9((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity9()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity9()) : newProduct.getCurrentRealQuantity().getQuantity9());
			rqe.setQuantity10((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity10()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity10()) : newProduct.getCurrentRealQuantity().getQuantity10());
			rqe.setQuantity11((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity11()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity11()) : newProduct.getCurrentRealQuantity().getQuantity11());
			rqe.setQuantity12((newProduct.getCurrentRealQuantity() == null || newProduct.getCurrentRealQuantity().getQuantity12()== null) ? (newProduct.getRealQuantities().size()==0 ? new BigDecimal(0) : newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity12()) : newProduct.getCurrentRealQuantity().getQuantity12());			
			*/
			if(newProduct.getCurrentRealQuantity() != null) {
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
				}
			
			else {
				if(newProduct.getRealQuantities() != null && newProduct.getRealQuantities().size()>0) {
					rqe.setQuantity1(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity1());
					rqe.setQuantity2(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity2());
					rqe.setQuantity3(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity3());
					rqe.setQuantity4(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity4());
					rqe.setQuantity5(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity5());
					rqe.setQuantity6(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity6());
					rqe.setQuantity7(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity7());
					rqe.setQuantity8(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity8());
					rqe.setQuantity9(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity9());
					rqe.setQuantity10(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity10());
					rqe.setQuantity11(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity11());
					rqe.setQuantity12(newProduct.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity12());
					
				}
				
			}
			
			
			return realQuantityRepository.save(rqe);
		}
		else {

			RealQuantityEntity realProduct = ((newProduct.getRealQuantities().size()>0 && newProduct.getRealQuantities().get(0) != null) ? newProduct.getRealQuantities().get(0) :newProduct.getCurrentRealQuantity() );
			realProduct.setProducerProductId(pp.getId());
			return realQuantityRepository.save(realProduct);
		}


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
		for(ProductDTO p : products) {
			if(p.getSeasonalityProduct() == null) {
				p.setSeasonalityProduct(new SeasonalityProductEntity());
			}
			for(ProducerEntity producer : p.getProducers()) {
				ProducerProductEntity pp = producerProductRepository.findTopByProducerIdAndProductIdOrderByIdDesc(producer.getId(), p.getId());
				RealQuantityEntity rqe = realQuantityRepository.findRealQuantity(pp.getId());
				if(rqe != null) {
					/*
					rqe.setQuantity1((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity1() == null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity1()) : p.getCurrentRealQuantity().getQuantity1());
					rqe.setQuantity2((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity2()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity2()): p.getCurrentRealQuantity().getQuantity2());
					rqe.setQuantity3((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity3()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity3()) : p.getCurrentRealQuantity().getQuantity3());
					rqe.setQuantity4((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity4()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity4()) : p.getCurrentRealQuantity().getQuantity4());
					rqe.setQuantity5((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity5()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity5()) : p.getCurrentRealQuantity().getQuantity5());
					rqe.setQuantity6((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity6()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity6()) : p.getCurrentRealQuantity().getQuantity6());
					rqe.setQuantity7((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity7()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity7()) : p.getCurrentRealQuantity().getQuantity7());
					rqe.setQuantity8((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity8()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity8()) : p.getCurrentRealQuantity().getQuantity8());
					rqe.setQuantity9((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity9()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity9()) : p.getCurrentRealQuantity().getQuantity9());
					rqe.setQuantity10((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity10()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity10()) : p.getCurrentRealQuantity().getQuantity10());
					rqe.setQuantity11((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity11()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity11()) : p.getCurrentRealQuantity().getQuantity11());
					rqe.setQuantity12((p.getCurrentRealQuantity() == null || p.getCurrentRealQuantity().getQuantity12()== null) ? (p.getRealQuantities().size()==0 ? new BigDecimal(0) : p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity12()) : p.getCurrentRealQuantity().getQuantity12());			
					*/
					if(p.getRealQuantities() != null && p.getRealQuantities().size()>0) {
						rqe.setQuantity1(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity1());
						rqe.setQuantity2(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity2());
						rqe.setQuantity3(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity3());
						rqe.setQuantity4(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity4());
						rqe.setQuantity5(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity5());
						rqe.setQuantity6(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity6());
						rqe.setQuantity7(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity7());
						rqe.setQuantity8(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity8());
						rqe.setQuantity9(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity9());
						rqe.setQuantity10(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity10());
						rqe.setQuantity11(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity11());
						rqe.setQuantity12(p.getRealQuantities().stream().filter(o -> o.getProducerProductId() == pp.getId()).findFirst().get().getQuantity12());
						
					}
					else {
						if(p.getCurrentRealQuantity() != null) {
						rqe.setQuantity1(p.getCurrentRealQuantity().getQuantity1());
						rqe.setQuantity2(p.getCurrentRealQuantity().getQuantity2());
						rqe.setQuantity3(p.getCurrentRealQuantity().getQuantity3());
						rqe.setQuantity4(p.getCurrentRealQuantity().getQuantity4());
						rqe.setQuantity5(p.getCurrentRealQuantity().getQuantity5());
						rqe.setQuantity6(p.getCurrentRealQuantity().getQuantity6());
						rqe.setQuantity7(p.getCurrentRealQuantity().getQuantity7());
						rqe.setQuantity8(p.getCurrentRealQuantity().getQuantity8());
						rqe.setQuantity9(p.getCurrentRealQuantity().getQuantity9());
						rqe.setQuantity10(p.getCurrentRealQuantity().getQuantity10());
						rqe.setQuantity11(p.getCurrentRealQuantity().getQuantity11());
						rqe.setQuantity12(p.getCurrentRealQuantity().getQuantity12());
						}
					}
					realQuantityRepository.save(rqe);
				}
				else {

					RealQuantityEntity realProduct = ((p.getRealQuantities().size()>0 && p.getRealQuantities().get(0) != null) ? p.getRealQuantities().get(0) :p.getCurrentRealQuantity() );
		
					realProduct.setProducerProductId(pp.getId());
					 realQuantityRepository.save(realProduct);
				}
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
