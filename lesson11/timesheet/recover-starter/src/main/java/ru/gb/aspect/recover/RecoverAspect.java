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

//        String methodName = pjp.getSignature().getName();
//        // log.atLevel(properties.getLevel().slf4j()).log("Before -> TimesheetService{}", methodName);
//
//        StringBuilder sb = new StringBuilder(methodName);
//
//        if(properties.isPrintArgs()) {
//            Object[] args = pjp.getArgs();
//            sb.append(" Arguments:");
//            for (Object arg: args) {
//                sb.append(" ").append(arg.getClass().getSimpleName())
//                        .append(" = "). append(arg);
//            }
//        }
//
//        log.atLevel(properties.getLevel()).log("Before -> TimesheetService{} {}", methodName, sb);
//        try {
//            return pjp.proceed();
//        } finally {
//            log.atLevel(properties.getLevel()).log("After -> TimesheetService{}", methodName);
//        }
//    }



//    @Pointcut("execution(* ru.gb.timesheet.service.TimesheetService.*(..))")
//    public void timesheetServiceMethodsPointcut() {
//    }
//
//    // Pointcut - точка входа в аспект
//    @Before(value = "timesheetServiceMethodsPointcut()")
//    public void beforeTimesheetServiceFindById(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//
//        StringBuilder signature = new StringBuilder("Args:");
//        Object[] signatureArgs = jp.getArgs();
//        for (Object signatureArg : signatureArgs) {
//            String typeName = signatureArg.getClass().getSimpleName();
//            signature.append(" (").append(typeName).append(" = ")
//                    .append(signatureArg).append(")");
//        }
//
//        log.info("Before -> TimesheetService#{} " + signature, methodName);
//    }

//  @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
//  public void afterTimesheetServiceFindById(JoinPoint jp, Exception ex) {
//    String methodName = jp.getSignature().getName();
//    log.info("AfterThrowing -> TimesheetService#{} -> {}", methodName, ex.getClass().getName());
//  }

//}
