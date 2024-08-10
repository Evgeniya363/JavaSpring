package ru.gb.aspect.recover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

@Slf4j // Slf4j - Simple logging facade for java
@Aspect
@RequiredArgsConstructor
public class RecoverAspect {

    private final RecoverProperties properties;

    @Pointcut("@annotation(ru.gb.aspect.recover.Recover)") // method
    public void recoverMethodsPointcut() {}

    @Pointcut("@within(ru.gb.aspect.recover.Recover)") // class
    public void recoverTypePointcut() {}

    @Around(value = "recoverMethodsPointcut() || recoverTypePointcut()")
    public Object recoverMethod(ProceedingJoinPoint pjp) throws Throwable {

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
