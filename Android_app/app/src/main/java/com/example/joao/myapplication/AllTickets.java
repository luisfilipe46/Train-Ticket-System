package com.example.joao.myapplication;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by joao on 03-11-2015.
 */
public class AllTickets implements Serializable {

    //private static AllTickets allTickets = new AllTickets( );

    public Vector<Ticket> tickets;

    public AllTickets()
    {
        tickets = new Vector<>();
        for(int i = 0; i<20; i++)
        {
            Ticket t;
            if(i == 3 || i==5 || i==1) {
                t = new Ticket("One Not used", i, i + 1, "2015-26-6", i + ":30", (i + 1) + ":30", 2, false);
            }
            else
            {
                t = new Ticket("One Used", i, i + 1, "2015-26-6", i + ":30", (i + 1) + ":30", 3, true);
            }


            tickets.add(t);
        }


    }

    /*public static AllTickets getInstance( ) {

        if(allTickets == null) {
            allTickets = new AllTickets();
        }

        return allTickets;
    }*/
}
