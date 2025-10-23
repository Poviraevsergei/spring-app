package by.tms.proxy;

import by.tms.UserRepository;

public class ProxyUserRepository {
    UserRepository userRepository;

    public ProxyUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void proxyMethodGetUserById(){
        before();
        userRepository.getUserById(10);
        after();
    }

    public void proxyMethodShowInfo(){
        before();
        userRepository.showInfo();
    }

    public void before(){
        System.out.println("Before");
    }

    public void after(){
        System.out.println("After");
    }
}
