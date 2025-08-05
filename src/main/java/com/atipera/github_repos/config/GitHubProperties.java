package com.atipera.github_repos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "github")
public class GitHubProperties {

  private String apiUrl;
  private String token;

  public String getApiUrl() {
    return apiUrl;
  }

  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
