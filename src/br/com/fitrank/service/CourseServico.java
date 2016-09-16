package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.persistencia.CourseDAO;

public class CourseServico {
	
	private CourseDAO courseDAO;
	
	public boolean adicionaListaIdsCourseServico(ArrayList<Course> listaCourses) {

		this.courseDAO = new CourseDAO();

		try {
			return courseDAO.adicionaListaIdsCourseServico(listaCourses);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
