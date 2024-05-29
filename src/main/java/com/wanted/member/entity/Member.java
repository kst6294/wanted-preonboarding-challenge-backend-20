package com.wanted.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	String name;
	String password;

}
