package com.wanted.preonboarding.module.product.repository;

import com.wanted.preonboarding.module.common.enums.Yn;
import com.wanted.preonboarding.module.product.entity.Product;
import com.wanted.preonboarding.module.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public void saveAll(List<Product> products) {

        String sql = "INSERT INTO PRODUCT (PRODUCT_NAME, PRICE, PRODUCT_STATUS, QUANTITY, USER_ID,  INSERT_DATE, UPDATE_DATE, INSERT_OPERATOR, UPDATE_OPERATOR, DELETE_YN) " +
                "VALUES (:productName, :price, :productStatus, :quantity, :userId, :insertDate, :updateDate, :insertOperator, :updateOperator, :deleteYn) ";


        List<SqlParameterSource> parameters = new ArrayList<>();

        for (Product product : products) {
            MapSqlParameterSource paramSource = new MapSqlParameterSource();
            paramSource
                    .addValue("insertDate", LocalDateTime.now())
                    .addValue("updateDate", LocalDateTime.now())
                    .addValue("insertOperator", product.getSeller().getEmail())
                    .addValue("updateOperator", product.getSeller().getEmail())
                    .addValue("deleteYn", Yn.N.name())

                    .addValue("userId", product.getSeller().getId())
                    .addValue("productName", product.getProductName())
                    .addValue("price", product.getPrice())
                    .addValue("productStatus", product.getProductStatus().name())
                    .addValue("quantity", product.getQuantity()
                    );

            parameters.add(paramSource);
        }

        SqlParameterSource[] batch = parameters.toArray(new SqlParameterSource[0]);
        namedParameterJdbcTemplate.batchUpdate(sql, batch);



    }
}
