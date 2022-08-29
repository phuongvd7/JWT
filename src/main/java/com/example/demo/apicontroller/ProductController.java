package com.example.demo.apicontroller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.service.ProductService;

@RestController
@RequestMapping("/api/product")
@PreAuthorize("isAuthenticated()")
public class ProductController {
	@Autowired
	ProductService productService;
	
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseDTO<Integer> add(@RequestBody ProductDTO p) throws ParseException{
		int id = productService.create(p);
		ResponseDTO<Integer>  resp = new ResponseDTO<Integer>();
		resp.setCode(HttpStatus.OK.value());
		resp.setData(id);
		return resp;
	}
	
	@PostMapping("/search")
	@PreAuthorize("hasAnyAuthority('ADMIN','USER','PM')")
	public ResponseDTO<List<ProductDTO>> search(@RequestBody SearchDTO searchDTO){
		return productService.search(searchDTO);
	}
	
	@GetMapping("/get/{id}")
	public ResponseDTO<ProductDTO> get(@PathVariable("id") int id){
		return ResponseDTO.<ProductDTO>builder().code(HttpStatus.OK.value()).data(productService.get(id)).build();
	}
	@DeleteMapping("/delete")
	public ResponseDTO<Void> delete(@RequestParam("id") int id){
		productService.delete(id);
		return ResponseDTO.<Void>builder().code(HttpStatus.OK.value()).build();
	}
	@PutMapping("/edit")
	public ResponseDTO<Void> edit(@RequestBody @Valid ProductDTO p) throws ParseException {
		productService.update(p);
		return ResponseDTO.<Void>builder().code(HttpStatus.OK.value()).build();
	}
} 














