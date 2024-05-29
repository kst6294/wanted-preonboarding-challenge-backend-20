package com.wanted.preonboarding.module.user.repository;


import com.wanted.preonboarding.module.common.enums.Yn;
import com.wanted.preonboarding.module.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void saveAll(List<Users> users) {

        String sql = "INSERT INTO USERS (PHONE_NUMBER, EMAIL, PASSWORD_HASH, MEMBERSHIP, INSERT_DATE, UPDATE_DATE, INSERT_OPERATOR, UPDATE_OPERATOR, DELETE_YN) " +
                "VALUES (:phoneNumber, :email, :passwordHash, :membership, :insertDate, :updateDate, :insertOperator, :updateOperator, :deleteYn) ";


        List<SqlParameterSource> parameters = new ArrayList<>();

        for (Users user : users) {
            MapSqlParameterSource paramSource = new MapSqlParameterSource();
            paramSource
                    .addValue("insertDate", LocalDateTime.now())
                    .addValue("updateDate", LocalDateTime.now())
                    .addValue("insertOperator", "MASTER")
                    .addValue("updateOperator", "MASTER")
                    .addValue("deleteYn", Yn.N.name())

                    .addValue("phoneNumber", user.getPhoneNumber())
                    .addValue("email", user.getEmail())
                    .addValue("passwordHash", user.getPasswordHash())
                    .addValue("membership", user.getMemberShip().name()


                    );

            parameters.add(paramSource);
        }

        SqlParameterSource[] batch = parameters.toArray(new SqlParameterSource[0]);
        namedParameterJdbcTemplate.batchUpdate(sql, batch);



    }
}
