package com.company.Main;

import com.company.Person.Person;
import com.company.Service.Service;
import com.company.Thread.ProgramChecker;
import com.company.Thread.Time;
import com.company.Vehicle.*;
import com.company.Warehouse.Item;
import com.company.Warehouse.Warehouse;

import java.time.LocalDate;
import java.util.LinkedList;

public class Main{

    public static void main(String[] args) throws Exception {

        LocalDate localDate = LocalDate.now();

        LinkedList<Service> services = new LinkedList<>();

        Service service = new Service("Warszawa");

        Warehouse warehouse = new Warehouse("Ochota",10,10);

        service.addWarehouses(warehouse);
        services.add(service);

        Person person = new Person("Adam","Baryla","01234567899","warszawa","02-01-2018");
        Person person1 = new Person("Jan","Baryla","01234567899","warszawa","02-01-2018");
        Person person2 = new Person("Kacper","Baryla","01234567899","warszawa","02-01-2018");
        Person person3 = new Person("Mikolaj","Baryla","01234567899","warszawa","02-01-2018");
        Person person4 = new Person("Bartosz","Baryla","01234567899","warszawa","02-01-2018");

        warehouse.rent(1,person,localDate);
        warehouse.rent(2,person1,localDate);
        warehouse.rent(3,person2,localDate);
        warehouse.rent(4,person3,localDate);
        warehouse.rent(5,person4,localDate);
        warehouse.rent(6,person,localDate);
        warehouse.rent(7,person,localDate);

        warehouse.addItem(new Item(10,"opona1"),2);
        warehouse.addItem(new Item(10,"szklanka"),2);
        warehouse.addItem(new Item(15,"karton"),2);
        warehouse.addItem(new Item(16,"zabawki"),2);
        warehouse.addItem(new Item(11,"baranek"),2);

        warehouse.addItem(new Item(10,"opona1"),3);
        warehouse.addItem(new Item(10,"szklanka"),3);
        warehouse.addItem(new Item(15,"karton"),3);
        warehouse.addItem(new Item(16,"zabawki"),3);
        warehouse.addItem(new Item(11,"baranek"),3);

        warehouse.addItem(new Item(10,"opona1"),4);
        warehouse.addItem(new Item(10,"szklanka"),4);
        warehouse.addItem(new Item(15,"karton"),4);
        warehouse.addItem(new Item(16,"zabawki"),4);
        warehouse.addItem(new Item(11,"baranek"),4);

        warehouse.addItem(new Item(10,"opona1"),5);
        warehouse.addItem(new Item(10,"szklanka"),5);
        warehouse.addItem(new Item(15,"karton"),5);
        warehouse.addItem(new Item(16,"zabawki"),5);
        warehouse.addItem(new Item(11,"baranek"),5);

        person.setFirstRent(localDate);
        person1.setFirstRent(localDate);
        person2.setFirstRent(localDate);
        person3.setFirstRent(localDate);
        person4.setFirstRent(localDate);

        warehouse.addPermision(1,person,person1);
        warehouse.addPermision(2,person1,person2);
        warehouse.addPermision(3,person2,person3);
        warehouse.addPermision(4,person3,person4);
        warehouse.addPermision(5,person4,person3);
        warehouse.addPermision(6,person,person2);
        warehouse.addPermision(7,person,person3);
        warehouse.addPermision(1,person,person4);
        warehouse.addPermision(2,person1,person3);
        warehouse.addPermision(3,person2,person4);
        warehouse.addPermision(4,person3,person);


        LinkedList<Person> people = new LinkedList<>();
        people.add(person);
        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);

         person.setCars(new CityCar(2334,4,"Astra", Car.Type.city, Car.Color.blue,true,3,16));
         person.setCars(new CityCar(232,5,"Astra",Car.Type.city, Car.Color.blue,true,3,16));
         person1.setCars(new OffRoadCar(23334,6,"Range rover",Car.Type.offRoad, Car.Color.red,true,true,"Niemcy"));
         person2.setCars(new Motorcycle(23324,2,"Speedd",Car.Type.motorcycle, Car.Color.yellow,190,"BMW",true));
         person3.setCars(new Amphibious(231134,8,"Amfia",Car.Type.amphibious, Car.Color.blue,"1817",5,true));
         person4.setCars(new CityCar(2534,8,"Elka",Car.Type.city, Car.Color.black,true,3,16));
         person3.setCars(new Amphibious(522,8,"ELkaBelka",Car.Type.amphibious, Car.Color.blue,"1817",5,true));
         person4.setCars(new CityCar(2334,8,"Fuller",Car.Type.city, Car.Color.black,false,5,13));
         person4.setCars(new Amphibious(2334,8,"Fuller",Car.Type.amphibious, Car.Color.black,"2017",5,false));

        service.startService(person,person.getCarByID(0),localDate,1);
        service.startService(person1,person1.getCarByID(2),localDate,1);
        service.startService(person2,person2.getCarByID(3),localDate,0);
        service.startService(person3,person3.getCarByID(4),localDate,1);

        service.startMechanikService(person,person.getCarByID(1),localDate,"Opona",1);
        service.startMechanikService(person4,person4.getCarByID(5),localDate,"Maska",0);
        service.startMechanikService(person3,person3.getCarByID(6),localDate,"Dach",0);
        service.startMechanikService(person4,person4.getCarByID(7),localDate,"Opona",1);
        service.startMechanikService(person4,person4.getCarByID(8),localDate,"OponaPrawa",1);

        ProgramFunctions programFunctions = new ProgramFunctions();
        Person logPerson = programFunctions.chosePerson(people);

        int numberOfActivities=-1;


        Time time = new Time(localDate);
        ProgramChecker programChecker = new ProgramChecker(time,warehouse,service);

        time.start();
        programChecker.start();


        while(numberOfActivities!=17) {

            if(logPerson == null)
                numberOfActivities=17;
            else {
                programFunctions.showMenu(logPerson, time.getLocalDate(),service);
                numberOfActivities = programFunctions.scan();


                switch (numberOfActivities) {
                    case 1 -> logPerson = programFunctions.chosePerson(people);
                    case 2 -> programFunctions.showFree(warehouse);
                    case 3 -> programFunctions.showPerson(logPerson);
                    case 4 -> programFunctions.rentWarehouse(logPerson, warehouse, time.getLocalDate());
                    case 5 -> programFunctions.showMyWarehouses(logPerson, warehouse);
                    case 6 -> programFunctions.addPermision(people, logPerson, warehouse);
                    case 7 -> programFunctions.getPermision(people, logPerson, warehouse);
                    case 8 -> programFunctions.putItem(logPerson, warehouse);
                    case 9 -> programFunctions.takeItem(logPerson, warehouse);
                    case 10 -> programFunctions.putCar(logPerson, time.getLocalDate(), service);
                    case 11 -> programFunctions.getCar(logPerson, service);
                    case 12 -> programFunctions.save(warehouse, service, time.getLocalDate());
                    case 13 -> programFunctions.serviceTicket(logPerson, service, time.getLocalDate(),warehouse);
                    case 14 ->  programFunctions.putTire(logPerson, warehouse);
                    case 15 ->  programFunctions.takeTire(logPerson, warehouse);
                    case 16 ->  programFunctions.extension(logPerson, warehouse,time.getLocalDate());
                    case 17 -> numberOfActivities = 17;
                    default -> System.out.println("Wybrano zły numer");
                }
            }

        }
        time.stop();
        programChecker.stop();
    }




}
