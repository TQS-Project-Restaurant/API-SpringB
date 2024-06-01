package tqs.project.api.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import tqs.project.api.configuration.JwtAuthenticationFilter;
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
    private ReservaService service;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    CustomUserDetailsService userService;

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

        when(service.createBooking(Mockito.any())).thenReturn(reserva);
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

    @Test
    void whenCreateBooking_thenReturnCreatedBooking() {
        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
                .body(reserva)
            .when()
                .post("/api/bookings")
            .then()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("utilizador.email", is("user@gmail.com"))
                .body("status", is(STATUS.PENDING.ordinal()));

        verify(service, times(1)).createBooking(Mockito.any());
    }

    @Test
    void whenCreateInvalidBooking_thenReturnBadRequest() {
        Reserva innerReserva = new Reserva();
        innerReserva.setHora(LocalTime.now());
        innerReserva.setDia(LocalDate.now());
        innerReserva.setQuantidadeMesas(12);
        innerReserva.setStatus(STATUS.PENDING.ordinal());
        innerReserva.setUtilizador(utilizador);

        when(service.createBooking(Mockito.any())).thenReturn(null);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
            .body(innerReserva)
        .when()
            .post("/api/bookings")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);

        verify(service, times(1)).createBooking(Mockito.any());
    }
}
