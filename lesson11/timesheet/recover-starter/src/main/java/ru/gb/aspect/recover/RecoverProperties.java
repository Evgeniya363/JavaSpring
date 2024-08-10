package ru.gb.aspect.recover;

import lombok.Data;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("application.recover")
@Data
public class RecoverProperties {

    List<String> noRecoverFor = new ArrayList<>();

}
