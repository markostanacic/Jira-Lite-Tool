package com.example.JiraLiteTool.JiraLiteTool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.JiraLiteTool.JiraLiteTool.domains.Backlog;
import com.example.JiraLiteTool.JiraLiteTool.domains.Project;
import com.example.JiraLiteTool.JiraLiteTool.domains.ProjectTask;
import com.example.JiraLiteTool.JiraLiteTool.repositories.BacklogRepository;
import com.example.JiraLiteTool.JiraLiteTool.repositories.ProjectRepository;
import com.example.JiraLiteTool.JiraLiteTool.repositories.ProjectTaskRepository;
import com.example.JiraLiteTool.exceptions.ProjectNotFoundExcepction;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {
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
			projectTask.setProjectIdentifier(projectIdentifier);

			// INITIAL priority when priority is null --> TODO: status has to be enum
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			

			if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
				projectTask.setPriority(3);
			}

			return projectTaskRepository.save(projectTask);

		} catch (Exception e) {
			throw new ProjectNotFoundExcepction("Project not found!");
		}

	}

	public Iterable<ProjectTask> findBacklogById(String backlogId) {

		Project project = projectRepository.findByProjectIdentifier(backlogId);

		if (project == null) {
			throw new ProjectNotFoundExcepction("Project with ID: '" + backlogId + "' does not exists");
		}

		return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlogId);
	}

	public ProjectTask findPTByProjectSequence(String backlogId, String ptId) {

		Backlog backlog = backlogRepository.findByProjectIdentifier(backlogId);

		if (backlog == null) {
			throw new ProjectNotFoundExcepction("Project with ID: '" + backlogId + "' does not exists");
		}

		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(ptId);

		if (projectTask == null) {
			throw new ProjectNotFoundExcepction("ProjectTask with ID: '" + ptId + "' does not exists");
		}

		// workaround for backlog/project id in the path for corresponding right project
		if (!projectTask.getProjectIdentifier().equals(backlogId)) {
			throw new ProjectNotFoundExcepction(
					"ProjectTask with ID: '" + ptId + "' does not exists in project: '" + backlogId);
		}

		return projectTask;
	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlogId, String ptId) {
		ProjectTask projectTask = findPTByProjectSequence(backlogId, ptId);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);
	}

	public void deletePTByProjectSequence(String backlogId, String ptId) {
		ProjectTask projectTask = findPTByProjectSequence(backlogId, ptId);

		projectTaskRepository.delete(projectTask);
	}

}
