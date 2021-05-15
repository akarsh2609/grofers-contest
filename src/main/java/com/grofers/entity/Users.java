package com.grofers.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Entity for User table
 */
@Entity
@Table(name = "users")
public class Users implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile")
    private BigInteger mobileNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(BigInteger mobileNo) {
        this.mobileNo = mobileNo;
    }

}
