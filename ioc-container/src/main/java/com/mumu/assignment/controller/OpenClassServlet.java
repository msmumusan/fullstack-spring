package com.mumu.assignment.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mumu.assignment.domain.Course;
import com.mumu.assignment.domain.OpenClass;
import com.mumu.assignment.model.CourseModel;
import com.mumu.assignment.model.OpenClassModel;

@WebServlet(urlPatterns = { "/classes", "/class-edit" })
public class OpenClassServlet extends AbstractBeanFactoryServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		var courseId = req.getParameter("courseId");
		// Find Course
		var courseModel = getBean("courseModel", CourseModel.class);
		var course = courseModel.findById(Integer.parseInt(courseId));

		req.setAttribute("course", course);

		var page = switch (req.getServletPath()) {
		case "/classes" -> {
			var model = getBean("openClassModel", OpenClassModel.class);
			req.setAttribute("classes", model.findByCourse(Integer.parseInt(courseId)));
			yield "classes";
		}
		default -> "class-edit";
		};

		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get request parameter
		var name = req.getParameter("name");
		var courseId = req.getParameter("courseId");
		var start_date = req.getParameter("startDate");
		var teacher = req.getParameter("teacherName");

		System.out.println("Getting courseId:::" + req.getParameter("courseId"));
		System.out.println("getting date::::: " + req.getParameter("startDate"));

		// Find Course
		var courseModel = getBean("courseModel", CourseModel.class);
		var course = courseModel.findById(Integer.parseInt(courseId));
		

		LocalDate localDate = LocalDate.parse(start_date);

		System.out.println("Parsing to LocalDate::::: " + LocalDate.parse(start_date));

		// create openclass object
		var openClass = new OpenClass();
		openClass.setCourse(course);
		openClass.setStartDate(localDate);
		openClass.setTeacher(teacher);

		// save to db
		getBean("openClassModel", OpenClassModel.class).create(openClass);

		// redirect to top page
		resp.sendRedirect("/");
	}

}
