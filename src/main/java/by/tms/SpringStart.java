package by.tms;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringStart {
    public static void main(String[] args) {
        //1. Создание контекста (Поднятие контекста - запуск Spring приложения)
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-settings.xml");

        //2. попросить Bean(Объект который создает Spring) чтобы убедится что спринг сам создает/хранит/управляет объектами
        Student student = (Student) context.getBean("beanStudent");
        System.out.println(student.getName());
    }
}
