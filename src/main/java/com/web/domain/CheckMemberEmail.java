package com.web.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "email")
public class CheckMemberEmail {
	@Id
	private String email;
	private String randomInitial;
}
