package ru.gb.timesheet.aspect;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecoverAspectTest {
    @Recover
    public Integer getIntegerValue() {
        System.out.println(1/0);
        return 0;
    }

    @Recover
    public int getIntValue() {
        System.out.println(1/0);
        return 0;
    }

    @Recover
    public Optional<?> getOptionalValue() {
        System.out.println(1/0);
        return Optional.empty();
    }
}
