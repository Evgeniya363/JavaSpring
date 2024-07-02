package ru.gb.spring.lesson2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Service
@Scope("singleton")
public class TicketNumberGenerator {
    public TicketNumberGenerator() {
        System.out.println("Конструктор TicketNumberGenerator");
    }
    public String createNewNumber() {
        return "Ticket #" + UUID.randomUUID();
    }
}
