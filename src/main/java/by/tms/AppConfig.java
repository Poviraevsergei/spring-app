package by.tms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@ComponentScan("by.tms") //вычитай рефлексией все классы по этому и вложенным путям
@Configuration //Тот-же component но используется для настройки приложения
public class AppConfig {

    @Scope("singleton")
    @Bean
    public static Student getStudent() {
        return new Student();
    }
}
