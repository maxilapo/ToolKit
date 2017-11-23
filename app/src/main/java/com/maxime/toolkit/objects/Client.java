package com.maxime.toolkit.objects;

/**
 * Created by Maxime on 2017-10-04.
 */

public class Client {

    int    id;
    String name;
    String email;
    String phone;

    String street;
    String street2;
    String city;
    String zip;
    String province;


    /*********************************** Constructor ************************************/

    public Client(int _id, String _name, String _email, String _phone) {
        this.id = _id;
        this.name = _name;
        this.email = _email;
        this.phone = _phone;
    }

    public Client(int _id, String _name, String _email, String _phone, String _street, String _street2, String _city, String _zip, String _province) {
        this.id = _id;
        this.name = _name;
        this.email = _email;
        this.phone = _phone;

        this.street = _street;
        this.street2 = _street2;
        this.city = _city;
        this.zip = _zip;
        this.province = _province;
    }

    /*************************************** GETTER *******************************************/

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getStreet() {
        return street;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getProvince() {
        return province;
    }

    /*************************************** SETTER *******************************************/

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
