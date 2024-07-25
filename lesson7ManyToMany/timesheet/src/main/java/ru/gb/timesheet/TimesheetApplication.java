package ru.gb.timesheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gb.timesheet.model.*;
import ru.gb.timesheet.repository.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class TimesheetApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TimesheetApplication.class, args);

        RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
        Role roleAdmins = new Role();
        roleAdmins.setName(RoleEnum.ADMIN.getName());

        Role roleUsers = new Role();
        roleUsers.setName(RoleEnum.USER.getName());

        Role roleRests = new Role();
        roleUsers.setName(RoleEnum.REST.getName());

        UserRepository userRepository = ctx.getBean(UserRepository.class);
        User user = new User();
        user.setLogin("user"); //user
        user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq");

        user.getRoles().add(roleUsers);
        roleUsers.getUsers().add(user);

        User admin = new User();
        admin.setLogin("admin"); // admin
        admin.setPassword("$2a$12$QPoOV5Qr8sRD5lkcugid8ezyLwZmcFt2j0kh6XLsPf/IgkmLxe5SK");

        User rest = new User();
        rest.setLogin("rest"); // user
        rest.setPassword("$2a$12$tZaB13uZJJkD9zgpSiwZXeeYtOY/vjDR9XQwm2woqGHpmxhQXeTYq");

        admin.getRoles().add(roleUsers);
        admin.getRoles().add(roleAdmins);
        roleUsers.getUsers().add(admin);
        roleAdmins.getUsers().add(admin);

        roleRepository.save(roleUsers);
        roleRepository.save(roleAdmins);
        userRepository.save(user);
        userRepository.save(admin);

        System.out.println(userRepository.findUserRolesByUserId(2L));
        System.out.println(userRepository.findByLogin("user"));


        EmployeeRepository employeeRepo = ctx.getBean(EmployeeRepository.class);
        for (int i = 1; i <= 3; i++) {
            Employee employee = new Employee();
            employee.setFirstName("Employee #" + i);
            employee.setSecondName("Surname #" + i);
            employeeRepo.save(employee);
        }

        ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);
        for (int i = 1; i <= 5; i++) {
            Project project = new Project();
            project.setName("Project #" + i);
            projectRepo.save(project);
        }

        TimesheetRepository timesheetRepo = ctx.getBean(TimesheetRepository.class);

        LocalDate createdAt = LocalDate.now();
        for (int i = 1; i <= 10; i++) {
            createdAt = createdAt.plusDays(1);

            Timesheet timesheet = new Timesheet();
            timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
            timesheet.setEmployeeId(ThreadLocalRandom.current().nextLong(1, 4));
            timesheet.setCreatedAt(createdAt);
            timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));

            timesheetRepo.save(timesheet);
        }

    }

}
