package ru.gb.aspect.recover;

import lombok.Data;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.logging")
@Data
public class RecoverProperties {

//    private LoggingLevel level; // Без Enum LoggingLevel
    private Level level = Level.DEBUG;
    //print-args
    private boolean printArgs = true;

}
