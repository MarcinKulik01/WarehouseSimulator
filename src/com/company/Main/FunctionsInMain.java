package com.company.Main;

import com.company.Exception.NeverRentException;
import com.company.Exception.ToManyThingsException;
import com.company.Exception.ToManyWarehousesException;
import com.company.Person.Person;
import com.company.Service.Service;
import com.company.Warehouse.Warehouse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

interface FunctionsInMain {

        void serviceTicket(Person logPerson, Service service, LocalDate localDate, Warehouse warehouse);
        void save (Warehouse warehouse, Service service,LocalDate localDate) throws IOException;
        void putCar(Person person, LocalDate localDate, Service service);
        void getCar(Person person,Service service);
        void putItem(Person person, Warehouse warehouse) throws Exception;
        void takeItem(Person person, Warehouse warehouse);
        Person chosePerson(LinkedList<Person> people);
        void showFree(Warehouse warehouse);
        void showPerson(Person person);
        void addPermision(LinkedList<Person> people, Person person,Warehouse warehouse);
        void getPermision(LinkedList<Person> people, Person person,Warehouse warehouse);
        void rentWarehouse(Person person, Warehouse warehouse,LocalDate localDate) throws ToManyWarehousesException;
        void showMyWarehouses(Person person, Warehouse warehouse) throws NeverRentException;
        void putTire(Person logPerson, Warehouse warehouse) throws ToManyThingsException;
        void takeTire(Person logPerson, Warehouse warehouse);
        void extension(Person logPerson, Warehouse warehouse, LocalDate localDate);
        int scan();
        void showMenu(Person logPerson, LocalDate localDate, Service service);

}
