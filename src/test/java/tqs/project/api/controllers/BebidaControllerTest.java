package tqs.project.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import tqs.project.api.models.Bebida;
import tqs.project.api.services.BebidaService;

@WebMvcTest(BebidaController.class)
@AutoConfigureMockMvc(addFilters = false)
class BebidaControllerTest {
    
    @Autowired
    MockMvc mvc;

    @MockBean
    BebidaService service;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    Bebida beverage = new Bebida();

    @BeforeEach
    void setUp(){
        beverage.setId(1L);
        beverage.setNome("Fanta");
    }

    @Test
    void givenBebida_whenGetBebidaById_thenReturnBebida(){
        when(service.getBebida(1L)).thenReturn(beverage);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/beverages/1")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("nome", equalTo("Fanta"));

        verify(service, times(1)).getBebida(1L);
    }

    @Test 
    void givenBebida_whenGetBebidaByWrongId_thenReturn404(){
        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
        .when()
            .get("/api/beverages/1")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);

        verify(service, times(1)).getBebida(Mockito.any());
    }
}
