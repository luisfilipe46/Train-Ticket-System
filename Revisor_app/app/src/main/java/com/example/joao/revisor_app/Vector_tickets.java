package com.example.joao.revisor_app;

import java.util.Vector;

/**
 * Created by joao on 08-11-2015.
 */
public class Vector_tickets {
    public Vector<Ticket> tickets;
    public Vector<Integer> ticketsForValidation;
    private static Vector_tickets ourInstance = new Vector_tickets();

    public static Vector_tickets getInstance() {
        return ourInstance;
    }

    private Vector_tickets() {
        tickets = new Vector<>();
        ticketsForValidation = new Vector<>();
    }

    private Vector<Ticket> getTickets()
    {
        return tickets;
    }

    public Ticket existsTicket(String qrCode)
    {
        for (int i =0;i< tickets.size();i++)
        {
            Ticket t = tickets.get(i);
            if(t.QrCodeText.equals(qrCode))
            {
                return t;
            }
        }
        return null;
    }


}
