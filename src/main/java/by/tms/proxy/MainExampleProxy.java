package by.tms.proxy;

import by.tms.TaskRepository;
import by.tms.UserRepository;

public class MainExampleProxy {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository(new TaskRepository());
        ProxyUserRepository proxyUserRepository = new ProxyUserRepository(userRepository);

        //Без АОП
        //userRepository.getUserById(10);
        //userRepository.showInfo();

        //С АОП
        proxyUserRepository.proxyMethodGetUserById();
        proxyUserRepository.proxyMethodShowInfo();
    }
}
