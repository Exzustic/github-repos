package com.atipera.github_repos.service;

import com.atipera.github_repos.config.GitHubProperties;
import com.atipera.github_repos.exception.GitHubUserNotFoundException;
import com.atipera.github_repos.model.Branch;
import com.atipera.github_repos.model.Repository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GitHubService {

  private final RestTemplate restTemplate;
  private final GitHubProperties properties;

  public GitHubService(RestTemplateBuilder builder, GitHubProperties properties) {
    this.restTemplate = builder.build();
    this.properties = properties;
  }

  public List<Repository> getNonForkRepositories(String username) {
    String url = properties.getApiUrl() + "/users/" + username + "/repos";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "token " + properties.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
              url,
              HttpMethod.GET,
              entity,
              new ParameterizedTypeReference<>() {
              }
      );

      return response.getBody().stream()
              .filter(repo -> !(Boolean) repo.get("fork"))
              .map(repo -> {
                String repoName = (String) repo.get("name");
                Map<String, Object> owner = (Map<String, Object>) repo.get("owner");
                String ownerLogin = (String) owner.get("login");

                List<Branch> branches = getBranches(username, repoName);
                return new Repository(repoName, ownerLogin, branches);
              })
              .toList();

    } catch (HttpClientErrorException.NotFound e) {
      throw new GitHubUserNotFoundException(username);
    }
  }

  private List<Branch> getBranches(String username, String repoName) {
    String branchesUrl = properties.getApiUrl() + "/repos/" + username + "/" + repoName + "/branches";

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "token " + properties.getToken());
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
            branchesUrl,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<>() {
            }
    );

    return response.getBody().stream()
            .map(branch -> {
              String name = (String) branch.get("name");
              Map<String, Object> commit = (Map<String, Object>) branch.get("commit");
              String sha = (String) commit.get("sha");
              return new Branch(name, sha);
            })
            .toList();
  }
}
