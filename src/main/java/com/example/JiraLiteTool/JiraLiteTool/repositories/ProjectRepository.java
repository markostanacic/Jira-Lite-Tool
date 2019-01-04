package com.example.JiraLiteTool.JiraLiteTool.repositories;

import com.example.JiraLiteTool.JiraLiteTool.domains.Project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	
	Project findByProjectIdentifier(String projectId);
	
	@Override
	Iterable<Project> findAll();
	
	void delete(Project project);
}