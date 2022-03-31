package com.mumu.assignment.model;

import java.util.List;

import com.mumu.assignment.domain.Course;

public interface CourseModel {

	List<Course> getAll();
	void save(Course course);
	Course findById(int id);
}