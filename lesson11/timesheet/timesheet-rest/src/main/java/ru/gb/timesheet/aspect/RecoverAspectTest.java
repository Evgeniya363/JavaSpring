package ru.gb.timesheet.aspect;

import org.springframework.stereotype.Component;
import ru.gb.aspect.logging.Logging;
import ru.gb.aspect.recover.Recover;

import java.util.Optional;

@Component
public class RecoverAspectTest {
    @Logging
    @Recover
    public Integer getIntegerValue() {
        System.out.println(1/0);
        return 0;
    }

    @Logging
    @Recover
    public int getIntValue() {
        System.out.println(1/0);
        return 0;
    }

    @Logging
    @Recover
    public Optional<?> getOptionalValue() {
        System.out.println(1/0);
        return Optional.empty();
    }
}
