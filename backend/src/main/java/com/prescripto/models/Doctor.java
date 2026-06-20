package com.prescripto.models;

public class Doctor {
    private int id;
    private String name;
    private String email;
    private String password;
    private String image;
    private String speciality;
    private String degree;
    private String experience;
    private String about;
    private int fees;
    private String addressLine1;
    private String addressLine2;
    private boolean available;
    private String slots_booked; // JSON string

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }
    public int getFees() { return fees; }
    public void setFees(int fees) { this.fees = fees; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public String getSlots_booked() { return slots_booked; }
    public void setSlots_booked(String slots_booked) { this.slots_booked = slots_booked; }
}
