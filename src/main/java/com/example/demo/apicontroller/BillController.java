package com.example.demo.apicontroller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BillDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.SearchBillDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.service.BillService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/bill")
@PreAuthorize("isAuthenticated()")
public class BillController {
	@Autowired
	BillService billService;
	
	@Autowired
	UserService userService;

	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseDTO<Integer> add(@RequestBody BillDTO p) throws ParseException {
//			User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		int id = billService.create(p);
		
		
		ResponseDTO<Integer> resp = new ResponseDTO<Integer>();
		resp.setCode(HttpStatus.OK.value());
		resp.setData(id);
		return resp;
		// su dung builder de tao doi tuong thay vi new, cach ngan gon hon
//		return ResponseDTO.<Integer>builder().code(HttpStatus.OK.value()).data(id).build();
	}

	@PostMapping("/search")
	@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
	public ResponseDTO<List<BillDTO>> search(@RequestBody SearchBillDTO searchBillDTO) throws ParseException {
		return billService.search(searchBillDTO);
	}

	@GetMapping("/get/{id}")
	public ResponseDTO<BillDTO> get(@PathVariable("id") int id) {
		return ResponseDTO.<BillDTO>builder().code(HttpStatus.OK.value()).data(billService.get(id)).build();
	}

	@DeleteMapping("/delete") // ?id=1
	public ResponseDTO<Void> delete(@RequestParam("id") int id) {
		billService.delete(id);
		return ResponseDTO.<Void>builder().code(HttpStatus.OK.value()).build();
	}

	// upload file can @Modelattribute hoac params
	@PutMapping("/edit")
	public ResponseDTO<Void> edit(@RequestBody @Validated BillDTO p) throws ParseException {
		billService.update(p);
		return ResponseDTO.<Void>builder().code(HttpStatus.OK.value()).build();
	}

}
