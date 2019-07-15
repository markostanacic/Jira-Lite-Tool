package com.example.JiraLiteTool.JiraLiteTool.repositories;

import com.example.JiraLiteTool.JiraLiteTool.domains.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {
	
	Backlog findByProjectIdentifier(String Identifier);
	
}