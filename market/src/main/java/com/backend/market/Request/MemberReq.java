package com.backend.market.Request;

import com.backend.market.DAO.Entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberReq {

    String logInId;

    String name;

    String password;

    List<Product> productList;
}
