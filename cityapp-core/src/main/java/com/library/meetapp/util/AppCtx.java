package com.library.meetapp.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppCtx {

    @Autowired
    private ApplicationContext appContext;

    private static AppCtx instance;


    @PostConstruct
    protected void initialize() {
        instance = this;
    }

    public static ApplicationContext getContext() {
        return instance.appContext;
    }

    public static <T> T getBean(Class<T> beanType)  {
        return getContext().getBean(beanType);
    }


    public static <T> Optional<T>  getOptionalBean(Class<T> beanType) {
        try {
            return Optional.of(getContext().getBean(beanType));
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.empty();

        }
    }


    public static <T> T getBeanOrInstantiate(Class<T> beanType) {

        return getOptionalBean(beanType).orElseGet(() -> {
            try {
                return beanType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

    }




}
