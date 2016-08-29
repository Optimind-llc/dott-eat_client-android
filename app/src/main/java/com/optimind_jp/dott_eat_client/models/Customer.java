package com.optimind_jp.dott_eat_client.models;

import java.io.Serializable;
import java.util.UUID;
import java.util.List;

/**
 * Created by hugh on 2016-08-22.
 * Initial implemented by Go on 2016-08-26.
 */

public class Customer implements Serializable {
    private UUID customerID;
    private String email;
    private String familyName, givenName, nickName,description;
    private String imageURL;
    private String telephone;
    private String appToken;
    private String webPayToken;
    private CustomerStatus status;
    private List<Order> currentOrders,historyOrders;
    private List<Transaction> historyActions;

    public Customer(String email, String nickName) {
        this.customerID = null;
        this.email = email;
        this.familyName = null;
        this.givenName = null;
        this.nickName = nickName;
        this.description = null;
        this.imageURL = null;
        this.telephone = null;
        this.appToken = null;
        this.webPayToken = null;
        this. status = CustomerStatus.NOT_CONFIRMED;
        this.currentOrders = null;
        this.historyOrders = null;
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