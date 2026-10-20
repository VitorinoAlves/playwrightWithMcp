package com.example.bdd.api.model;

import com.microsoft.playwright.options.FormData;

public class CreateAccountRequest {
    private final String name;
    private final String email;
    private final String password;
    private final String title;
    private final String birthDate;
    private final String birthMonth;
    private final String birthYear;
    private final String firstName;
    private final String lastName;
    private final String company;
    private final String address1;
    private final String address2;
    private final String country;
    private final String zipcode;
    private final String state;
    private final String city;
    private final String mobileNumber;

    private CreateAccountRequest(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.title = builder.title;
        this.birthDate = builder.birthDate;
        this.birthMonth = builder.birthMonth;
        this.birthYear = builder.birthYear;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.company = builder.company;
        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.country = builder.country;
        this.zipcode = builder.zipcode;
        this.state = builder.state;
        this.city = builder.city;
        this.mobileNumber = builder.mobileNumber;
    }

    public FormData toFormData() {
        return FormData.create()
                .set("name", name != null ? name : "")
                .set("email", email != null ? email : "")
                .set("password", password != null ? password : "")
                .set("title", title != null ? title : "")
                .set("birth_date", birthDate != null ? birthDate : "")
                .set("birth_month", birthMonth != null ? birthMonth : "")
                .set("birth_year", birthYear != null ? birthYear : "")
                .set("firstname", firstName != null ? firstName : "")
                .set("lastname", lastName != null ? lastName : "")
                .set("company", company != null ? company : "")
                .set("address1", address1 != null ? address1 : "")
                .set("address2", address2 != null ? address2 : "")
                .set("country", country != null ? country : "")
                .set("zipcode", zipcode != null ? zipcode : "")
                .set("state", state != null ? state : "")
                .set("city", city != null ? city : "")
                .set("mobile_number", mobileNumber != null ? mobileNumber : "");
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCountry() {
        return country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private String password;
        private String title;
        private String birthDate;
        private String birthMonth;
        private String birthYear;
        private String firstName;
        private String lastName;
        private String company;
        private String address1;
        private String address2;
        private String country;
        private String zipcode;
        private String state;
        private String city;
        private String mobileNumber;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder birthMonth(String birthMonth) {
            this.birthMonth = birthMonth;
            return this;
        }

        public Builder birthYear(String birthYear) {
            this.birthYear = birthYear;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder company(String company) {
            this.company = company;
            return this;
        }

        public Builder address1(String address1) {
            this.address1 = address1;
            return this;
        }

        public Builder address2(String address2) {
            this.address2 = address2;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder zipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public CreateAccountRequest build() {
            return new CreateAccountRequest(this);
        }
    }
}
