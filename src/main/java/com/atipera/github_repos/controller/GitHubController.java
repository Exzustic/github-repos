package com.atipera.github_repos.controller;

import com.atipera.github_repos.exception.GitHubUserNotFoundException;
import com.atipera.github_repos.model.Repository;
import com.atipera.github_repos.service.GitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class GitHubController {

  private final GitHubService gitHubService;

  public GitHubController(GitHubService gitHubService) {
    this.gitHubService = gitHubService;
  }

  @GetMapping("/{username}/repos")
  public List<Repository> getRepos(@PathVariable String username) {
    return gitHubService.getNonForkRepositories(username);
  }

  @ExceptionHandler(GitHubUserNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(GitHubUserNotFoundException e) {
    Map<String, Object> body = new HashMap<>();
    body.put("status", 404);
    body.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
  }
}
