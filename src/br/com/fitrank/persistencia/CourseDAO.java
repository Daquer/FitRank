package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fitrank.modelo.Course;
import br.com.fitrank.util.JDBCFactory;


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
				+ "ritmo, "
				+ "id_post"
				+ ") VALUES (?, ?, ?, ?, ?)";
				
		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);
			
			int i = 0;
			
			preparedStatement.setString(++i, course.getId_course());
			preparedStatement.setFloat(++i, course.getDistancia());
			preparedStatement.setFloat(++i, course.getCalorias());
			preparedStatement.setFloat(++i, course.getRitmo()); 
			preparedStatement.setString(++i, course.getId_post());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

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
				+ "ritmo = ?, "
				+ "id_post = ? "
				+ "where id_course = ?";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);
			
			int i = 0;
			
			preparedStatement.setFloat(++i, course.getDistancia());
			preparedStatement.setFloat(++i, course.getCalorias());
			preparedStatement.setFloat(++i, course.getRitmo()); 
			preparedStatement.setString(++i, course.getId_post());
			preparedStatement.setString(++i, course.getId_course());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

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
				+ "ritmo, "
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
				course.setRitmo(rs.getFloat("ritmo"));
				course.setId_post(rs.getString("id_post"));

			}
		} catch (SQLException e) {
			 
			System.out.println(e.getMessage());
	
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
}
