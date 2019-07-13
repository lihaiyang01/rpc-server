package com.oceanli;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class RpcProcessHandler implements Runnable {

    private Socket client;

    private Object service;

    private Map<String, Object> serviceMap;

    /*public RpcProcessHandler(Object service, Socket client) {
        this.service = service;
        this.client = client;
    }*/
    public RpcProcessHandler(Map<String, Object> serviceMap, Socket client) {
        this.serviceMap = serviceMap;
        this.client = client;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            InputStream inputStream = client.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
            RpcRequest request = (RpcRequest)objectInputStream.readObject();
            Object result = invoke(request);
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object invoke(RpcRequest request) {

        Object result = null;
        String className = request.getClassName();
        Object service = this.serviceMap.get(className);
        if (service == null) {
            throw new RuntimeException("service not found : " + className);
        }
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        Class<?>[] paramTypes = new Class<?>[parameters.length];
        for (int i = 0 ; i < parameters.length; i ++) {
            paramTypes[i] = parameters[i].getClass();
        }
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getMethod(methodName, paramTypes);
            result = method.invoke(service, parameters);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
