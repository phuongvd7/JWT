package com.example.demo.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.example.demo.dto.CategoryDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepo;

@Service
public class CategoryService {
	@Autowired
	CategoryRepo categoryRepo;

	public int create(CategoryDTO categoryDTO) {
		Category category = new ModelMapper().map(categoryDTO, Category.class);
		categoryRepo.save(category);
		return category.getId();
	}

	public void update(CategoryDTO categoryDTO) throws ParseException {
		Category newCategory = new ModelMapper().map(categoryDTO, Category.class);
		categoryRepo.save(newCategory);
	}

	public void delete(int id) {
		categoryRepo.deleteById(id);
	}

	public CategoryDTO get(int id) {
		Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(category);
	}

	private CategoryDTO convert(Category category) {
		return new ModelMapper().map(category, CategoryDTO.class);
	}

	public ResponseDTO<List<CategoryDTO>> search(SearchDTO searchDTO) {
		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize());
		Page<Category> pageCategory = categoryRepo.searchByName("%" + searchDTO.getKeyword().toLowerCase() + "%", pageable);
		ResponseDTO<List<CategoryDTO>> resp = new ResponseDTO<List<CategoryDTO>>();

		resp.setCode(HttpStatus.OK.value());
		resp.setTotalPages(pageCategory.getTotalPages());

		List<CategoryDTO> categoryDTOs = new ArrayList<CategoryDTO>();
		for (Category category : pageCategory.getContent()) {
			CategoryDTO categoryDTO = new ModelMapper().map(category, CategoryDTO.class);
			categoryDTOs.add(categoryDTO);
			resp.setData(categoryDTOs);
		}

//		resp.setData(categoryDTOs);
//		resp.setData(pageCategory.get().map(abc -> convert(abc)).collect(Collectors.toList()));

		return resp;
	}

}
