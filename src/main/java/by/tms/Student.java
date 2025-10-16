package by.tms;

import org.springframework.stereotype.Component;

@Component("beanStudent") //создал bean этого класса
public class Student {
    private String name = "Dima";
    private int age = 18;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
