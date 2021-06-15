package com.company.Person;

import com.company.Warehouse.ParkingSpace;
import com.company.Warehouse.PlaceForRent;

public class TenantAlert {

    private static int index=0;
    private int id;
    private PlaceForRent placeForRent = null;
    private ParkingSpace parkingSpace = null;
    private String description;
    private int liabilities;

    public TenantAlert(String name,PlaceForRent placeForRent,int liabilities){
        this.description = name;
        this.placeForRent = placeForRent;
        this.liabilities = liabilities;
    }

    public TenantAlert(String name, ParkingSpace parkingSpace, int liabilities){
        this.description = name;
        this.parkingSpace = parkingSpace;
        this.liabilities = liabilities;
    }

    public int getId() {
        return id;
    }

    public PlaceForRent getPlaceForRent() {
        return placeForRent;
    }

    public String getDescription() {
        return description;
    }

    public int getLiabilities() {
        return liabilities;
    }
}
