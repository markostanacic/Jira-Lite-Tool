package com.example.JiraLiteTool.JiraLiteTool.webcontrollers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JiraLiteTool.JiraLiteTool.domains.ProjectTask;
import com.example.JiraLiteTool.JiraLiteTool.services.ProjectTaskService;
import com.example.JiraLiteTool.JiraLiteTool.services.ValidationErrorService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;

	@Autowired
	private ValidationErrorService validationErrorService;

	@PostMapping("/{backlogId}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlogId) {

		ResponseEntity<?> errorMap = validationErrorService.validationError(result);

		if (errorMap != null) {
			return errorMap;
		}

		ProjectTask projectTask1 = projectTaskService.addProjectTask(backlogId, projectTask);

		return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
	}

	@GetMapping("/{backlogId}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlogId) {
		return projectTaskService.findBacklogById(backlogId);
	}

	@GetMapping("/{backlogId}/{ptId}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String ptId) {
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlogId, ptId);

		return new ResponseEntity<>(projectTask, HttpStatus.OK);
	}

	@PatchMapping("/{backlogId}/{ptId}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
			@PathVariable String backlogId, @PathVariable String ptId) {
		ResponseEntity<?> errorMap = validationErrorService.validationError(result);

		if (errorMap != null) {
			return errorMap;
		}

		ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlogId, ptId);

		return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	}

	@DeleteMapping("/{backlogId}/{ptId}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId, @PathVariable String ptId) {
		projectTaskService.deletePTByProjectSequence(backlogId, ptId);

		return new ResponseEntity<>("Project Task " + ptId + " was deleted successfully", HttpStatus.OK);
	}

}
