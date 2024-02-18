package com.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.CountingStar;
import com.web.persistence.StarRepository;

@Service
public class StarService {

	@Autowired
	private StarRepository stRepository;
	
	public void saveStar(CountingStar star) {
		stRepository.save(star);
	}
	
	public void deleteStar(Long seq) {
		stRepository.deleteById(seq);
	}
	
	public List<CountingStar> getAllStar() {
		return stRepository.findAll();
	}
	
}
