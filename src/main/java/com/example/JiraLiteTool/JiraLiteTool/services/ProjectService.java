package com.example.JiraLiteTool.JiraLiteTool.services;

import com.example.JiraLiteTool.JiraLiteTool.domains.Project;
import com.example.JiraLiteTool.JiraLiteTool.repositories.ProjectRepository;
import com.example.JiraLiteTool.exceptions.ProjectIdException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveOrUpdateProject(Project project) {

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException(
					"Project ID " + project.getProjectIdentifier().toUpperCase() + "already exists");
		}
	}
	
	public Project findProjectByIdentifier(String projectId) {

		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException("Project doesn't exists");
		}
		return project;
	}
	
	public Iterable<Project> findAllProject() {
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

		if (project == null) {
			throw new ProjectIdException(
					"Cannot delete project with ID " + projectId + " This project does not exists");
		}

		projectRepository.delete(project);
	}

}
