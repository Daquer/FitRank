package br.com.fitrank.modelo;


//@Entity(name="fitness_course")
public class Fitness {
//	@Id
	private String fbObjectId;

	private String postType;
	
	private String profileNameFrom;
	
	private String app;
	
	private String startTime;
	 
	private String endTime;
	
	private String publishTime;
	
	private String message;
	
	private boolean noFeedStory;
	
	private String distance;
	private String duration;
	private String avgPace;
	private String elevationGain;
	private String calories;
	private String heart_rate;
	private String maxHeartRate;
	private String weather;
	private String celsiusDegrees;
	private String placeKind;
	private String evaluation;
	private String avgSpeed;

	private String redLineHeartRate;
	private String redLineHeartRateDuration;

	private String anaerobicHeartRate;
	private String anaerobicHeartRateDuration;

	private String aerobicHeartRate;
	private String aerobicHeartRateDuration;

	private String fatBurningHeartRate;
	private String fatBurningHeartRateDuration;

	private String easyHeartRate;
	private String easyHeartRateDuration;

	private String noZoneHeartRate;
	private String noZoneHeartRateDuration;

	public String getFbObjectId() {
		return fbObjectId;
	}
	
	public void setFbObjectId(String fbObjectId) {
		this.fbObjectId = fbObjectId;
	}
	
	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getProfileNameFrom() {
		return profileNameFrom;
	}

	public void setProfileNameFrom(String profileNameFrom) {
		this.profileNameFrom = profileNameFrom;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isNoFeedStory() {
		return noFeedStory;
	}

	public void setNoFeedStory(boolean noFeedStory) {
		this.noFeedStory = noFeedStory;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAvgPace() {
		return avgPace;
	}

	public void setAvgPace(String avgPace) {
		this.avgPace = avgPace;
	}

	public String getElevationGain() {
		return elevationGain;
	}

	public void setElevationGain(String elevationGain) {
		this.elevationGain = elevationGain;
	}

	public String getCalories() {
		return calories;
	}

	public void setCalories(String calories) {
		this.calories = calories;
	}

	public String getHeart_rate() {
		return heart_rate;
	}

	public void setHeart_rate(String heart_rate) {
		this.heart_rate = heart_rate;
	}

	public String getMaxHeartRate() {
		return maxHeartRate;
	}

	public void setMaxHeartRate(String maxHeartRate) {
		this.maxHeartRate = maxHeartRate;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getCelsiusDegrees() {
		return celsiusDegrees;
	}

	public void setCelsiusDegrees(String celsiusDegrees) {
		this.celsiusDegrees = celsiusDegrees;
	}

	public String getPlaceKind() {
		return placeKind;
	}

	public void setPlaceKind(String placeKind) {
		this.placeKind = placeKind;
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getAvgSpeed() {
		return avgSpeed;
	}

	public void setAvgSpeed(String avgSpeed) {
		this.avgSpeed = avgSpeed;
	}

	public String getRedLineHeartRate() {
		return redLineHeartRate;
	}

	public void setRedLineHeartRate(String redLineHeartRate) {
		this.redLineHeartRate = redLineHeartRate;
	}

	public String getRedLineHeartRateDuration() {
		return redLineHeartRateDuration;
	}

	public void setRedLineHeartRateDuration(String redLineHeartRateDuration) {
		this.redLineHeartRateDuration = redLineHeartRateDuration;
	}

	public String getAnaerobicHeartRate() {
		return anaerobicHeartRate;
	}

	public void setAnaerobicHeartRate(String anaerobicHeartRate) {
		this.anaerobicHeartRate = anaerobicHeartRate;
	}

	public String getAnaerobicHeartRateDuration() {
		return anaerobicHeartRateDuration;
	}

	public void setAnaerobicHeartRateDuration(String anaerobicHeartRateDuration) {
		this.anaerobicHeartRateDuration = anaerobicHeartRateDuration;
	}

	public String getAerobicHeartRate() {
		return aerobicHeartRate;
	}

	public void setAerobicHeartRate(String aerobicHeartRate) {
		this.aerobicHeartRate = aerobicHeartRate;
	}

	public String getAerobicHeartRateDuration() {
		return aerobicHeartRateDuration;
	}

	public void setAerobicHeartRateDuration(String aerobicHeartRateDuration) {
		this.aerobicHeartRateDuration = aerobicHeartRateDuration;
	}

	public String getFatBurningHeartRate() {
		return fatBurningHeartRate;
	}

	public void setFatBurningHeartRate(String fatBurningHeartRate) {
		this.fatBurningHeartRate = fatBurningHeartRate;
	}

	public String getFatBurningHeartRateDuration() {
		return fatBurningHeartRateDuration;
	}

	public void setFatBurningHeartRateDuration(String fatBurningHeartRateDuration) {
		this.fatBurningHeartRateDuration = fatBurningHeartRateDuration;
	}

	public String getEasyHeartRate() {
		return easyHeartRate;
	}

	public void setEasyHeartRate(String easyHeartRate) {
		this.easyHeartRate = easyHeartRate;
	}

	public String getEasyHeartRateDuration() {
		return easyHeartRateDuration;
	}

	public void setEasyHeartRateDuration(String easyHeartRateDuration) {
		this.easyHeartRateDuration = easyHeartRateDuration;
	}

	public String getNoZoneHeartRate() {
		return noZoneHeartRate;
	}

	public void setNoZoneHeartRate(String noZoneHeartRate) {
		this.noZoneHeartRate = noZoneHeartRate;
	}

	public String getNoZoneHeartRateDuration() {
		return noZoneHeartRateDuration;
	}

	public void setNoZoneHeartRateDuration(String noZoneHeartRateDuration) {
		this.noZoneHeartRateDuration = noZoneHeartRateDuration;
	} 		
}
