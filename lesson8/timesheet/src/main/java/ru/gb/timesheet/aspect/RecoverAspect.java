package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class RecoverAspect {

    @Pointcut("@annotation(ru.gb.timesheet.aspect.Recover)") // methods
    public void recoverTypePointcut() {}

    @Around(value = "recoverTypePointcut()")
    public Object recoverThrowingTimesheetServiceMethods(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        try {
            return pjp.proceed();
        } catch (Throwable e) {
            if (method.isAnnotationPresent(Recover.class)) {
                Class<?> outputClass = ((MethodSignature) methodSignature).getReturnType();
                return defaultValue(outputClass);
            }
            throw new Throwable(e);
        }

    }

    public static <B> Object defaultValue(Class<B> clazz) {
        return Array.get(Array.newInstance(clazz, 1),0);
    }

}
