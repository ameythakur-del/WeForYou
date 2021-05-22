package com.NGO.weforyou;

public class Hospital {
    private String name, adress, link, totalOxygen, availableOxygen, totalBeds, availableBeds, totalIcus, availableIcus, totalVentilators, availableVentilators, mobile, Type, Taluka, center;
    private long Time;

    public Hospital() {
    }

    public Hospital(String name, String adress, String link, String totalOxygen, String availableOxygen, String totalBeds, String availableBeds, String totalIcus, String availableIcus, String totalVentilators, String availableVentilators, String mobile, String Type, String Taluka, Long Time, String center) {
        this.name = name;
        this.adress = adress;
        this.link = link;
        this.totalOxygen = totalOxygen;
        this.availableOxygen = availableOxygen;
        this.totalBeds = totalBeds;
        this.availableBeds = availableBeds;
        this.totalIcus = totalIcus;
        this.availableIcus = availableIcus;
        this.totalVentilators = totalVentilators;
        this.availableVentilators = availableVentilators;
        this.mobile = mobile;
        this.Type = Type;
        this.Taluka = Taluka;
        this.Time = Time;
        this.center = center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTotalOxygen() {
        return totalOxygen;
    }

    public void setTotalOxygen(String totalOxygen) {
        this.totalOxygen = totalOxygen;
    }

    public String getAvailableOxygen() {
        return availableOxygen;
    }

    public void setAvailableOxygen(String availableOxygen) {
        this.availableOxygen = availableOxygen;
    }

    public String getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(String totalBeds) {
        this.totalBeds = totalBeds;
    }

    public String getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(String availableBeds) {
        this.availableBeds = availableBeds;
    }

    public String getTotalIcus() {
        return totalIcus;
    }

    public void setTotalIcus(String totalIcus) {
        this.totalIcus = totalIcus;
    }

    public String getAvailableIcus() {
        return availableIcus;
    }

    public void setAvailableIcus(String availableIcus) {
        this.availableIcus = availableIcus;
    }

    public String getTotalVentilators() {
        return totalVentilators;
    }

    public void setTotalVentilators(String totalVentilators) {
        this.totalVentilators = totalVentilators;
    }

    public String getAvailableVentilators() {
        return availableVentilators;
    }

    public void setAvailableVentilators(String availableVentilators) {
        this.availableVentilators = availableVentilators;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        Type = Type;
    }

    public String getTaluka() {
        return Taluka;
    }

    public void setTaluka(String Taluka) {
        Taluka = Taluka;
    }

    public Long getTime() {
        return Time;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }
}
