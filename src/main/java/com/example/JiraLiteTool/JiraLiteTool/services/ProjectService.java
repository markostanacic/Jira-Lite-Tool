package com.example.JiraLiteTool.JiraLiteTool.services;

import com.example.JiraLiteTool.JiraLiteTool.domains.Project;
import com.example.JiraLiteTool.JiraLiteTool.repositories.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {

		// Logic

		return projectRepository.save(project);
	}

}