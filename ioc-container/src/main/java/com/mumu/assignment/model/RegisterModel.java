package com.mumu.assignment.model;

import java.util.List;

import com.mumu.assignment.domain.Registration;

public interface RegisterModel {
	List<Registration> findByOpenClassId(int openClassId);
	
//	List<Registration> getAll();
	
	Registration findById(int id);

	void register(Registration registration);
}
