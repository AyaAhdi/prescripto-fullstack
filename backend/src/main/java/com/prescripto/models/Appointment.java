package com.prescripto.models;

public class Appointment {
    private int id;
    private int userId;
    private int docId;
    private String slotDate;
    private String slotTime;
    private int amount;
    private String bookingDate;
    private boolean cancelled;
    private boolean isCompleted;
    private boolean payment;
    private String paymentMethod;

    // Join data for frontend
    private User userData;
    private Doctor docData;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getDocId() { return docId; }
    public void setDocId(int docId) { this.docId = docId; }
    public String getSlotDate() { return slotDate; }
    public void setSlotDate(String slotDate) { this.slotDate = slotDate; }
    public String getSlotTime() { return slotTime; }
    public void setSlotTime(String slotTime) { this.slotTime = slotTime; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean isCompleted) { this.isCompleted = isCompleted; }
    public boolean isPayment() { return payment; }
    public void setPayment(boolean payment) { this.payment = payment; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public User getUserData() { return userData; }
    public void setUserData(User userData) { this.userData = userData; }
    public Doctor getDocData() { return docData; }
    public void setDocData(Doctor docData) { this.docData = docData; }
}
