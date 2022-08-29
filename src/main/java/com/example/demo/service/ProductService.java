package com.example.demo.service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;

@Service
public class ProductService {
	@Autowired
	ProductRepo productRepo;
	
	public int create(ProductDTO productDTO) throws ParseException {
		Product product = new ModelMapper().map(productDTO, Product.class);
		productRepo.save(product);
		return product.getId();
	}
	
	public void update(ProductDTO productDTO) throws ParseException {
		Product product = new ModelMapper().map(productDTO, Product.class);
		productRepo.save(product);
	}
	
	public void delete(int id) {
		productRepo.deleteById(id);
	}
	
	public ProductDTO get(int id) {
		Product product = productRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(product);
	}
	private ProductDTO convert(Product product) {
		return new ModelMapper().map(product, ProductDTO.class);
	}
	
	public ResponseDTO<List<ProductDTO>> search(SearchDTO searchDTO){
		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize());
		
		Page<Product> pageCategory = productRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageable);
	
		ResponseDTO<List<ProductDTO>> resp = new ResponseDTO<List<ProductDTO>>();
		
		resp.setCode(HttpStatus.OK.value());
		
		resp.setTotalPages(pageCategory.getTotalPages());
		
		resp.setData(pageCategory.get().map(a -> convert(a)).collect(Collectors.toList()));
		
		return resp; 
	}
}
