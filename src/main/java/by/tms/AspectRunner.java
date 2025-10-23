package by.tms;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Aspect
@Component
public class AspectRunner {
    //      Срез через аннотацию @Pointcut
    //1. К каким методам применять АОП (Pointcut)
    //1.1 "within(by.tms.*)"
    //1.2 "execution(public * *owIn*())"
    //1.3 "@annotation(by.tms.PrintRunTime)"
    @Pointcut("within(by.tms.UserRepository)")
    public void pointcutMethodBefore() {
    }

    //2. В какой именно момент вызывать (Advice)
    //2.1   @Before
    //2.2   @After
    //2.3   @AfterThrowing
    //2.4   @AfterReturning
    //2.5   @Around
    @Before(value = "@annotation(by.tms.PrintRunTime)")
    public void before(JoinPoint jp) { //Можем передать объект JoinPoint
        System.out.println("Aspect logic Before Method");
    }

    @After(value = "@annotation(by.tms.PrintRunTime)")
    public void after() {
        System.out.println("Aspect logic After Method");
    }

    @AfterThrowing(value = "@annotation(by.tms.PrintRunTime)", throwing = "ex")
    public void afterThrowingMethod(ArithmeticException ex) {
        System.out.println("Aspect logic After Throwing Method: " + ex.getMessage());
    }

    @AfterReturning(value = "@annotation(by.tms.PrintRunTime)", returning = "value")
    public void afterReturningMethod(Object value) {
        System.out.println("Aspect logic After Returning Method: " + value);
    }

    @Around(value = "@annotation(by.tms.PrintRunTime)")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        Timestamp start = Timestamp.valueOf(LocalDateTime.now());
        jp.proceed();   //запускает целевой метод (getUserById(int id))
        Timestamp end = Timestamp.valueOf(LocalDateTime.now());
        System.out.println("Время работы метода " + jp.getSignature().getName() + ": " + (end.getTime() - start.getTime() + "ms."));
    }
}


//TODO: 3. Как прокси работает под капотом
