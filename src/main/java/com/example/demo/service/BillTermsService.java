package com.example.demo.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BillTermsDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.model.BillTerms;
import com.example.demo.repository.BillRepo;
import com.example.demo.repository.BillTermsRepo;
import com.example.demo.repository.ProductRepo;
@Service
public class BillTermsService {

	@Autowired
	BillRepo billRepo;
	@Autowired
	BillTermsRepo billTermsRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	public int create(BillTermsDTO billTermsDTO) {
		BillTerms billTerms = new ModelMapper().createTypeMap(BillTermsDTO.class, BillTerms.class)
				.addMappings(map -> map.skip(BillTerms::setPrice)).map(billTermsDTO);
		double price = productRepo.getById(billTermsDTO.getProduct().getId()).getPrice() * billTermsDTO.getQuantity();
		billTermsRepo.save(billTerms);
		return billTerms.getId();
	}
	public void update(BillTermsDTO billTermsDTO) throws ParseException {
		BillTerms billTerms = new ModelMapper().map(billTermsDTO, BillTerms.class);
		billTermsRepo.save(billTerms);
	}

	public void delete(int id) {
		billTermsRepo.deleteById(id);
	}

	public BillTermsDTO get(int id) {
		BillTerms billTerms = billTermsRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(billTerms);
	}

	private BillTermsDTO convert(BillTerms billTerms) {
		return new ModelMapper().map(billTerms, BillTermsDTO.class);
	}

	public ResponseDTO<List<BillTermsDTO>> search(SearchDTO searchDTO) {
		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize());
		Page<BillTerms> pageBillTerms = billTermsRepo.searchNameProduct("%" + searchDTO.getKeyword().toLowerCase() + "%", pageable);
		ResponseDTO<List<BillTermsDTO>> resp = new ResponseDTO<List<BillTermsDTO>>();

		resp.setCode(HttpStatus.OK.value());
		resp.setTotalPages(pageBillTerms.getTotalPages());

		List<BillTermsDTO> billTermsDTOs = new ArrayList<BillTermsDTO>();
		for (BillTerms billTerms : pageBillTerms.getContent()) {
			BillTermsDTO billTermsDTO = new ModelMapper().map(billTerms, BillTermsDTO.class);
			billTermsDTOs.add(billTermsDTO);
			resp.setData(billTermsDTOs);
		}

//		resp.setData(categoryDTOs);
//		resp.setData(pageCategory.get().map(abc -> convert(abc)).collect(Collectors.toList()));

		return resp;
	}

}

