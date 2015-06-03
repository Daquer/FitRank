package br.com.fitrank.modelo;

import static com.restfb.util.DateUtils.toDateFromLongFormat;

import java.io.Serializable;
import java.util.Date;

import com.restfb.Facebook;
import com.restfb.types.Post;

//https://developers.facebook.com/docs/reference/opengraph/action-type/fitness.runs
public class PostFitnessFB extends Post implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//verificar a necessidade de campo place
	
	
	@Facebook("start_time")
	private String startTime;
	 
	@Facebook("end_time")
	private String endTime;
	
	@Facebook("publish_time")
	private String publishTime;
	
	@Facebook
	private String message;
	
	@Facebook("no_feed_story")
	private boolean noFeedStory;
	
	@Facebook("data")
	private CourseData dataCourse;

	public Date getStartTime() {
		return toDateFromLongFormat(startTime);
	}
	
	public Date getEndTime() {
		return toDateFromLongFormat(endTime);
	}
	
	public Date getPublishTime() {
		return toDateFromLongFormat(publishTime);
	}
	
	public String getMessage() {
		return message;
	}

	public boolean isNoFeedStory() {
		return noFeedStory;
	}
	
	public CourseData getDataCourse() {
		return dataCourse;
	}
	
	public void setDataCourse(CourseData dataCourse) {
		this.dataCourse = dataCourse;
	}
}