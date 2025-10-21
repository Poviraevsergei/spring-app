package by.tms.qualifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminUser implements User {
    @Value("${username}") //внедряет значения по ключу
    String name;
}
