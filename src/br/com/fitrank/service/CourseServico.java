package br.com.fitrank.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.modelo.PostFitness;
import br.com.fitrank.persistencia.CourseDAO;
import br.com.fitrank.persistencia.PostFitnessDAO;

public class CourseServico {
	
	private CourseDAO courseDAO;
	
	public boolean adicionaListaIdsCourseServico(ArrayList<Course> listaCourses) {

		this.courseDAO = new CourseDAO();

		try {
			return courseDAO.adicionaListaIdsCourse(listaCourses);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
}
