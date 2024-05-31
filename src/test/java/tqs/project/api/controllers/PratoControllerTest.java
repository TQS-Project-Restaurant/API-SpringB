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
import tqs.project.api.models.Prato;
import tqs.project.api.services.PratoService;

@WebMvcTest(PratoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PratoControllerTest {
    
    @Autowired
    MockMvc mvc;

    @MockBean
    PratoService service;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    Prato prato = new Prato();

    @BeforeEach
    void setUp(){
        prato.setId(1L);
        prato.setNome("Francesinha");
    }

    @Test
    void givenPrato_whenGetPratoById_thenReturnPrato(){
        when(service.getPrato(1L)).thenReturn(prato);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/dishes/1")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("nome", equalTo("Francesinha"));

        verify(service, times(1)).getPrato(1L);
    }

    @Test 
    void givenPrato_whenGetPratoByWrongId_thenReturn404(){
        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
        .when()
            .get("/api/dishes/1")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);

        verify(service, times(1)).getPrato(Mockito.any());
    }
}
