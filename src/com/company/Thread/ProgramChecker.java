package com.company.Thread;

import com.company.Service.Service;
import com.company.Warehouse.Warehouse;

public class ProgramChecker extends Thread implements Runnable{

    private Time time;
    private Warehouse warehouse;
    private Service service;

    public ProgramChecker(Time time, Warehouse warehouse, Service service) {
        this.time = time;
        this.warehouse = warehouse;
        this.service = service;
    }

    @Override
     public void run() {
        while (true) {

            try {
                Thread.sleep(10000);
            } catch(InterruptedException exc) {
                System.out.println("Wątek symulacji dnia zoostał przerwany.");
                return;
            }

                warehouse.checkAllWarehouses(time.getLocalDate());
                service.checkServices(time.getLocalDate());
                service.checkParkingPlaces(time.getLocalDate());
        }
    }
}
