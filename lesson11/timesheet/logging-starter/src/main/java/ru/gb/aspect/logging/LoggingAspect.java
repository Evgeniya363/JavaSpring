package ru.gb.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j // Slf4j - Simple logging facade for java
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties properties;

    @Pointcut("@annotation(ru.gb.aspect.logging.Logging)") // method
    public void loggingMethodsPointcut() {}

    @Pointcut("@within(ru.gb.aspect.logging.Logging)") // class
    public void loggingTypePointcut() {}

    @Around(value = "loggingMethodsPointcut() || loggingTypePointcut()")
    public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {

        String methodName = pjp.getSignature().getName();
        // log.atLevel(properties.getLevel().slf4j()).log("Before -> TimesheetService{}", methodName);

        StringBuilder sb = new StringBuilder(methodName);

        if(properties.isPrintArgs()) {
            Object[] args = pjp.getArgs();
            sb.append(" Arguments:");
            for (Object arg: args) {
                sb.append(" ").append(arg.getClass().getSimpleName())
                        .append(" = "). append(arg);
            }
        }

        log.atLevel(properties.getLevel()).log("Before -> TimesheetService{} {}", methodName, sb);
        try {
            return pjp.proceed();
        } finally {
            log.atLevel(properties.getLevel()).log("After -> TimesheetService{}", methodName);
        }
    }



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

}
