package ru.gb.timesheet.service;

import com.netflix.appinfo.InstanceInfo;
//import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import ru.gb.timesheet.client.EmployeeResponse;
import ru.gb.timesheet.client.ProjectResponse;
import ru.gb.timesheet.client.TimesheetResponse;
import ru.gb.timesheet.page.TimesheetPageDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {
    private final DiscoveryClient discoveryClient;

    private RestClient restClient() {
//        Application application = discoveryClient.getApplication();
        List<ServiceInstance> instances = discoveryClient.getInstances("TIMESHEET-REST");
        int instancesCount = instances.size();
        int instanceIndex = ThreadLocalRandom.current().nextInt(0, instancesCount);

        ServiceInstance instance = instances.get(instanceIndex);
        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
        return RestClient.create(uri);


    }
    public Optional<TimesheetPageDTO> findById(Long id) {
        try {
            TimesheetResponse timesheet = restClient().get()
                    .uri("/timesheets/" + id)
                    .retrieve()
                    .body(TimesheetResponse.class);
            return Optional.of(createTimesheetPageDTO(timesheet, id));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }

    }

    public List<TimesheetPageDTO> findAll() {
        return createListTimesheetPageDTO("/timesheets");
    }

    public List<TimesheetPageDTO> findByEmployeeId(Long id) {
        return createListTimesheetPageDTO("/employees/" + id + "/timesheets");
    }

    public List<TimesheetPageDTO> findByProjectId(Long id) {
        return createListTimesheetPageDTO("/projects/" + id + "/timesheets");
    }

    private List<TimesheetPageDTO> createListTimesheetPageDTO(String uri) {
        List<TimesheetResponse> timesheets = restClient().get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        List<TimesheetPageDTO> result = new ArrayList<>();

        if (timesheets != null) {
            for (TimesheetResponse timesheet : timesheets) {
                result.add(createTimesheetPageDTO(timesheet, timesheet.getId()));
            }
        }
        return result;
    }

    private TimesheetPageDTO createTimesheetPageDTO(TimesheetResponse timesheet, Long id) {
        TimesheetPageDTO timesheetPageDTO = new TimesheetPageDTO();
        timesheetPageDTO.setId(String.valueOf(id));
        timesheetPageDTO.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageDTO.setCreatedAt(timesheet.getCreatedAt().format((DateTimeFormatter.ISO_DATE)));

        ProjectResponse project = restClient().get()
                .uri("/projects/" + timesheet.getProjectId())
                .retrieve()
                .body(ProjectResponse.class);
        timesheetPageDTO.setProjectName(project.getName());
        
        EmployeeResponse employee = restClient().get()
                .uri("/employees/" + timesheet.getEmployeeId())
                .retrieve()
                .body(EmployeeResponse.class);
        timesheetPageDTO.setEmployeeName(employee.getSecondName() + " " + employee.getFirstName());
//        timesheetPageDTO.setEmployeeName("Name");
        return timesheetPageDTO;
    }

}
