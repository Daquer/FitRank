package br.com.fitrank.modelo;

//Entidade EXTRACAO_HTML do banco 
public class ExtracaoHTML {

	private int id_jsoup;
	
	private String id_course;
	
	private Float distance;
	
	private Float duration;
	
	private Float avg_pace;

	private Float elevation_gain;

	private Float calories;

	private Float heart_rate;

	private Float max_heart_rate;

	private String weather;

	private Float celsius_degrees;

	private String place_kind;

	private String evaluation;

	private Float avg_speed;

	private Float red_line_heart_rate;

	private Float red_line_heart_rate_duration;

	private Float anaerobic_heart_rate;

	private Float anaerobic_heart_rate_duration;

	private Float aerobic_heart_rate;

	private Float aerobic_heart_rate_duration;

	private Float fat_burning_heart_rate;

	private Float fat_burning_heart_rate_duration;

	private Float easy_heart_rate;

	private Float easy_heart_rate_duration;

	private Float no_zone_heart_rate;

	private Float no_zone_heart_rate_duration;
	
	private byte[] json_course;
	
	private String json_url;
	
	public int getId_jsoup() {
		return id_jsoup;
	}

	public void setId_jsoup(int id_jsoup) {
		this.id_jsoup = id_jsoup;
	}

	public String getId_course() {
		return id_course;
	}

	public void setId_course(String id_course) {
		this.id_course = id_course;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public Float getAvg_pace() {
		return avg_pace;
	}

	public void setAvg_pace(Float avg_pace) {
		this.avg_pace = avg_pace;
	}

	public Float getElevation_gain() {
		return elevation_gain;
	}

	public void setElevation_gain(Float elevation_gain) {
		this.elevation_gain = elevation_gain;
	}

	public Float getCalories() {
		return calories;
	}

	public void setCalories(Float calories) {
		this.calories = calories;
	}

	public Float getHeart_rate() {
		return heart_rate;
	}

	public void setHeart_rate(Float heart_rate) {
		this.heart_rate = heart_rate;
	}

	public Float getMax_heart_rate() {
		return max_heart_rate;
	}

	public void setMax_heart_rate(Float max_heart_rate) {
		this.max_heart_rate = max_heart_rate;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public Float getCelsius_degrees() {
		return celsius_degrees;
	}

	public void setCelsius_degrees(Float celsius_degrees) {
		this.celsius_degrees = celsius_degrees;
	}

	public String getPlace_kind() {
		return place_kind;
	}

	public void setPlace_kind(String place_kind) {
		this.place_kind = place_kind;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public Float getAvg_speed() {
		return avg_speed;
	}

	public void setAvg_speed(Float avg_speed) {
		this.avg_speed = avg_speed;
	}

	public Float getRed_line_heart_rate() {
		return red_line_heart_rate;
	}

	public void setRed_line_heart_rate(Float red_line_heart_rate) {
		this.red_line_heart_rate = red_line_heart_rate;
	}

	public Float getRed_line_heart_rate_duration() {
		return red_line_heart_rate_duration;
	}

	public void setRed_line_heart_rate_duration(Float red_line_heart_rate_duration) {
		this.red_line_heart_rate_duration = red_line_heart_rate_duration;
	}

	public Float getAnaerobic_heart_rate() {
		return anaerobic_heart_rate;
	}

	public void setAnaerobic_heart_rate(Float anaerobic_heart_rate) {
		this.anaerobic_heart_rate = anaerobic_heart_rate;
	}

	public Float getAnaerobic_heart_rate_duration() {
		return anaerobic_heart_rate_duration;
	}

	public void setAnaerobic_heart_rate_duration(Float anaerobic_heart_rate_duration) {
		this.anaerobic_heart_rate_duration = anaerobic_heart_rate_duration;
	}

	public Float getAerobic_heart_rate() {
		return aerobic_heart_rate;
	}

	public void setAerobic_heart_rate(Float aerobic_heart_rate) {
		this.aerobic_heart_rate = aerobic_heart_rate;
	}

	public Float getAerobic_heart_rate_duration() {
		return aerobic_heart_rate_duration;
	}

	public void setAerobic_heart_rate_duration(Float aerobic_heart_rate_duration) {
		this.aerobic_heart_rate_duration = aerobic_heart_rate_duration;
	}

	public Float getFat_burning_heart_rate() {
		return fat_burning_heart_rate;
	}

	public void setFat_burning_heart_rate(Float fat_burning_heart_rate) {
		this.fat_burning_heart_rate = fat_burning_heart_rate;
	}

	public Float getFat_burning_heart_rate_duration() {
		return fat_burning_heart_rate_duration;
	}

	public void setFat_burning_heart_rate_duration(
			Float fat_burning_heart_rate_duration) {
		this.fat_burning_heart_rate_duration = fat_burning_heart_rate_duration;
	}

	public Float getEasy_heart_rate() {
		return easy_heart_rate;
	}

	public void setEasy_heart_rate(Float easy_heart_rate) {
		this.easy_heart_rate = easy_heart_rate;
	}

	public Float getEasy_heart_rate_duration() {
		return easy_heart_rate_duration;
	}

	public void setEasy_heart_rate_duration(Float easy_heart_rate_duration) {
		this.easy_heart_rate_duration = easy_heart_rate_duration;
	}

	public Float getNo_zone_heart_rate() {
		return no_zone_heart_rate;
	}

	public void setNo_zone_heart_rate(Float no_zone_heart_rate) {
		this.no_zone_heart_rate = no_zone_heart_rate;
	}

	public Float getNo_zone_heart_rate_duration() {
		return no_zone_heart_rate_duration;
	}

	public void setNo_zone_heart_rate_duration(Float no_zone_heart_rate_duration) {
		this.no_zone_heart_rate_duration = no_zone_heart_rate_duration;
	}

	public byte[] getJson_course() {
		return json_course;
	}

	public void setJson_course(byte[] json_course) {
		this.json_course = json_course;
	}

	public String getJson_url() {
		return json_url;
	}

	public void setJson_url(String json_url) {
		this.json_url = json_url;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}
		
}
