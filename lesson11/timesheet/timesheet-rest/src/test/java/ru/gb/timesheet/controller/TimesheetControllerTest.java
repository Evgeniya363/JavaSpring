package ru.gb.timesheet.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
/*
Протестировать TimesheetController
GET /timesheets/{id}
GET /timesheets
POST /timesheets
DELETE /timesheets
PUT /timesheets/{id}
 */

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class TimesheetControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @Autowired
    TimesheetRepository timesheetRepository;

    @BeforeEach
    void testDeleteAll() {
        timesheetRepository.deleteAll();
    }

    @Test
    void testGetByIdAllOk() {
        // given
        Timesheet expectedTimesheet = createTimesheet();
        // when, then
        webTestClient.get()
                .uri("/timesheets/" + expectedTimesheet.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Timesheet.class)
                .value(actual -> {
                    assertNotNull(actual);
                    assertEquals(expectedTimesheet.getId(), actual.getId());
                    assertEquals(expectedTimesheet.getMinutes(), actual.getMinutes());
                    assertEquals(expectedTimesheet.getCreatedAt(), actual.getCreatedAt());
                    assertEquals(expectedTimesheet.getEmployeeId(), actual.getEmployeeId());
                    assertEquals(expectedTimesheet.getProjectId(), actual.getProjectId());
                });

    }

    @Test
    void testGetByIdNotFound() {

        webTestClient.get()
                .uri("/projects/-2")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(Void.class);
    }

    @Test
    void testGetAll() {
        //given
        java.util.List<Timesheet> timesheets = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            timesheets.add(createTimesheet());
        }
        // when
        webTestClient.get()
                .uri("/timesheets")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Timesheet.class)
                // then
                .hasSize(timesheets.size())
                .consumeWith(result -> {
                    java.util.List<Timesheet> responseBody = result.getResponseBody();
                    assertNotNull(responseBody);

                    for (int i = 0; i < responseBody.size(); i++) {
                        Timesheet actual = responseBody.get(i);
                        Timesheet expected = timesheets.get(i);
                        assertNotNull(actual);
                        assertEquals(actual, expected);  // Аннотация @Data включает @@EqualsAndHashCode
                    }

                });

    }

    @Test
    void testCreate() {

        Timesheet timesheet = createTimesheet();
        webTestClient.post()
                .uri("/timesheets")
                .bodyValue(timesheet)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Timesheet.class)
                .isEqualTo(timesheet);  // Аннотация @Data включает @@EqualsAndHashCode

        assertTrue(timesheetRepository.existsById(timesheet.getId()));

    }

    @Test
    void testDeleteById() {
        Timesheet timesheet = createTimesheet();

        webTestClient.delete()
                .uri("/timesheets/" + timesheet.getId())
                .exchange()
                .expectStatus().isNoContent();

        assertFalse(timesheetRepository.existsById(timesheet.getId()));

    }

    @Test
    void testUpdate() {

        Timesheet timesheet = createTimesheet();
        timesheet.setMinutes(1000);
        timesheet.setCreatedAt(LocalDate.now().minusDays(1000));
        timesheet.setProjectId(1000L);
        timesheet.setEmployeeId(1000L);
        Timesheet expected = timesheetRepository.save(timesheet);

        webTestClient.put()
                .uri("/timesheets/" + timesheet.getId())
                .bodyValue(expected)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Timesheet.class)
                .isEqualTo(expected);

        Timesheet actual = timesheetRepository.findById(expected.getId())
                .orElseThrow(() -> new NoSuchElementException("Не найден элемент"));

        assertEquals(expected, actual);

    }

    Timesheet createTimesheet() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        Timesheet timesheet = new Timesheet();
        timesheet.setMinutes(random.nextInt(1, 100));
        timesheet.setCreatedAt(LocalDate.now().minusDays(random.nextInt(1, 100)));
        timesheet.setEmployeeId(random.nextLong(1, 100L));
        timesheet.setProjectId(random.nextLong(1, 100L));

        return timesheetRepository.save(timesheet);
    }
}