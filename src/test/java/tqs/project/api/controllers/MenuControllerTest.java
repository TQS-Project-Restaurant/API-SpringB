package tqs.project.api.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import tqs.project.api.models.Menu;
import tqs.project.api.services.MenuService;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private MenuService service;

    Menu menu = new Menu();
    
    @BeforeEach
    void setUp(){
        menu.setDia(LocalDate.now());
    }

    @Test
    void givenMenu_whenGetDailyMenu_thenReturnTodaysMenu(){
        when(service.getDailyMenu()).thenReturn(menu);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/menu")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("dia", is(LocalDate.now().toString()));

        verify(service, times(1)).getDailyMenu();
    }

    @Test
    void givenNoMenu_whenGetDailyMenu_thenReturn404(){
        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
        .when()
            .get("/api/menu")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);

        verify(service, times(1)).getDailyMenu();
    }
}
