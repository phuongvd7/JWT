package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.BillDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchBillDTO;
import com.example.demo.model.Bill;
import com.example.demo.repository.BillRepo;

@Service
public class BillService {

	@Autowired
	BillRepo billRepo;
	@Autowired
	MailService mailService;
	public int create(BillDTO billDTO) throws ParseException {
		Bill bill = new ModelMapper().createTypeMap(BillDTO.class, Bill.class)
				.addMappings(map -> map.skip(Bill::setBuyDate)).map(billDTO);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bill.setBuyDate(sdf.parse(billDTO.getBuyDate()));
		
		 long currentMilis = new Date().getTime();
		 //convert milis to Date
		 Date date = new Date(currentMilis - 5 * 60 * 1000);
//		mailService.sendEmail(user.getEmail(), "don hang cua ban da duoc tao", "1 hoa don moi da duoc tao");
		 mailService.sendEmail("ducphuong170498@gmail.com", "don hang cua ban da duoc tao", "1 hoa don moi da duoc tao");
		 List<Bill> billList = billRepo.searchByDate(date);
//		 new Thread() {
//				@Override
//				public void run() {
//					mailService.sendEmail("ducphuong170498@gmail.com", "Test", 
//							"Abc zyx");
//				}
//			}.start();
		 
		 
		billRepo.save(bill);
		return bill.getId();
	}

	public void update(BillDTO billDTO) throws ParseException {

		Bill newBill = new ModelMapper().createTypeMap(BillDTO.class, Bill.class)
				.addMappings(map -> map.skip(Bill::setBuyDate)).map(billDTO);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		newBill.setBuyDate(sdf.parse(billDTO.getBuyDate()));
		billRepo.save(newBill);
	}

	public void delete(int id) {
		billRepo.deleteById(id);
	}

	public BillDTO get(int id) {
		Bill bill = billRepo.findById(id).orElseThrow(NoResultException::new);

		BillDTO billDTO = new ModelMapper().map(bill, BillDTO.class);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		billDTO.setBuyDate(sdf.format(bill.getBuyDate()));

		return billDTO;
	}

	public ResponseDTO<List<BillDTO>> search(SearchBillDTO searchBillDTO) throws ParseException {
		Pageable pageable = PageRequest.of(searchBillDTO.getPage(), searchBillDTO.getSize());
		ResponseDTO<List<BillDTO>> resp = new ResponseDTO<List<BillDTO>>();

		if (StringUtils.hasText(searchBillDTO.getUser().getName())) {
			Page<Bill> pageBill = billRepo.searchByName("%" + searchBillDTO.getKeyword().toLowerCase() + "%", pageable);
			resp.setCode(HttpStatus.OK.value());
			resp.setTotalPages(pageBill.getTotalPages());
			List<BillDTO> billDTOs = new ArrayList<BillDTO>();
			for (Bill bill : pageBill.getContent()) {
				BillDTO billDTO = new ModelMapper().map(bill, BillDTO.class);
				billDTO.setBuyDate(new SimpleDateFormat("dd/MM/yyyy").format(bill.getBuyDate()));
				billDTOs.add(billDTO);
			}
			resp.setData(billDTOs);
		} else if (searchBillDTO.getFromDate() != null && searchBillDTO.getToDate() != null
				&& !searchBillDTO.getFromDate().trim().isEmpty() && !searchBillDTO.getToDate().trim().isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Page<Bill> pageBill = billRepo.search_fromDate_toDate(sdf.parse(searchBillDTO.getFromDate()),
					sdf.parse(searchBillDTO.getToDate()), pageable);
			resp.setCode(HttpStatus.OK.value());
			resp.setTotalPages(pageBill.getTotalPages());
			List<BillDTO> billDTOs = new ArrayList<BillDTO>();
			for (Bill bill : pageBill.getContent()) {
				BillDTO billDTO = new ModelMapper().map(bill, BillDTO.class);
				billDTO.setBuyDate(new SimpleDateFormat("dd/MM/yyyy").format(bill.getBuyDate()));
				billDTOs.add(billDTO);
			}
			resp.setData(billDTOs);
		} else if (searchBillDTO.getFromDate() != null && !searchBillDTO.getFromDate().trim().isEmpty()
				&& searchBillDTO.getToDate() == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Page<Bill> pageBill = billRepo.searchByFrom(sdf.parse(searchBillDTO.getFromDate()), pageable);
			resp.setCode(HttpStatus.OK.value());
			resp.setTotalPages(pageBill.getTotalPages());
			List<BillDTO> billDTOs = new ArrayList<BillDTO>();
			for (Bill bill : pageBill.getContent()) {
				BillDTO billDTO = new ModelMapper().map(bill, BillDTO.class);
				billDTO.setBuyDate(new SimpleDateFormat("dd/MM/yyyy").format(bill.getBuyDate()));
				billDTOs.add(billDTO);
			}
			resp.setData(billDTOs);
		} else if (searchBillDTO.getFromDate() == null && searchBillDTO.getToDate() != null
				&& !searchBillDTO.getToDate().trim().isEmpty()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Page<Bill> pageBill = billRepo.searchByTo(sdf.parse(searchBillDTO.getToDate()), pageable);
			resp.setCode(HttpStatus.OK.value());
			resp.setTotalPages(pageBill.getTotalPages());
			List<BillDTO> billDTOs = new ArrayList<BillDTO>();
			for (Bill bill : pageBill.getContent()) {
				BillDTO billDTO = new ModelMapper().map(bill, BillDTO.class);
				billDTO.setBuyDate(new SimpleDateFormat("dd/MM/yyyy").format(bill.getBuyDate()));
				billDTOs.add(billDTO);
			}
			resp.setData(billDTOs);
		}
		return resp;
	}
}
