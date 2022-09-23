package com.tinderbusiness.models;

public class UserModel {

    String id = "";
    String contactName = "";
    String email = "";
    String password = "";
    String country = "";
    String continent = "";
    String industry = "";
    String typesCollaborations = "";
    String interestedIndustry = "";
    String budget = "";
    String description = "";
    int bgColor = 0;
    int txtColor = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getTypesCollaborations() {
        return typesCollaborations;
    }

    public void setTypesCollaborations(String typesCollaborations) {
        this.typesCollaborations = typesCollaborations;
    }

    public String getInterestedIndustry() {
        return interestedIndustry;
    }

    public void setInterestedIndustry(String interestedIndustry) {
        this.interestedIndustry = interestedIndustry;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getTxtColor() {
        return txtColor;
    }

    public void setTxtColor(int txtColor) {
        this.txtColor = txtColor;
    }
}
