package com.example.JiraLiteTool.exceptions;

public class ProjectNotFoundExcepctionResponse {

	private String projectNotFound;

	public ProjectNotFoundExcepctionResponse(String projectNotFound) {
		super();
		this.projectNotFound = projectNotFound;
	}

	public String getProjectNotFound() {
		return projectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}

}
