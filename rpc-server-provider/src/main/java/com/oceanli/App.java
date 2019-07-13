package com.oceanli;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        /*IHelloService helloService = new HelloService();
        RpcProxyServer rpcProxyServer = new RpcProxyServer();
        rpcProxyServer.publisher(helloService, 8080);*/
        AnnotationConfigApplicationContext
                context = new AnnotationConfigApplicationContext(SpringConfig.class);
    }
}
