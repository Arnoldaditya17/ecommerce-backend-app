package com.aditya.product.services;

import com.aditya.common.exceptions.ResourceNotFoundException;
import com.aditya.common.utils.EntityDtoMapper;
import com.aditya.product.dtos.ProductDto;
import com.aditya.product.models.CategoryEntity;
import com.aditya.product.models.ProductEntity;
import com.aditya.product.repositories.CategoryRepository;
import com.aditya.product.repositories.ProductRepository;
import com.aditya.product.utils.ProductCsvRepresentation;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

	private final EntityDtoMapper entityDtoMapper;

	private final ProductRepository productRepository;

	private final ModelMapper modelMapper;

	private final CategoryRepository categoryRepository;

	public ProductServiceImpl(ProductRepository productRepository, EntityDtoMapper entityDtoMapper, ModelMapper modelMapper,
			CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.entityDtoMapper = entityDtoMapper;
		this.modelMapper = modelMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public ProductDto addProduct(ProductDto productDto) {
		ProductEntity savedProduct = productRepository.save(modelMapper.map(productDto, ProductEntity.class));
		return modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public Page<ProductDto> getAllProduct(Pageable pageable) {
		Page<ProductEntity> products = productRepository.findAll(pageable);
		List<ProductDto> dtoList = products.getContent().stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
		return new PageImpl<>(dtoList, pageable, products.getTotalElements());
	}

	@Override
	public void addCategoryToProduct(UUID productId, UUID categoryId) {
		ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", productId));
		CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found !!", categoryId));
		product.addCategory(category);
		productRepository.save(product);
	}

	@Override
	public void removeCategoryFromProduct(UUID productId, UUID categoryId) {
		ProductEntity product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", productId));
		CategoryEntity category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Not found !!", categoryId));
		product.removeCategory(category);
		productRepository.save(product);
	}

	@Override
	public ProductDto updateProduct(ProductDto productDto, UUID id) {
		ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", id));
		modelMapper.map(productDto, product);
		ProductEntity updatedProduct = productRepository.save(product);
		return entityDtoMapper.toDto(updatedProduct, ProductDto.class);
	}

	@Override
	public ProductDto getProductById(UUID id) {
		ProductEntity product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", id));
		return entityDtoMapper.toDto(product, ProductDto.class);
	}

	@Override
	public void deleteProductById(UUID id) {
		productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not found !!", id));
		productRepository.deleteById(id);

	}

	@Override
	public void deleteAllProducts() {
		productRepository.deleteAll();
	}

	@Override
	public List<ProductDto> searchProduct(String keyword) {
		List<ProductEntity> productEntityList = productRepository.findByName(keyword);
		return productEntityList.stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
	}

	@Override
	public Integer uploadFile(MultipartFile file) throws IOException {
		Set<String> existingSkus = productRepository.findAllSkus();
		Set<ProductDto> productList = parseCsv(file, existingSkus);
		Set<ProductEntity> productEntities = productList.stream().map(productDto -> modelMapper.map(productDto, ProductEntity.class))
				.collect(Collectors.toSet());
		productRepository.saveAll(productEntities);
		return productList.size();
	}

	private Set<ProductDto> parseCsv(MultipartFile file, Set<String> existingSkus) throws IOException {
		try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
			HeaderColumnNameMappingStrategy<ProductCsvRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
			strategy.setType(ProductCsvRepresentation.class);
			CsvToBean<ProductCsvRepresentation> csvToBean = new CsvToBeanBuilder<ProductCsvRepresentation>(reader).withMappingStrategy(strategy)
					.withIgnoreEmptyLine(true).withIgnoreLeadingWhiteSpace(true).build();
			return csvToBean.parse().stream().filter(csvLine -> !existingSkus.contains(csvLine.getSkuCode())).map(csvLine -> {
				ProductDto product = new ProductDto();
				product.setName(csvLine.getName());
				product.setDescription(csvLine.getDescription());
				product.setSkuCode(csvLine.getSkuCode());
				product.setMarketPrice(csvLine.getMarketPrice());
				product.setSalePrice(csvLine.getSalePrice());
				product.setCreatedAt(new Date());
				product.setUpdatedAt(new Date());
				return product;
			}).collect(Collectors.toSet());
		}
	}

}
