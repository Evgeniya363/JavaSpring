package ru.gb.timesheet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class RecoverAspect {

//    @Pointcut("@annotation(ru.gb.timesheet.aspect.Recover)")
//    public void recoverMethodsPointcut() {}
//
//    @Around(value = "recoverMethodsPointcut()")
    @Around("@within(ru.gb.aspect.Recover)")
    public Object afterThrowingTimesheetServiceMethods(ProceedingJoinPoint pjp) throws InstantiationException, IllegalAccessException {

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        if (method.isAnnotationPresent(Recover.class)) {
            Class<?> returnType = ((MethodSignature) methodSignature).getReturnType();
            try {
                return pjp.proceed();
            } catch (Throwable e) {


                System.out.println("return ="+returnType);

                log.info("Run time exception: {}\n{}", e.getClass().getSimpleName(), e.getMessage());
                if (returnType.isPrimitive()) {
                    returnType = ClassUtils.primitiveToWrapper(returnType);
                }
            }

            //
            try {
                return returnType.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

}
