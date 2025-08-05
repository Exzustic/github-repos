package com.atipera.github_repos.service;

import com.atipera.github_repos.model.Branch;
import com.atipera.github_repos.model.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application-test.yml")
@ActiveProfiles("test")
class GitHubServiceIntegrationTest {

  @Autowired
  private GitHubService gitHubService;

  @Test
  void getNonForkRepositories_realUser_returnsRepositoriesWithBranches() {
    String username = "Exzustic";

    List<Repository> repos = gitHubService.getNonForkRepositories(username);

    assertThat(repos).isNotEmpty();

    for (Repository repo : repos) {
      assertThat(repo.getName()).isNotBlank();
      assertThat(repo.getOwnerLogin()).isEqualToIgnoringCase(username);
      assertThat(repo.getBranches()).isNotNull();
      for (Branch branch : repo.getBranches()) {
        assertThat(branch.getName()).isNotBlank();
        assertThat(branch.getLastCommitSha()).matches("[a-f0-9]{40}");
      }
    }
  }
}