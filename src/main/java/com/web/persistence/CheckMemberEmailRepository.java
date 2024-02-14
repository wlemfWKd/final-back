package com.web.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.domain.CheckMemberEmail;

public interface CheckMemberEmailRepository extends JpaRepository<CheckMemberEmail, String>{

}
