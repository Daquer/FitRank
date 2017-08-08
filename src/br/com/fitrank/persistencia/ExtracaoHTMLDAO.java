package br.com.fitrank.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fitrank.modelo.ExtracaoHTML;
import br.com.fitrank.util.JDBCFactory;
import br.com.fitrank.util.Logger;

public class ExtracaoHTMLDAO {

	private Connection conexao;

	public ExtracaoHTMLDAO() {
		this.conexao = new JDBCFactory().getConnection();
	}

	public ExtracaoHTML adicionaJSoup(ExtracaoHTML extracaoHTML) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO Extracao_HTML ("
			    + "distance, "
			    + "duration, "
			    + "avg_pace, "
			    + "elevation_gain, "
			    + "calories, "
			    + "heart_rate, "
			    + "max_heart_rate, "
			    + "weather, "
			    + "celsius_degrees, "
			    + "place_kind, "
			    + "evaluation, "
			    + "avg_speed, "
			    + "red_line_heart_rate, "
			    + "red_line_heart_rate_duration, "
			    + "anaerobic_heart_rate, "
			    + "anaerobic_heart_rate_duration, "
			    + "aerobic_heart_rate, "
			    + "aerobic_heart_rate_duration, "
			    + "fat_burning_heart_rate, "
			    + "fat_burning_heart_rate_duration, "
			    + "easy_heart_rate, "
			    + "easy_heart_rate_duration, "
			    + "no_zone_heart_rate, "
			    + "no_zone_heart_rate_duration, "
			    + "json_course, "
			    + "url, "
			    + "id_course"
				+ ") VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			int i = 0;
			
			preparedStatement.setFloat(++i, extracaoHTML.getDistance());
			preparedStatement.setFloat(++i, extracaoHTML.getDuration());
			preparedStatement.setFloat(++i, extracaoHTML.getAvg_pace());
			preparedStatement.setFloat(++i, extracaoHTML.getElevation_gain());
			preparedStatement.setFloat(++i, extracaoHTML.getCalories());
			preparedStatement.setFloat(++i, extracaoHTML.getHeart_rate());
			preparedStatement.setFloat(++i, extracaoHTML.getMax_heart_rate());
			preparedStatement.setString(++i, extracaoHTML.getWeather());
			preparedStatement.setFloat(++i, extracaoHTML.getCelsius_degrees());
			preparedStatement.setString(++i, extracaoHTML.getPlace_kind());
			preparedStatement.setString(++i, extracaoHTML.getEvaluation());
			preparedStatement.setFloat(++i, extracaoHTML.getAvg_speed());
			preparedStatement.setFloat(++i, extracaoHTML.getRed_line_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getRed_line_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getAnaerobic_heart_rate());
			preparedStatement.setFloat(++i, extracaoHTML.getAnaerobic_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getAerobic_heart_rate());
			preparedStatement.setFloat(++i, extracaoHTML.getAerobic_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getFat_burning_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getFat_burning_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getEasy_heart_rate());
			preparedStatement.setFloat(++i, extracaoHTML.getEasy_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getNo_zone_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getNo_zone_heart_rate_duration());
			preparedStatement.setBytes(++i, extracaoHTML.getJson_course());
			preparedStatement.setString(++i, extracaoHTML.getJson_url());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return extracaoHTML;
	}

	public ExtracaoHTML atualizaJSoup(ExtracaoHTML extracaoHTML) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "UPDATE Extracao_HTML SET "
				+ "distance = ?, "
				+ "duration = ?, "
				+ "avg_pace = ?, "
				+ "elevation_gain = ?, "
				+ "calories = ?, "
				+ "heart_rate = ?, "
				+ "max_heart_rate = ?, "
				+ "weather = ?, "
				+ "celsius_degrees = ?, "
				+ "place_kind = ?, "
				+ "evaluation = ?, "
				+ "avg_speed = ?, "
				+ "red_line_heart_rate = ?, "
				+ "red_line_heart_rate_duration = ?, "
				+ "anaerobic_heart_rate = ?, "
				+ "anaerobic_heart_rate_duration = ?, "
				+ "aerobic_heart_rate = ?, "
				+ "aerobic_heart_rate_duration = ?, "
				+ "fat_burning_heart_rate = ?, "
				+ "fat_burning_heart_rate_duration = ?, "
				+ "easy_heart_rate = ?, "
				+ "easy_heart_rate_duration = ?, "
				+ "no_zone_heart_rate = ?, "
				+ "no_zone_heart_rate_duration = ?, "
				+ "json_course = ?, "
				+ "url = ?" 
				+ "id_course = ?, "
					+ "WHERE id_jsoup = ?";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			int i = 0;

			preparedStatement.setFloat(++i, extracaoHTML.getDistance());
			preparedStatement.setFloat(++i, extracaoHTML.getDuration());
			preparedStatement.setFloat(++i, extracaoHTML.getAvg_pace());
			preparedStatement.setFloat(++i, extracaoHTML.getElevation_gain());
			preparedStatement.setFloat(++i, extracaoHTML.getCalories());
			preparedStatement.setFloat(++i, extracaoHTML.getHeart_rate());
			preparedStatement.setFloat(++i, extracaoHTML.getMax_heart_rate());
			preparedStatement.setString(++i, extracaoHTML.getWeather());
			preparedStatement.setFloat(++i, extracaoHTML.getCelsius_degrees());
			preparedStatement.setString(++i, extracaoHTML.getPlace_kind());
			preparedStatement.setString(++i, extracaoHTML.getEvaluation());
			preparedStatement.setFloat(++i, extracaoHTML.getAvg_speed());
			preparedStatement.setFloat(++i, extracaoHTML.getRed_line_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getRed_line_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getAnaerobic_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getAnaerobic_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getAerobic_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getAerobic_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getFat_burning_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getFat_burning_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getEasy_heart_rate());
			preparedStatement.setFloat(++i, extracaoHTML.getEasy_heart_rate_duration());
			preparedStatement.setFloat(++i, extracaoHTML.getNo_zone_heart_rate());
			preparedStatement.setFloat(++i,	extracaoHTML.getNo_zone_heart_rate_duration());
			preparedStatement.setBytes(++i, extracaoHTML.getJson_course());
			preparedStatement.setString(++i, extracaoHTML.getJson_url());
			preparedStatement.setString(++i, extracaoHTML.getId_course());
			preparedStatement.setInt(++i, extracaoHTML.getId_jsoup());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return extracaoHTML;
	}
	
	
	public ExtracaoHTML leJSoup(ExtracaoHTML extracaoHTML) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String selectTableSQL = "SELECT "
			    + "id_jsoup, "
			    + "distance, "
			    + "duration, "
			    + "avg_pace, "
			    + "elevation_gain, "
			    + "calories, "
			    + "heart_rate, "
			    + "max_heart_rate, "
			    + "weather, "
			    + "celsius_degrees, "
			    + "place_kind, "
			    + "evaluation, "
			    + "avg_speed, "
			    + "red_line_heart_rate, "
			    + "red_line_heart_rate_duration, "
			    + "anaerobic_heart_rate, "
			    + "anaerobic_heart_rate_duration, "
			    + "aerobic_heart_rate, "
			    + "aerobic_heart_rate_duration, "
			    + "fat_burning_heart_rate, "
			    + "fat_burning_heart_rate_duration, "
			    + "easy_heart_rate, "
			    + "easy_heart_rate_duration, "
			    + "no_zone_heart_rate, "
			    + "no_zone_heart_rate_duration, "
			    + "json_course, "
			    + "url, "
			    + "id_course"
				+ "from Extracao_HTML"
				+ "where id_jsoup = ?";

		try {
			dbConnection = conexao;
			preparedStatement = dbConnection.prepareStatement(selectTableSQL);
			
			
			ResultSet rs = preparedStatement.executeQuery(selectTableSQL);
			
			
			preparedStatement.setInt(1, extracaoHTML.getId_jsoup());
			
			
			while (rs.next()) {
				
				extracaoHTML.setId_jsoup(rs.getInt("id_jsoup"));
				extracaoHTML.setDistance(rs.getFloat("distance"));
				extracaoHTML.setDuration(rs.getFloat("duration"));
				extracaoHTML.setAvg_pace(rs.getFloat("avg_pace"));
				extracaoHTML.setElevation_gain(rs.getFloat("elevation_gain"));
				extracaoHTML.setCalories(rs.getFloat("calories"));
				extracaoHTML.setHeart_rate(rs.getFloat("heart_rate"));
				extracaoHTML.setMax_heart_rate(rs.getFloat("max_heart_rate"));
				extracaoHTML.setWeather(rs.getString("weather"));
				extracaoHTML.setCelsius_degrees(rs.getFloat("celsius_degrees"));
				extracaoHTML.setPlace_kind(rs.getString("place_kind"));
				extracaoHTML.setEvaluation(rs.getString("evaluation"));
				extracaoHTML.setAvg_speed(rs.getFloat("avg_speed"));
				extracaoHTML.setRed_line_heart_rate(rs.getFloat("red_line_heart_rate"));
				extracaoHTML.setRed_line_heart_rate_duration(rs.getFloat("red_line_heart_rate_duration"));
				extracaoHTML.setAnaerobic_heart_rate(rs.getFloat("anaerobic_heart_rate"));
				extracaoHTML.setAnaerobic_heart_rate_duration(rs.getFloat("anaerobic_heart_rate_duration"));
				extracaoHTML.setAerobic_heart_rate(rs.getFloat("aerobic_heart_rate"));
				extracaoHTML.setAerobic_heart_rate_duration(rs.getFloat("aerobic_heart_rate_duration"));
				extracaoHTML.setFat_burning_heart_rate(rs.getFloat("fat_burning_heart_rate"));
				extracaoHTML.setFat_burning_heart_rate_duration(rs.getFloat("fat_burning_heart_rate_duration"));
				extracaoHTML.setEasy_heart_rate(rs.getFloat("easy_heart_rate"));
				extracaoHTML.setEasy_heart_rate_duration(rs.getFloat("easy_heart_rate_duration"));
				extracaoHTML.setNo_zone_heart_rate(rs.getFloat("no_zone_heart_rate"));
				extracaoHTML.setNo_zone_heart_rate_duration(rs.getFloat("no_zone_heart_rate_duration"));
				extracaoHTML.setJson_course(rs.getBytes("json_course"));
				extracaoHTML.setJson_url(rs.getString("json_url"));

			}
			
			// execute insert SQL stetement
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			Logger.insertLog(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return extracaoHTML;
	}
}
