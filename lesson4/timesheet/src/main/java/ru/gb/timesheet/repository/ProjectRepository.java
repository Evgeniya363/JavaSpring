package ru.gb.timesheet.repository;

import org.springframework.stereotype.Repository;
import ru.gb.timesheet.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {
    public static Long sequence = 1L;
    private List<Project> projects = new ArrayList<>();

    public ProjectRepository() {
        Project p1 = new Project();
        p1.setName("Project1");
        create(p1);
        Project p2 = new Project();
        p2.setName("Project2");
        create(p2);
    }

    public Project create(Project project) {
        project.setId(sequence++);
        projects.add(project);
        return project;
    }

    public void delete(Long id) {
        getById(id).ifPresent(projects::remove);
    }

    public Optional<Project> getById(Long id) {
        return projects.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    public List<Project> getAll() {
        return List.copyOf(projects);
    }


}
