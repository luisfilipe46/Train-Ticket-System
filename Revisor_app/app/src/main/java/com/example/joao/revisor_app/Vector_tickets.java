package com.example.joao.revisor_app;

import java.util.Vector;

/**
 * Created by joao on 08-11-2015.
 */
public class Vector_tickets {
    public Vector<Ticket> tickets;
    private static Vector_tickets ourInstance = new Vector_tickets();

    public static Vector_tickets getInstance() {
        return ourInstance;
    }

    private Vector_tickets() {
        tickets = new Vector<>();
    }

    private Vector<Ticket> getTickets()
    {
        return tickets;
    }
}
