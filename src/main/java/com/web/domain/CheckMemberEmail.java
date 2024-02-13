package com.web.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class CheckMemberEmail {
	@Id
	private String email;
	private String randomInitial;
}
