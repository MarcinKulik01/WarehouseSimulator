package com.company.Thread;

import java.time.LocalDate;

public class Time extends Thread implements Runnable{

    private LocalDate localDate;

    public Time(LocalDate localDate) {
        this.localDate = localDate;

    }

    @Override
     public void run() {
        while (true) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException exc) {
                System.out.println("Wątek symulacji dnia zoostał przerwany.");
                return;
            }
            localDate = localDate.plusDays(1);
        }
    }

    synchronized public LocalDate getLocalDate(){
        return localDate;
    }

}
