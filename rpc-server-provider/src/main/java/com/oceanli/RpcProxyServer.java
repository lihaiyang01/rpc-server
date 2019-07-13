package com.oceanli;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProxyServer {

    /*ExecutorService executorService = Executors.newCachedThreadPool();

    public void publisher(Object service, int port) {
        try {

            ServerSocket server = new ServerSocket(port);
            System.out.println("BIO RPC START, port: " + port);
            while(true) {
                Socket client = server.accept();
                executorService.execute(new RpcProcessHandler(service, client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
