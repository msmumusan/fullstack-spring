package com.mumu.assignment.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mumu.assignment.domain.OpenClass;
import com.mumu.assignment.domain.Registration;
import com.mumu.assignment.model.CourseModel;
import com.mumu.assignment.model.OpenClassModel;
import com.mumu.assignment.model.RegisterModel;

@WebServlet(urlPatterns = { "/registration", "/registration-edit" })
public class RegistrationServlet extends AbstractBeanFactoryServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var openClassId = req.getParameter("openClassId");

		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findById(Integer.parseInt(openClassId));

		req.setAttribute("openClass", openClass);
		var page = switch (req.getServletPath()) {
		case "/registration" -> {
			var model = getBean("registerModel", RegisterModel.class);
			req.setAttribute("registration", model.findByOpenClassId(Integer.parseInt(openClassId)));
//			req.setAttribute("registration", model.getAll());

			yield "registration";
		}
		default -> "registration-edit";
		};

		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// get request parameter
		var openClassId = req.getParameter("openClassId");
		var studentName = req.getParameter("studentName");
		var phone = req.getParameter("phone");
		var email = req.getParameter("email");

		System.out.println("Getting openClassId:::" + req.getParameter("openClassId").toString());

		// Find OpenClass
		var openClassModel = getBean("openClassModel", OpenClassModel.class);
		var openClass = openClassModel.findById(Integer.parseInt(openClassId));

		System.out.println("Find Open Class by openClassId ::: " + openClass);

		// register
		var register = new Registration();
		register.setOpenClass(openClass);
		register.setStudent(studentName);
		register.setPhone(phone);
		register.setEmail(email);

		// save to db
		getBean("registerModel", RegisterModel.class).register(register);

		// redirect to top page
		resp.sendRedirect("/");
	}

}
