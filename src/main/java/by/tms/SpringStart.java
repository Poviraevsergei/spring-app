package by.tms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ComponentScan("by.tms")
public class SpringStart {
    public static void main(String[] args) {
        //1. Создание контекста (Поднятие контекста - запуск Spring приложения)
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringStart.class);

        UserRepository userRepository = (UserRepository) context.getBean("userRepository");
        userRepository.getUserById(10);
    }
}