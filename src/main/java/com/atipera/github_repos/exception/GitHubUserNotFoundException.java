package com.atipera.github_repos.exception;

public class GitHubUserNotFoundException extends RuntimeException {
  public GitHubUserNotFoundException(String username) {
    super("User " + username + " not found");
  }
}
