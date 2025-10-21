package by.tms.diSpringStyle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(BeanDefinition.SCOPE_SINGLETON)
@Component
public class Car {
    private String model;
    private String brand;
    private String color;

    //@Autowired  //1. Над полем(Не желательно) Внедряет другой объект сюда
    private Engine engine;

    @Autowired //3. Лучший способ. Начиная с версии 4.3 если один конструктор то можно @Autowired не ставить.
    public Car(Engine engine) {
        this.engine = engine;
    }

    @PostConstruct
    public void init() {
        System.out.println("Init method Inside Car class!");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Destroy Method Inside Car class!");
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Engine getEngine() {
        return engine;
    }

    //@Autowired //2. Через методы
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
