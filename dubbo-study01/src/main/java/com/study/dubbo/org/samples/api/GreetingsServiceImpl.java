package com.study.dubbo.org.samples.api;

public class GreetingsServiceImpl implements GreetingsService {
    public String sayHi(String name) {
        return "hi " + name;
    }

    @Override
    public String introduceSelf(String name) {
        return "I am " + name;
    }
}
