package tqs.project.api.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import tqs.project.api.configuration.JwtAuthenticationFilter;
import tqs.project.api.dao.ReservaRequest;
import tqs.project.api.models.Reserva;
import tqs.project.api.models.Utilizador;
import tqs.project.api.others.ROLES;
import tqs.project.api.others.Restaurant;
import tqs.project.api.others.STATUS;
import tqs.project.api.services.ReservaService;
import tqs.project.api.services.CustomUserDetailsService;

@WebMvcTest(ReservaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReservaControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ReservaService service;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    CustomUserDetailsService userService;

    @MockBean
    SecurityContext securityContext;

    @MockBean
    Authentication authentication;

    LocalDate day = LocalDate.parse("2024-01-01");
    Restaurant restaurant = new Restaurant();
    Reserva reserva = new Reserva();
    Utilizador utilizador = new Utilizador();

    @BeforeEach
    void setUp(){
        reserva.setQuantidadeMesas(1);
        reserva.setDia(LocalDate.now());
        reserva.setHora(LocalTime.now());
        reserva.setStatus(STATUS.PENDING.ordinal());

        utilizador.setEmail("user@gmail.com");
        utilizador.setPassword("password");
        utilizador.setRole(ROLES.USER);

        reserva.setUtilizador(utilizador);

        List<Reserva> bookings = Arrays.asList(reserva);

        when(service.createBooking(Mockito.any(), Mockito.any())).thenReturn(reserva);
        when(service.getAvailableSlots(day)).thenReturn(restaurant.getDailySlots());
        when(service.getUserBookings("user@gmail.com")).thenReturn(bookings);

        // it feels like there's a better way of doing this, using a custom UserDetails or a mockuser interface
        // however this seems to work for what we want so...
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("user@gmail.com");
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

    @Test
    @WithMockUser(username = "user@gmail.com")
    void whenCreateBooking_thenReturnCreatedBooking() {
        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setQuantidadeMesas(1);
        reservaRequest.setDia(LocalDate.now());
        reservaRequest.setHora(LocalTime.now());

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
                .body(reservaRequest)
            .when()
                .post("/api/bookings")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("utilizador.email", is("user@gmail.com"))
                .body("status", is(STATUS.PENDING.ordinal()));

        verify(service, times(1)).createBooking(Mockito.any(), Mockito.any());
    }

    @Test
    void whenCreateBookingWithoutAuthentication_thenReturnBadRequest() {
        when(securityContext.getAuthentication()).thenReturn(null);
        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setQuantidadeMesas(1);
        reservaRequest.setDia(LocalDate.now());
        reservaRequest.setHora(LocalTime.now());

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
                .body(reservaRequest)
            .when()
                .post("/api/bookings")
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void whenCreateInvalidBooking_thenReturnBadRequest() {
        ReservaRequest reservaRequest = new ReservaRequest();
        reservaRequest.setQuantidadeMesas(12);
        reservaRequest.setDia(LocalDate.now());
        reservaRequest.setHora(LocalTime.now());

        when(service.createBooking(Mockito.any(), Mockito.any())).thenReturn(null);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
            .body(reservaRequest)
        .when()
            .post("/api/bookings")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);

        verify(service, times(1)).createBooking(Mockito.any(), Mockito.any());
    }

    @Test
    @WithMockUser(username = "user@gmail.com")
    void whenGetAuthenticatedUserBookings_thenReturnAuthenticatedUserBookings(){
        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
        .when()
            .get("/api/bookings")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
            .body("utilizador.email[0]", is("user@gmail.com"));

        verify(service, times(1)).getUserBookings(Mockito.any());
    }

    @Test
    void whenGetUserBookingsWithoutAuthentication_thenReturnBadRequest(){
        when(securityContext.getAuthentication()).thenReturn(null);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
        .when()
            .get("/api/bookings")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
