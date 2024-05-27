package com.example.demo.repository;

import com.example.demo.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRespository extends JpaRepository<Buy, Long> {
}
