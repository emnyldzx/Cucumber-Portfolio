package com.hotelrez.services;

import com.hotelrez.models.Auth;
import com.hotelrez.models.Booking;
import com.hotelrez.models.BookingDates;
import com.hotelrez.models.BookingResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class ReservationService extends BaseTest {
    //token pass
    //rez oluştur
    //silme metodu

    public String generateToken(){
        Auth authBody = new Auth("admin","password123");

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(authBody)
                .post("/auth");

        response
                .then()
                .statusCode(200);
        return response.jsonPath().getJsonObject("token");
    }
    public BookingResponse createBooking(){
        BookingDates bookingdates = new BookingDates("2024-03-02","2024-09-01");
        Booking booking = new Booking("Emin","Yildiz", 23000, false,bookingdates, "VIP");

        Response response = given(spec)
                .contentType(ContentType.JSON)
                .when()
                .body(booking)
                .post("/booking");
        response    .then()
                .statusCode(200);
        return response.as(BookingResponse.class);
    }

    public void deleteRez(String token, int bookingid){
        Response response = given(spec)
                .header("Cookice", "token="+token)
                .when()
                .delete("/booking"+bookingid);
        response
                .then()
                .statusCode(201);
    }

}
