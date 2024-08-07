package ru.gb.timesheet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Stubber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.repository.EmployeeRepository;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest // Скажет JUNIT, что надо поднять контекст: все бины
class ProjectServiceTest {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TimesheetRepository timesheetRepository;

    @Autowired
    ProjectService projectService;


    @Test
    void findByIdEmpty() {
        // given
        assertFalse(projectRepository.existsById(2L));

        Assertions.assertTrue(projectService.getById(2L).isEmpty());
    }

    @Test
    void findByIdPresent() {
        // given
        Project project = new Project();
        project.setName("New Project");
        project = projectRepository.save(project);

        // when
        Optional<Project> actual = projectService.getById(project.getId());

        // then
        assertTrue(actual.isPresent());
        assertEquals(actual.get().getId(), project.getId());
        assertEquals(actual.get().getName(), project.getName());

    }
}


//@SpringBootTest(classes = {ProjectService.class, ProjectRepository.class, TimesheetRepository.class})
//    // Тогда вместо @Autowired - @MockBean
//class ProjectServiceTest {
//
//    @MockBean
//    ProjectRepository projectRepository;
//    @MockBean
//    TimesheetRepository timesheetRepository;

//    @Autowired
//    ProjectService projectService;
//
//
//    @Test
//    void testGetById() {
        // Вот так не работает:
        // doReturn(Optional.empty()).when(projectRepository.findById(2L));

        // org.mockito.exceptions.misusing.UnfinishedStubbingException:
        //Unfinished stubbing detected here:
        //-> at ru.gb.timesheet.service.ProjectServiceTest.testGetById(ProjectServiceTest.java:38)
        //
        //E.g. thenReturn() may be missing.
        //Examples of correct stubbing:
        //    when(mock.isOk()).thenReturn(true);
        //    when(mock.isOk()).thenThrow(exception);
        //    doThrow(exception).when(mock).someVoidMethod();
        //Hints:
        // 1. missing thenReturn()
        // 2. you are trying to stub a final method, which is not supported
        // 3. you are stubbing the behaviour of another mock inside before 'thenReturn' instruction is completed
        //
        //
        //	at ru.gb.timesheet.service.ProjectServiceTest.testGetById(ProjectServiceTest.java:38)
        //	at java.base/java.lang.reflect.Method.invoke(Method.java:580)
        //	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        //	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)

//        when(projectRepository.findById(2L)).thenReturn(Optional.empty());
//        Assertions.assertTrue(projectService.getById(2L).isEmpty());
//    }
//}