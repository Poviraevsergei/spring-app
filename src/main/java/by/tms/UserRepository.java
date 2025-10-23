package by.tms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserRepository {
    private TaskRepository taskRepository;

    @Autowired
    public UserRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PrintRunTime
    public String getUserById(int id) {
        System.out.println("UserRepository ищет юзера" + id);
        taskRepository.showTask();
        showInfo();// АОП не отработает для него
        return "Это пользователь";
    }

    @PrintRunTime
    public void showInfo() {
        System.out.println("UserRepository showInfo");
    }
}

