package com.example.JiraLiteTool.JiraLiteTool.repositories;

import com.example.JiraLiteTool.JiraLiteTool.domains.ProjectTask;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
	
	List<ProjectTask> findByProjectIdentifierOrderByPriority(String backlogId);
	
	ProjectTask findByProjectSequence(String ptId);
}
