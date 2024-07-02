package com.hotelrez.steps;

import com.hotelrez.models.BookingResponse;
import com.hotelrez.services.ReservationService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReservationSteps {
    ReservationService rezServices = new ReservationService();
    String authKey;
    BookingResponse bookingresponse;

    @Given("Kullanici yeni bir rezervasyon olusturuyor")
    public void cagriBaslangici(){
        rezServices = new ReservationService();
    }

    @Given("Kullanici rezervasyon icin gereken bilgileri veriyor")
    public void createAuth(){
        authKey = rezServices.generateToken();
    }

    @When("Kullanici otel rezervasyonu yaratiyor")
    public void createRez(){
        bookingresponse = rezServices.createBooking();
    }

    @Then("Rezervasyon basarili sekilde olusturuldu")
    public void rezAssertions(){
        Assertions.assertEquals("Emin",bookingresponse.getBooking().getFirstname());
        Assertions.assertEquals("Yildiz",bookingresponse.getBooking().getLastname());
        Assertions.assertEquals(23000,bookingresponse.getBooking().getTotalprice());
        Assertions.assertFalse(bookingresponse.getBooking().isDepositpaid());
        Assertions.assertEquals("VIP",bookingresponse.getBooking().getAdditionalneeds());
    }

    @Then("Kullanici rezervasyonu iptal ediyor")
    public void cancelRez(){
        rezServices.deleteRez(authKey,bookingresponse.getBookingid());
    }

}
