package by.tms;

import org.springframework.stereotype.Component;

@Component
public class TaskRepository {

    @PrintRunTime
    void showTask() {
        System.out.println("showTask repository");
    }
}
