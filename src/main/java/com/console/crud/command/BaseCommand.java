package com.console.crud.command;

import org.springframework.stereotype.Component;

@Component
public abstract class BaseCommand implements Command{

    private final int code;
    private final String description;

    protected BaseCommand(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
