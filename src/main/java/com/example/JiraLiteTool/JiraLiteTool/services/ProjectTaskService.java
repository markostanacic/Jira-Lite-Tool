package com.example.JiraLiteTool.JiraLiteTool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JiraLiteTool.JiraLiteTool.domains.Backlog;
import com.example.JiraLiteTool.JiraLiteTool.domains.ProjectTask;
import com.example.JiraLiteTool.JiraLiteTool.repositories.BacklogRepository;
import com.example.JiraLiteTool.JiraLiteTool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		// PTs to be added to a specific project, project != null, BL exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		// set the BL to pt
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like this: IDPRO-1 IDPRO-2
		Integer backlogSequence = backlog.getPTSequence();
		// Update the BL SEQUENCE
		backlogSequence++;

		backlog.setPTSequence(backlogSequence);

		projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + backlogSequence);
		projectTask.setProjectIdentifer(projectIdentifier);

		// INITIAL priority when priority is null
		if (projectTask.getPriority() == null) {
			projectTask.setPriority(3);
		}

		if (projectTask.getStatus().isEmpty() || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		return null;

	}

	public Iterable<ProjectTask> findBacklogById(String backlogId) {
		return projectTaskRepository.findByProjectIdentfierOrderByPriority(backlogId);
	}

}
