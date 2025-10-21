package by.tms.qualifier;

import by.tms.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainQualifier {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AdminUser user = ((AdminUser) ((App) context.getBean("app")).getUser());
        System.out.println(user.name);
    }
}
