package com.NGO.weforyou;

public class urbanmodel {
    private String Name, area,phoneNumber,Specialization,latitude,longitude;

    public urbanmodel() {
    }

    public urbanmodel(String Name, String area, String phoneNumber, String Specialization,String latitude,String longitude) {
        this.Name = Name;
        this.area = area;
        this.phoneNumber = phoneNumber;
        this.Specialization = Specialization;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String Specialization) {
        this.Specialization = Specialization;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
