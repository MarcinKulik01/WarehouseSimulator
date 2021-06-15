package com.company.Main;

import com.company.Exception.*;
import com.company.Person.Person;
import com.company.Service.IndependentRepairPlace;
import com.company.Service.RepairPlace;
import com.company.Service.Service;
import com.company.Vehicle.Car;
import com.company.Warehouse.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class ProgramFunctions implements FunctionsInMain {

    private Scanner scanner = new Scanner(System.in);

    public void serviceTicket(Person logPerson, Service service, LocalDate localDate, Warehouse warehouse) {

        if(logPerson.ifCar()==false&&logPerson.checkCarsToService()==true) {
            System.out.println("Podaj 1 jesli po naprawie ma zostac odstawiony na parking lub 0 jesli nie: ");

            int ifParking = scan();

            int oneOrTwo = 0;
            int index;
            int check = 0;

            while (oneOrTwo < 3) {

                System.out.println("wybierz opcje serwisowa\n 1 - samodzielna naprawa \n 2 - naprawa u mechanika");
                int number = scan();
                if (number == 1) {

                    while (check<3) {
                        logPerson.showCarsToService();
                        System.out.println("POdaj id, proba nr: " + check);

                        index = scan();

                        if (logPerson.checkCars(index) != null) {
                            check = 3;
                            service.startService(logPerson, logPerson.checkCars(index), localDate,ifParking);
                        }
                        check++;
                    }
                    oneOrTwo = 3;

                } else if (number == 2) {
                    while (check<3) {


                        logPerson.showCarsToService();

                        System.out.println("Podaj id, proba nr: " + check);
                        index = scan();

                        if (logPerson.checkCars(index) != null) {
                            check = 3;
                            System.out.println("Podaj nazwe naprawy");
                            String repairName = scanner.next();
                            service.startMechanikService(logPerson,logPerson.checkCars(index),localDate,repairName,ifParking);

                        }
                        check++;
                    }
                    oneOrTwo = 3;
                } else if(number!=1||number!=2){
                    System.out.println("Zly numer, proba nr: " + oneOrTwo);
                    oneOrTwo++;
                }
            }
        }
        else System.out.println("Brak samochodu");
    }

     public void save(Warehouse warehouse, Service service, LocalDate localDate) throws IOException {

        File plikWarehouse = new File("Warehouse.txt");
        File plikService = new File("Service.txt");
        plikWarehouse.createNewFile();
        plikService.createNewFile();

        LinkedList<ConsumerWarehouse> warehouses = warehouse.getConsumerWarehouses();
        LinkedList<ServiceWarehouse> services = warehouse.getServiceWarehouses();

        Collections.sort(warehouses);
        Collections.sort(services);

        FileWriter fileWriterWarehouse = new FileWriter(plikWarehouse);
        FileWriter fileWriterService= new FileWriter(plikService);

        try{
            int pom=0;
            int counter=1;
            fileWriterWarehouse.write( "Data zapisu: "+localDate);

            for(ConsumerWarehouse warehouse1: warehouses){
                for(int i=pom; i<services.size(); i++){
                    if(services.get(i).getvPomieszczenia()<warehouse1.getvPomieszczenia()) {
                        fileWriterWarehouse.write( "\nMagazyn nr "+counter+++"\n"+services.get(i) + "\n");
                        pom++;
                    }
                }
                fileWriterWarehouse.write("\nMagazyn nr "+counter+++"\n"+warehouse1 + "\n");
            }

            fileWriterWarehouse.flush();
            fileWriterWarehouse.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        try{
            fileWriterService.write("Aktualny stan: "+localDate+"\n\n");

            fileWriterService.write("Niezalezne miejsce: \n"+service.independentRepairPlace+"\n\n Miejsca serwisowe: \n");

            int counter = 1;
            for(RepairPlace repairPlace: service.repairPlaces) {
                fileWriterService.write("nr "+counter+++" "+repairPlace.toString()+"\n");
            }

            counter = 1;
            fileWriterService.write("\nLista oczekujacych na naprawe samodzielna: \n");
            for(Map.Entry<Car,Person> entry: service.waitingListForIndependentRepair.entrySet()) {
                fileWriterService.write("nr " + counter++ + " pojazd: " + entry.getKey().toString() + " osoba zglaszajaca: " + entry.getValue().getName() + " " + entry.getValue().getSurname() + "\n");
            }

            counter = 1;
            fileWriterService.write("\nLista oczekujacych na naprawe w serwisie: \n");
            for(Map.Entry<Car,Person> entry1: service.waitingMapForMechanikRepair.entrySet()) {
                fileWriterService.write("nr "+counter+++" pojazd: "+entry1.getKey().toString()+" osoba zglaszajaca: "+entry1.getValue().getName()+" "+entry1.getValue().getSurname()+"\n");
            }

            counter = 1;
            fileWriterService.write("\nHistoria napraw samodzielnych: \n");
            for(IndependentRepairPlace independentRepairPlace: service.independentRepairHistory) {
                fileWriterService.write("nr "+counter+++" "+independentRepairPlace+"\n");
            }

            counter = 1;
            fileWriterService.write("\nHistoria napraw w serwisie: \n");
            for(RepairPlace repairPlace: service.mechanikRepairPlacesHistory) {
                fileWriterService.write("nr "+counter+++" "+repairPlace+"\n");
            }

            counter = 1;
            fileWriterService.write("\nMiescja parkingowe: \n");
            for(ParkingSpace parkingSpace: service.getParkingSpaces()) {
                fileWriterService.write("\nMiejsce nr "+counter+++"\n "+parkingSpace+"\n");
            }

            fileWriterService.flush();
            fileWriterService.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    public void putCar(Person person, LocalDate localDate, Service service){

        if(!person.ifCar()) {
            person.showCars();
            int goodNumber = 3;

            while (goodNumber > 0) {
                System.out.println("Podaj id, Ilosc prob: " +goodNumber);
                int carId = scan();
                goodNumber--;

                if (person.checkCars(carId) != null) {
                    service.setCarOnParking(person, person.checkCars(carId), localDate);
                    person.checkCars(carId).setCarOnParking(true);
                    goodNumber = -1;
                }
            }
        } else
            System.out.println("Brak pojazdu");
    }

    public void getCar(Person person,Service service){

        if(!person.ifCar()){
            service.showCarsOnParking(person.getCars());
            int goodNumber = 3;

            while (goodNumber > 0) {
                System.out.println("Podaj id, Ilosc prob: " + goodNumber);

                int carId = scan();
                goodNumber++;
                if (person.checkCars(carId) != null) {
                    service.getCar(person, person.checkCars(carId));
                    person.checkCars(carId).setCarOnParking(false);
                    goodNumber = -1;
                }
                else {
                    goodNumber =-1;
                    System.out.println("brak samocodow do zabrania");
                }
            }
        }
        else
            System.out.println("Brak pojazdu do wlozania na parking");

    }

    public void putItem(Person person, Warehouse warehouse) throws Exception {

        int checkWarehouseId = 0;

        if(warehouse.checkIfPersonHasWarehouse(person)) {
            while (checkWarehouseId < 3) {
                System.out.println("Podaj id pomieszczenia z ktorego chcesz skorzystac");

                warehouse.showMyWarehousePermision(person);
                int c = scan();

                if (warehouse.checkWarehousePermision(person, c)) {

                    checkWarehouseId=3;

                    System.out.println("Podaj nazwe przedmiotu a nastepnie wpisz 1 i podaj V lub 2 i podaj dlugoscXszerokoscXwysokosc");
                    String nazwa = scanner.next();
                    int chose = scan();
                    int v, x, y, z;

                    if (chose == 1) {
                        v = scan();
                        try {
                            warehouse.addItem(new Item(v, nazwa), c);

                        } catch (ToManyThingsException toManyThingsException) {
                            System.out.println(toManyThingsException.getMessage());
                        }
                    } else if (chose == 2) {
                        x = scan();
                        y = scan();
                        z = scan();

                        try {
                            warehouse.addItem(new Item(x, y, z, nazwa), c);

                        } catch (ToManyThingsException toManyThingsException) {
                            System.out.println(toManyThingsException.getMessage());
                        }
                    } else
                        System.out.println("Zly numer sproboj ponownie z menu");
                }
            }
            checkWarehouseId++;
        }
        else System.out.println("Nie masz dostepu do pomieszczenia");
    }

    public void takeItem(Person person, Warehouse warehouse) {

        int checkWarehouse = 0;
        int checkItem = 0;

        if (warehouse.checkIfPersonHasWarehouse(person)) {

            System.out.println("Podaj id pomieszczenia z ktorego chcesz zabrac cos");
            warehouse.showMyWarehousePermision(person);

            while (checkWarehouse < 3) {
                int idWarehouse = scan();
                boolean istnieje = warehouse.checkWarehousePermision(person, idWarehouse);

                if (istnieje) {

                    if (warehouse.showMyItems(idWarehouse)) {

                        while (checkItem < 3) {
                            System.out.println("Wybierz przedmiot jaki chcesz zabrac (podaj id)");
                            int idItem = scan();
                            if (warehouse.checkItem(idWarehouse, idItem)) {
                                warehouse.removeItem(idWarehouse, idItem);
                                checkItem = 3;
                            }
                            checkItem++;
                        }
                    }
                    checkWarehouse = 3;
                } else {
                    System.out.println("zle id, podaj poprawne");
                    checkWarehouse++;
                }
            }
        } else System.out.println("Brak pomieszczen");
    }


    public Person chosePerson(LinkedList<Person> people) {

        int howManyTimes = 3;
        int number;

        Person logPerson = null;

        while (howManyTimes > 0) {
            System.out.println("Podaj swoje ID, pozostalo prob: "+howManyTimes);

            number = scan();

            if(number==-10)
                howManyTimes=0;
            for (Person i : people) {
                if (number == i.getId()) {
                    howManyTimes=0;
                    logPerson = i;
                }
            }
            howManyTimes--;
        }
        if(logPerson==null)
            System.out.println("NIe udalo sie zalogowac ");
        return logPerson;
    }

    public void showFree(Warehouse warehouse){
        warehouse.showFreeWarehouses();
    }

    public void showPerson(Person person){
        System.out.println(person);
    }

    public void addPermision(LinkedList<Person> people, Person person,Warehouse warehouse){

        int checkID = 3;
        int checkWarehouse = 3;

        if(warehouse.checkIfPersonHasWarehouse(person)) {

            while (checkID>0) {
                System.out.println("wybierz id osoby ktorej chcesz dodac prawa, proba: "+checkID);

                for (Person personList : people) {
                    if (personList != person) {
                        System.out.println(personList.getId()+" "+personList.getName()+" "+personList.getSurname());
                    }
                }

                int scanId = scan();

                for (Person person1 : people) {

                    if (scanId == person1.getId()) {

                        while (checkWarehouse > 0) {

                            System.out.println("podaj gdzie chcesz dodac prawo, zostalo prob: " + checkWarehouse);

                            warehouse.showOwnerWarehouse(person);
                            int warehouseId = scan();

                            if (warehouse.checkIfOwner(person, warehouseId)) {
                                warehouse.addPermision(warehouseId, person, person1);
                                checkID = -1;
                                checkWarehouse=-1;
                            }

                            checkWarehouse--;
                        }
                    }

                    checkID--;
                }
            }
            if(checkID==-1)
                System.out.println("Podano zle id");
        }
        else
            System.out.println("brak magazynow do dodania permisji ");
    }

    public void getPermision(LinkedList<Person> people, Person person,Warehouse warehouse){

        int checkID = 3;
        int checkWarehouse = 3;

        if(warehouse.checkIfPersonHasWarehouse(person)) {
            while (checkID>0) {

                System.out.println("wybierz id osoby ktorej chcesz zabrac prawa, proba: "+checkID);
                for (Person person1 : people) {
                    if (person1 != person) {
                        System.out.println(person1.getId()+" "+person1.getName()+" "+person1.getSurname());
                    }
                }

                int scanId = scan();
                for (Person person1 : people) {
                    if (scanId == person1.getId()) {

                        while (checkWarehouse > 0) {
                            System.out.println("podaj gdzie chcesz zabrac prawo, proba: " + checkWarehouse);

                            warehouse.showOwnerWarehouse(person);
                            int warehouseId = scan();

                            if (warehouse.checkIfOwner(person, warehouseId)) {
                                warehouse.getPermision(warehouseId, person, person1);
                                checkID = -1;
                                checkWarehouse=-1;
                            }
                            checkWarehouse--;
                        }
                    }
                }
                if(checkID==-1)
                    System.out.println("Podano zle id");
                checkID--;
            }
        }else System.out.println("brak magazynow do dodania permisji");
    }

    public  void rentWarehouse(Person person, Warehouse warehouse,LocalDate localDate) throws ToManyWarehousesException{

        showFree(warehouse);
        int check = 3;

        while (check > 0) {
            System.out.println("Podaj id magazynu ktory chcesz wynajac, pozostalo prob " + check);
            int chose = scan();

            try {
                if (warehouse.ifEgzist(chose)) {
                    if (person.getTenantAlerts().size() < 3) {
                        warehouse.rent(chose, person, localDate);
                    } else
                        throw new ProblematicTenantException("Osoba " + person + "posiada juz najem pomieszczen: " + person.getTenantMessege());
                    check = -1;

                    if (person.getTochangeFirstRent()) {
                        person.setFirstRent(localDate);
                    }
                }
            }catch (ProblematicTenantException problematicTenantException){
                System.out.println("Przekroczono ilosc nieoplaconych magazynow: \n");
                System.out.println(person.getTenantMessege());
                check=-1;
            }
            check--;
        }
        if(check==-1)
            System.out.println("Sproobuj ponowanie pozniej");

    }

    public void showMyWarehouses(Person person, Warehouse warehouse) throws NeverRentException {

        int number = 3;
        int index;
        if(person.getFirstRent()!=null) {
            warehouse.showWarehouseID(person);
            while (number > 0){
                System.out.println("WYbierz magazyn, pozostalo prob: "+number);
                index = scan();
                if (warehouse.checkIfOwner(person,index)){
                    warehouse.showWarehouse(index);
                    number = -1;
                }
                number--;
            }
        }
        else
            throw new NeverRentException("Nigdy nic nie wynajmowales");

    }

    public void putTire(Person logPerson, Warehouse warehouse) throws ToManyThingsException {
        System.out.println("Podaj rozmiar kola");
        int size = scan();
        warehouse.addTireToServiceWarehouse(new Item(size,"Opona"),logPerson);
    }

    public  void takeTire(Person logPerson, Warehouse warehouse){
        int howMany=3;
        try {
            logPerson.showMyTire();
            int id;
            while (howMany > 0) {

                System.out.println("Podaj id opony, pozostale proby: " + howMany);
                id = scan();

                if (logPerson.ifHasId(id)) {
                    warehouse.removeTire(id);
                    howMany = -1;
                }
                howMany--;
            }
            if (howMany == 0)
                System.out.println("Sprobuj ponownie pozniej");
        }catch (NeverTireException e){
            System.out.println("Brak opon do zabrania");
        }
    }

    public void extension(Person logPerson, Warehouse warehouse, LocalDate localDate) {
        int problems = 3;
        int id;

        if(!logPerson.getTenantAlerts().isEmpty()){

            System.out.println(logPerson.getTenantMessege()+"\n");
            while (problems>0) {
                System.out.println(" Podaj id, pozostalo prob: "+problems);
                id = scan();

                if(logPerson.checkTenant(id)!=null){
                    problems = -1;
                    if(ChronoUnit.DAYS.between(logPerson.checkTenant(id).getPlaceForRent().getEndRent(),localDate)<30) {
                        warehouse.extension(logPerson.checkTenant(id).getPlaceForRent(), localDate);
                    }
                    else
                        logPerson.deleteTenant(logPerson.checkTenant(id));
                }
                problems--;
            }
        }
        else
            System.out.println("Wszytsko oplacone");
    }

    public int scan(){
        try {
            int a = scanner.nextInt();
            return a;
        }
        catch (Exception e){
        }
        System.out.println("nie podano liczby");
        return -10;
    }

    public void showMenu(Person logPerson, LocalDate localDate, Service service) {


        System.out.println("--WITAMY--W---Serwisie:  "+service.getName()+"  zalogowany jako: " + logPerson.getName() + " " + logPerson.getSurname()+
                "\n********      Wybierz jaka czynnosc chcialbys wykonac      *********" +
                "\n**       1. Oznacza : przeloguj sie                               **" +
                "\n**       2. Oznacza : wyswietl wolne pomieszczenia                **" +
                "\n**       3. Oznacza : wyswietl swoje dane                         **" +
                "\n**       4. Oznacza : wynajmij pomieszczenie                      **"+
                "\n**       5. Oznacza : wyswietl zawartosc pomieszczenia            **"+
                "\n**       6. Oznacza : nadaj uprawnienia                           **"+
                "\n**       7. Oznacza : odbierz uprawnienia                         **"+
                "\n**       8. Oznacza : wlozenie przedmiotow                        **"+
                "\n**       9. Oznacza : wyjecie przedmiotow                         **"+
                "\n**      10. Oznacza : zaprakowanie samochodu                      **"+
                "\n**      11. Oznacza : odparkowanie samochodu                      **"+
                "\n**      12. Oznacza : zapis do pliku                              **"+
                "\n**      13. Oznacza : zgloszenie serwisowe                        **"+
                "\n**      14. Oznacza : dodanie opon                                **"+
                "\n**      15. Oznacza : zabranie opon                               **"+
                "\n**      16. Oznacza : przedluzenie najmu                          **"+
                "\n**      17. Oznacza : wyjscie z programu                          **"+
                "\n**    Obcena data to: " + localDate + "                      ");

    }


}
