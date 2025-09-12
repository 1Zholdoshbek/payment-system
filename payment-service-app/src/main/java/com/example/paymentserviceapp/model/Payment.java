package com.example.paymentserviceapp.model;

public class Payment {

    private long id;
    private double value;

    public Payment() {

    }
    public Payment(long id, double value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}

