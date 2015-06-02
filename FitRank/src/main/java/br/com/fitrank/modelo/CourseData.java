package br.com.fitrank.modelo;

import com.restfb.Facebook;

public class CourseData {
	
	@Facebook("course")
	private Course course;
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public static class Course {
		
		@Facebook("id")
		private String id;
		
		@Facebook("url")
		private String url;
		
		@Facebook("type")
		private String type;
		
		@Facebook("title")
		private String title;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
}
