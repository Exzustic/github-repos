
#  GitHub Repositories Viewer

This is a Spring Boot REST API application that connects to the [GitHub REST API v3](https://docs.github.com/en/rest) and returns a list of **non-fork repositories** for a given user, including all their **branches** and the **SHA of the last commit** for each branch.

---

##  Technologies Used

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Boot Test (JUnit 5)
- Gradle
- GitHub REST API v3

---

##  Features

-  Fetch non-forked repositories of a GitHub user
-  For each repository, list all branches
-  For each branch, return the SHA of the last commit
-  Handle `404 Not Found` error when the user doesn't exist

---

##  API Endpoint

```http
GET /users/{username}/repos
```

###  Example Request

```http
GET /users/octocat/repos
```

###  Example Response

```json
[
  {
    "name": "Hello-World",
    "ownerLogin": "Exzustic",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "6dcb09b..."
      }
    ]
  }
]
```

---

##  Error Handling

If the user is not found:

```json
{
  "status": 404,
  "message": "User Exzustic not found"
}
```

---

##  Environment Configuration

Create a `.env` file or configure `application.yml`:

### Option 1: `.env`

```env
GITHUB_TOKEN=ghp_your_token_here
```

Then expose the variables in your IDE or shell environment.

### Option 2: `application.yml`

```yaml
# src/main/resources/application.yml
github.token=${GITHUB_TOKEN}
```
---

##  Testing

- Unit tests for the `GitHubService` business logic
- One integration test for the full happy path (real call to GitHub API or mocked API)

### Run Tests:

```bash
./gradlew test
```

---

##  Build & Run

To build and run the application locally:

```bash
./gradlew bootRun
```

Or build a JAR:

```bash
./gradlew clean build
java -jar build/libs/github-repos-viewer-0.0.1-SNAPSHOT.jar
```

---


##  Requirements

- Java 21
- Gradle
- GitHub Personal Access Token  
  (Unauthenticated GitHub API calls are rate-limited to 60 per hour)

---

##  License

This project is provided for educational and testing purposes only.

---
