package ru.gb.aspect.logging;


import org.slf4j.event.Level;

public enum LoggingLevel {

    DEBUG(Level.DEBUG),
    INFO(Level.INFO),
    WARNING(Level.WARN);

    private final Level slf4j;

    LoggingLevel(Level slf4j) {
        this.slf4j = slf4j;
    }

    public Level slf4j() {
        return slf4j;
    }
}
