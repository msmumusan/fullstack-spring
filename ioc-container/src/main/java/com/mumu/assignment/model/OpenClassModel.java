package com.mumu.assignment.model;

import java.util.List;

import com.mumu.assignment.domain.OpenClass;

public interface OpenClassModel {

	List<OpenClass> findByCourse(int courseId);

	OpenClass findById(int id);

	void create(OpenClass openClass);

}
