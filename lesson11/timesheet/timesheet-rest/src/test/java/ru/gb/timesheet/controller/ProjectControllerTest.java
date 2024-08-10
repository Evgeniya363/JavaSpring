package ru.gb.timesheet.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.repository.ProjectRepository;

import static org.junit.jupiter.api.Assertions.*;

// Вариант без WebTestClient
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerTest {

    @Autowired
    ProjectRepository projectRepository;

    @LocalServerPort
    private int port;
    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
    }
    
    @Test
    void testCreate() {
        Project project = new Project();
        project.setName("New project");
        Project toCreate = projectRepository.save(project);
        
        // POST /projects
        ResponseEntity<Project> response = restClient.post()
                .uri("/projects")
                .body(toCreate)
                .retrieve()
                .toEntity(Project.class);

        // Проверяем HTTP-ручку сервера
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Project responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(responseBody.getId(), toCreate.getId());
        assertEquals(responseBody.getName(), toCreate.getName());

        // Проверяем, что запись в БД есть
        assertTrue(projectRepository.existsById(responseBody.getId()));
    }

    @Test
    void testDeleteById() {
        Project expected = new Project();
        expected.setName("New Project");
        Project toDelete = projectRepository.save(expected);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/projects/" + toDelete.getId())
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // Проверяем, что в базе нет
        assertFalse(projectRepository.existsById(toDelete.getId()));

    }

    @Test
    void getByIdNotFound() {
        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restClient.get()
                    .uri("/projects/-2")
                    .retrieve()
                    .toBodilessEntity();
        });

    }

    @Test
    void getByIdAllOk() {
        // save (project)
        // GET /project/1L => 200 OK

        //given
        Project expected = new Project();
        expected.setName("New Project");
        expected = projectRepository.save(expected);

        ResponseEntity<Project> actual = restClient.get()
                .uri("/projects/" + expected.getId())
                .retrieve()
                .toEntity(Project.class);

        // assert 200 OK
        Assertions.assertEquals(HttpStatus.OK, actual.getStatusCode());
        Project responseBody = actual.getBody();
        assertNotNull(responseBody);
        assertEquals(expected.getId(), responseBody.getId());
        assertEquals(expected.getName(), responseBody.getName());
    }
}


/*



@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProjectControllerTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @Test
    void findByIdPresent() {
        // given
        Project project = new Project();
        project.setName("New Project");
        Project expected = projectRepository.save(project);

        // when, then
        webTestClient.get()
                .uri("/projects/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Project.class)
                .value(actual -> {
                    assertEquals(expected.getId(), actual.getId());
                    assertEquals(expected.getName(), actual.getName());
                });

    }
}
*/
