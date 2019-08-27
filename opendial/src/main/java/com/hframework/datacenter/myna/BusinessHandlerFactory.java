package com.hframework.datacenter.myna;

import java.util.HashMap;
import java.util.Map;

public class BusinessHandlerFactory {
    private static BusinessHandlerFactory factory;
    private Map<String, BusinessHandler> handlerMap;

    private BusinessHandlerFactory(){
        handlerMap = new HashMap<>();
    }

    public static BusinessHandlerFactory getFactory() {
        if(factory == null) {
            synchronized (BusinessHandlerFactory.class) {
                if(factory == null) {
                    factory = new BusinessHandlerFactory();
                }
            }
        }
        return factory;
    }

    public void register(String code, BusinessHandler handler) {
        handlerMap.put(code, handler);
    }

    public Map<String, BusinessHandler> getHandlerMap() {
        return handlerMap;
    }
}
