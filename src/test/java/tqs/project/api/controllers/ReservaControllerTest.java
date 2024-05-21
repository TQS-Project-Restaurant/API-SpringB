package tqs.project.api.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.project.api.others.Restaurant;
import tqs.project.api.services.ReservaService;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private ReservaService service;

    LocalDate day = LocalDate.parse("2024-01-01");
    Restaurant restaurant = new Restaurant();

    @BeforeEach
    void setUp(){
        when(service.getAvailableSlots(day)).thenReturn(restaurant.getDailySlots());
    }

    @Test
    void givenDate_whenGetAvailableSlots_thenReturnSlots(){
        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/bookings/availableSlots?date=" + day)
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", is(restaurant.getDailySlots().size()));
        
        verify(service, times(1)).getAvailableSlots(day);
    }
}
