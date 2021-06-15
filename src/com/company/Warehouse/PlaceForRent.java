package com.company.Warehouse;

import com.company.Exception.ToManyThingsException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;

public abstract class PlaceForRent implements Comparable<PlaceForRent> {

    private static volatile int staticId = 0;
    private int id;
    private int vRoom;
    private int zajeteMiejscePrzezPrzedmioty=0;
    private int price;

    private LocalDate startRent;
    private LocalDate endRent;

    LinkedList<Item> itemlist = new LinkedList<>();



    PlaceForRent(int vRoom,int price){
        this.vRoom = vRoom;
        id=staticId++;
        this.price = price;
    }

    PlaceForRent(int x,int y,int z,int price){
        this.vRoom = x*y*z;
        id=staticId++;
        this.price = price;
    }

    public void setZajeteMiejscePrzezPrzedmioty(int zajeteMiejscePrzezPrzedmioty) {
        this.zajeteMiejscePrzezPrzedmioty = zajeteMiejscePrzezPrzedmioty;
    }

    public int getZajeteMiejscePrzezPrzedmioty() {
        return zajeteMiejscePrzezPrzedmioty;
    }

    @Override
    public int compareTo(PlaceForRent o) {
        return vRoom - o.vRoom;
    }

    public int getvPomieszczenia() {
        return vRoom;
    }

    public void getClear(){
        startRent = null;
        endRent = null;
        itemlist.clear();
        zajeteMiejscePrzezPrzedmioty=0;
    }

    public LocalDate getEndRent() {
        return endRent;
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setStartRent(LocalDate startRent) {
        this.startRent = startRent;
    }

    public void setEndRent(LocalDate endRent) {
        this.endRent = endRent;
    }

    public LocalDate getStartRent() {
        return startRent;
    }

    public abstract void setItem(Item item) throws ToManyThingsException;

    public String toItemList(){
        Collections.sort(itemlist);

        int counter=0;
        String ans ="[ ";

        for(Item item: itemlist){

            if(counter==4) {
                ans+="\n";
                counter=0;
            }
            counter++;
            ans += " { " + item.toString() + " } ";
        }
        if(ans.equals("[ "))
            return " [ brak przedmiotow ] \n";
        return ans+"]\n";
    }

    @Override
    public String toString() {
        return "Pomieszczenie o ID = " + id +
                ", powierzchnia wynosi = " + vRoom +
                ", zajeteMiejscePrzezPrzedmioty = " + (double)(zajeteMiejscePrzezPrzedmioty/vRoom) +
                ", cena najmu = " + price +
                "\nUmieszczone Przedmioty = " + toItemList();
    }

}
