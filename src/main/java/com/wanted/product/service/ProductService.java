package com.wanted.product.service;

import com.wanted.product.dto.ProductDTO;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


public interface ProductService {

	List<Object> list();

	List<Object> purchaseList();

	List<Object> reserveList();

	ProductDTO readOne(Long id);

}
