package com.oceanli;

@RpcService(IHelloService.class)
public class HelloService implements IHelloService {
    @Override
    public String sayHello(String content) {

        System.out.println("hello, " + content);
        return "hello, " + content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("save user: " + user);
        return "save user succeed";
    }
}
