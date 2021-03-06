package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.persistencia.CourseDAO;

public class CourseServico {
	private CourseDAO courseDAO;
		
	public boolean adicionaListaIdsCourseServico(ArrayList<Course> listaCourses) {

		this.courseDAO = new CourseDAO();
 
		try {
			return courseDAO.adicionaListaIdsCourse(listaCourses);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Course> leCoursePorIdPessoa(String idPessoa) {
		this.courseDAO = new CourseDAO();
		
		try {
	    	return courseDAO.leCoursePorIdPessoa(idPessoa);
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean atualizaListaCourses(ArrayList<Course> listaCourses) {

		this.courseDAO = new CourseDAO();
 
		try {
			return courseDAO.atualizaListaCourses(listaCourses);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
