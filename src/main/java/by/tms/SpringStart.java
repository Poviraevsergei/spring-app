package by.tms;

import org.springframework.beans.factory.aot.AotServices;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ConcurrentReferenceHashMap;

@ComponentScan("by.tms") //вычитай рефлексией все классы по этому и вложенным путям
public class SpringStart {
    public static void main(String[] args) {
        //1. Создание контекста (Поднятие контекста - запуск Spring приложения)
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringStart.class);

        //2. попросить Bean(Объект который создает Spring) чтобы убедится что спринг сам создает/хранит/управляет объектами
        Student student = (Student) context.getBean("getStudent");
        System.out.println(student.getName());
    }

    @Bean
    public static Student getStudent() {
        return new Student();
    }
}
