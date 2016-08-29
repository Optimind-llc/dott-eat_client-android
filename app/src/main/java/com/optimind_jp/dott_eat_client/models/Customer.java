package com.optimind_jp.dott_eat_client.models;

import android.net.Uri;

import java.io.Serializable;
import java.net.URL;
import java.util.UUID;
import java.util.List;

/**
 * Created by hugh on 2016-08-22.
 * Initial implemented by Go on 2016-08-26.
 */

public class Customer implements Serializable {
    private UUID customerID;
    private String email;
    private String familyName, givenName, nickName, description;
    private Uri photoUrl;
    private String telephone;
    private String appToken;
    private String webPayToken;
    private CustomerStatus status;
    private List<Order> currentOrders,historyOrders;
    private List<Transaction> historyActions;

    public Customer(String email, String nickName, String familyName, String givenName) {
        this.customerID = null;
        this.email = email;
        this.familyName = familyName;
        this.givenName = givenName;
        this.nickName = nickName;
        this.description = null;
        this.photoUrl = null;
        this.telephone = null;
        this.appToken = null;
        this.webPayToken = null;
        this. status = CustomerStatus.NOT_CONFIRMED;
        this.currentOrders = null;
        this.historyOrders = null;
    }

    public void copy(Customer newCustomer) {
        this.customerID = newCustomer.customerID;
        this.email = newCustomer.email;
        this.familyName = newCustomer.familyName;
        this.givenName = newCustomer.givenName;
        this.nickName = newCustomer.nickName;
        this.description = newCustomer.description;
        this.photoUrl = newCustomer.photoUrl;
        this.telephone = newCustomer.telephone;
        this.appToken = newCustomer.appToken;
        this.webPayToken = newCustomer.webPayToken;
        this. status = newCustomer.status;
        this.currentOrders = newCustomer.currentOrders;
        this.historyOrders = newCustomer.historyOrders;
        return;
    }
    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
        return;
    }
    public void setTelephone(){
        this.telephone = telephone;
        return;
    }

    public CustomerStatus getStatus(){
        return this.status;
    }
    // generate webpay token by credit card information
    // return: [true] : success and save the token in "webPayToken",
    //         [false]: fail.
    public boolean generateWebpayToken( String number, String name, String cvc, int expMonth, int expYear){
        return true;
    }
}