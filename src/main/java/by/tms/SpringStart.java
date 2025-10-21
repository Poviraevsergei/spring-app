package by.tms;

import by.tms.diSpringStyle.Car;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringStart {
    public static void main(String[] args) {
        //1. Создание контекста (Поднятие контекста - запуск Spring приложения)
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("--- main method ---");

        Car carBean = (Car)context.getBean("car");
        carBean.setColor("Black");
        System.out.println(((Car) context.getBean("car")).getColor());

        ((AnnotationConfigApplicationContext)context).close();
    }
}

//TODO: 1. IoC/DI
//TODO: 2. Жизненный цикл бина(PostConstruct, PreDestroy)
//TODO: 3. Scope(singleton*, prototype, session, global-session, request)
//singleton - только один объект при поднятии контекста.
//prototype - каждый раз новый объект кто он необходим(не выполняется destroy метод)

//TODO: 4. Annotations

