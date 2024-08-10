package ru.gb.aspect.logging;

import lombok.Data;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.logging")
@Data
public class LoggingProperties {

//    private LoggingLevel level; // Без Enum LoggingLevel
    private Level level = Level.DEBUG;
    //
//    @Value("${print-args}")
    private boolean printArgs = true;

}
