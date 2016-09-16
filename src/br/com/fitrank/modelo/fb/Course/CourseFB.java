package br.com.fitrank.modelo.fb.Course;

import com.restfb.Facebook;

public class CourseFB{
	@Facebook("created_time")
	private String created_time;
	
	@Facebook("title")
	private String title;
	
	@Facebook("type")
	private String type;
	
	@Facebook("id")
	private String id;
	
	@Facebook("count")
	private String count;
	
	@Facebook("first_action_id")
	private String first_action_id;
	
	@Facebook("first_time")
	private String first_time;
	
	@Facebook("data")
	private CourseData data;
	
	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getFirst_action_id() {
		return first_action_id;
	}

	public void setFirst_action_id(String first_action_id) {
		this.first_action_id = first_action_id;
	}

	public String getFirst_time() {
		return first_time;
	}

	public void setFirst_time(String first_time) {
		this.first_time = first_time;
	}

	public CourseData getData() {
		return data;
	}

	public void setData(CourseData data) {
		this.data = data;
	}
}

