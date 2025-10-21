package by.tms.diJavaStyle;

public class Main {
    public static void main(String[] args) {
        Car car = new Car(new Engine());
        System.out.println(car.getEngine());
    }
}
