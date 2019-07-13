package com.oceanli;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GPRpcServer implements ApplicationContextAware, InitializingBean {

    public Map<String, Object> serviceMap = new HashMap<>();

    private int port = 8080;

    ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(RpcService.class);
        for (Object service: beans.values()) {
            RpcService rpcService = service.getClass().getAnnotation(RpcService.class);
            Class<?> serviceName = rpcService.value();
            serviceMap.put(serviceName.getName(), service);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        try {

            ServerSocket server = new ServerSocket(port);
            System.out.println("BIO RPC START, port: " + port);
            while(true) {
                Socket client = server.accept();
                executorService.execute(new RpcProcessHandler(serviceMap, client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
