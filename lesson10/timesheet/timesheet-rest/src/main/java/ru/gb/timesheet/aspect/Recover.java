package ru.gb.timesheet.aspect;

import org.slf4j.event.Level;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Recover {
// recover - восстанавливать
//    Class<?>[] noRecoverFor() default {};

//    boolean enabled() default true;
//
//    Level level() default Level.DEBUG;

}