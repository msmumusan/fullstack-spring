package com.mumu.assignment.model.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.mumu.assignment.domain.Course;
import com.mumu.assignment.domain.OpenClass;
import com.mumu.assignment.domain.Registration;
import com.mumu.assignment.model.RegisterModel;

public class RegisterModelImpl implements RegisterModel {

	private static final String SELECT_SQL = """
			select r.id , r.student, r.phone, r.email,
			oc.id openClassId, oc.start_date, oc.teacher
			from registration r join open_class oc on r.open_class_id = oc.id
			where oc.id = ?
			""";

	private DataSource dataSource;
	
	private static final String SELECT_ONE = "select * from registration where id = ?";

//	private static final String SELECT_ALL = "select * from registration";

	private static final String INSERT = "insert into registration(open_class_id, student, phone, email) values (?, ?, ?, ?)";

	public RegisterModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Registration> findByOpenClassId(int openClassId) {
		var list = new ArrayList<Registration>();

		try (var conn = dataSource.getConnection(); var stmt = conn.prepareStatement(SELECT_SQL)) {

			stmt.setInt(1, openClassId);

			var rs = stmt.executeQuery();

			while (rs.next()) {
				var openClass = new OpenClass();
				openClass.setId(openClassId);

				var register = new Registration();
				register.setId(rs.getInt("id"));
				register.setOpenClass(openClass);
				register.setStudent(rs.getString("student"));
				register.setPhone(rs.getString("phone"));
				register.setEmail(rs.getString("email"));

				list.add(register);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void register(Registration registration) {
		try (var conn = dataSource.getConnection(); var stmt = conn.prepareStatement(INSERT)) {

			stmt.setInt(1, registration.getOpenClass().getId());
			stmt.setString(2, registration.getStudent());
			stmt.setString(3, registration.getPhone());
			stmt.setString(4, registration.getEmail());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

//	@Override
//	public List<Registration> getAll() {
//		var list = new ArrayList<Registration>();
//
//		try (var conn = dataSource.getConnection(); var stmt = conn.prepareStatement(SELECT_ALL)) {
//
//			var result = stmt.executeQuery();
//
//			while (result.next()) {
//				var register = new Registration();
//
//				register.setId(result.getInt("id"));
//				register.setStudent(result.getString("student"));
//				register.setPhone(result.getString("phone"));
//				register.setEmail(result.getString("email"));
//
//				list.add(register);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}

	@Override
	public Registration findById(int id) {
		try (var conn = dataSource.getConnection(); var stmt = conn.prepareStatement(SELECT_ONE)) {

			stmt.setInt(1, id);

			var result = stmt.executeQuery();

			while (result.next()) {
				var register = new Registration();

				register.setId(result.getInt("id"));
				
				System.out.println("Getting openClassId::::: " + result.getInt("id"));

				return register;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
