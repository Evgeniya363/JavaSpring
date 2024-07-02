package ru.gb.spring.lesson2;

import java.time.LocalDateTime;

public class Ticket {
    String number;
    LocalDateTime createAt;

    public Ticket(String number) {
        this.number = number;
        this.createAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "number='" + number + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
