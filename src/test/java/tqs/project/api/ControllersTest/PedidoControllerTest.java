package tqs.project.api.ControllersTest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.project.api.Controllers.PedidoController;

import static org.hamcrest.CoreMatchers.is;

import org.apache.http.HttpStatus;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {
    
    @Autowired
    MockMvc mvc;

    @Test
    void getAllPendingPedidos(){
        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/pedidos/pending")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", is(0));
    }

    @Test
    void getAllOnGoingPedidos(){
        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/pedidos/ongoing")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", is(0));
    }
}
