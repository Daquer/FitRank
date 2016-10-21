package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;


public class CourseDAO {
	
	private Connection conexao;

	public CourseDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}
	
	public Course adicionaCourse(Course course) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO course ("
				+ "id_course, "
				+ "distancia, "
				+ "calorias, "
				+ "id_post"
				+ ") VALUES (?, ?, ?, ?, ?)";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, course.getId_course());
			preparedStatement.setFloat(++i, course.getDistancia());
			preparedStatement.setFloat(++i, course.getCalorias());
			preparedStatement.setString(++i, course.getId_post());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaCourse | " + e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return course;
	}
	
	public Course atualizaCourse(Course course)
			throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "update course set "
				+ "distancia = ? "
				+ "calorias = ?, "
				+ "id_post = ? "
				+ "where id_course = ?";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);
			
			int i = 0;
			
			preparedStatement.setFloat(++i, course.getDistancia());
			preparedStatement.setFloat(++i, course.getCalorias());
			preparedStatement.setString(++i, course.getId_post());
			preparedStatement.setString(++i, course.getId_course());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("atualizaCourse |" +  e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return course;
	}
	
	public Course leCourse(Course course) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
	
		String selectTableSQL = "SELECT "
				+ "id_course,"
				+ "distancia, "
				+ "calorias, "
				+ "id_post, "
				+ "FROM course "
				+ "WHERE id_course = ?";
		
		try {
			
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			
			ResultSet rs = preparedStatement.executeQuery(selectTableSQL);
			
			
			preparedStatement.setString(1, course.getId_course());
			
			
			while (rs.next()) {
				
				course.setId_course(rs.getString("id_course"));
				course.setDistancia(rs.getFloat("distancia"));
				course.setCalorias(rs.getFloat("calorias"));
				course.setId_post(rs.getString("id_post"));

			}
		} catch (SQLException e) {
			 
			Logger.insertLog("leCourse | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
			
		}
		
		return course;
	}

	public boolean adicionaListaIdsCourse(ArrayList<Course> listaCourses) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		boolean isSucess = true;
		
		String insertTableSQL = "INSERT INTO course ("
				+ "id_course, "
				+ "id_pessoa, "
				+ "id_post " 
				+ ") VALUES (?, ?, ?)";

		
		for (int aux = 0; aux < (listaCourses.size() - 1); aux++) {
			insertTableSQL +=  ", (?, ?, ?)";			
		}	
		
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			for (Course course : listaCourses) {
			
				preparedStatement.setString(++i, course.getId_course());
				preparedStatement.setString(++i, course.getId_pessoa());
				preparedStatement.setString(++i, course.getId_post());
				
			}
			// execute insert SQL statement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog("adicionaListaIdsCourse | " + e.getMessage());
			isSucess = false;
			
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		
		return isSucess;
	}
	
	public boolean atualizaListaCourses(List<Course> listaCourses)
			throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		boolean isSucess = true;
		
		String updateTableSQL = "update course set "
				+ "distancia = ?, "
				+ "calorias = ? "
				+ "where id_course = ?;";
		
		for (int aux = 0; aux < (listaCourses.size() - 1); aux++) {	
			updateTableSQL += "update course set "
					+ "distancia = ?, "
					+ "calorias = ? "
					+ "where id_course = ?;";
		}	
		
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);
			
			int i = 0;

			
			for (Course course : listaCourses) {
				preparedStatement.setFloat(++i, course.getDistancia());
				preparedStatement.setFloat(++i, course.getCalorias());
				preparedStatement.setString(++i, course.getId_course());
			}
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {

			Logger.insertLog("atualizaListaCourses |" +  e.getMessage());
			isSucess = false;
			
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

		return isSucess;
	}
	
	public List<Course> leCoursePorIdPessoa(String idPessoa) throws SQLException {
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		List<Course> listaPostFitness = new ArrayList<Course>();
	
		String selectTableSQL = "select "
				+ "id_course, "
				+ "id_post, "
				+ "id_pessoa "
				+ "from course "
				+ "where id_pessoa = ?";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			preparedStatement.setString(1, idPessoa);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while ( rs.next() ) {
				Course course = new Course();
				course.setId_course(rs.getString("id_course"));
				course.setId_post(rs.getString("id_post"));
				course.setId_pessoa(rs.getString("id_pessoa"));
				
				listaPostFitness.add(course);
			}
	
		} catch (SQLException e) {
	
			Logger.insertLog("leCoursePorIdPessoa | " + e.getMessage());
	
		} finally {
	
			if (preparedStatement != null) {
				preparedStatement.close();
			}
	
			if (dbConnection != null) {
				dbConnection.close();
			}
	
		}
		return listaPostFitness;
	}
}
