package com.example.demo.apicontroller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;	
@RestController
@RequestMapping("/api/category")
@PreAuthorize("isAuthenticated()")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseDTO<Integer> add(@RequestBody CategoryDTO p) throws ParseException {
//		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		int id = categoryService.create(p);
		ResponseDTO<Integer>  resp = new ResponseDTO<Integer>();
		resp.setCode(HttpStatus.OK.value());
		resp.setData(id);
		return resp;
	// su dung builder de tao doi tuong thay vi new, cach ngan gon hon
//	return ResponseDTO.<Integer>builder().code(HttpStatus.OK.value()).data(id).build();
}
	@PostMapping("/search")
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	public ResponseDTO<List<CategoryDTO>> search(@RequestBody SearchDTO searchDTO){
		return categoryService.search(searchDTO);
	}
	@GetMapping("/get/{id}")
	public ResponseDTO<CategoryDTO> get(@PathVariable("id") int id){
		return ResponseDTO.<CategoryDTO>builder().code(HttpStatus.OK.value()).data(categoryService.get(id)).build();
	}
	@DeleteMapping("/delete") // ?id=1
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		categoryService.delete(id);
		return ResponseDTO.<Void>builder().code(HttpStatus.OK.value()).build();
	}
	// upload file can @Modelattribute hoac params
		@PutMapping("/edit")
		public ResponseDTO<Void> edit(@RequestBody @Validated CategoryDTO p) throws ParseException {
			categoryService.update(p);
			return ResponseDTO.<Void>builder().code(HttpStatus.OK.value()).build();
		}
	
	
	
}
