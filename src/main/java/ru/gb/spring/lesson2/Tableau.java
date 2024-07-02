package ru.gb.spring.lesson2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@Scope("singleton")
@RestController
public class Tableau {
    @Autowired
    TicketNumberGenerator generator;

    public Tableau() {
        System.out.println("Конструктор Tableau");
    }

    @RequestMapping("/ticket")
    public String newTicket() {
        return new Ticket(generator.createNewNumber()).toString();

    }
}
