package com.walking.tbooking.model;

public class Ticket {
    private Long id;
    private ServiceClass serviceClass;
    private int seatNumber;
    private String baggageAllowance;
    private Long flightId;
    private Long passengerId;


    public Ticket() {
    }

    public Ticket(Long id, ServiceClass serviceClass,
                  int seatNumber, String baggageAllowance,
                  Long flightId, Long passengerId) {
        this.id = id;
        this.serviceClass = serviceClass;
        this.seatNumber = seatNumber;
        this.baggageAllowance = baggageAllowance;
        this.flightId = flightId;
        this.passengerId = passengerId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ServiceClass getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClass serviceClass) {
        this.serviceClass = serviceClass;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getBaggageAllowance() {
        return baggageAllowance;
    }

    public void setBaggageAllowance(String baggageAllowance) {
        this.baggageAllowance = baggageAllowance;
    }

    public enum ServiceClass {
        ECONOMY, BUSINESS, FIRST
    }
}
