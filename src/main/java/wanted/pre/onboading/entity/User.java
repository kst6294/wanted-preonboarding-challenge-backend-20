package wanted.pre.onboading.entity;

import jakarta.persistence.Entity;

@Entity
public class User {

    private Integer userId;

    private String loginId;

    private String password;
}
