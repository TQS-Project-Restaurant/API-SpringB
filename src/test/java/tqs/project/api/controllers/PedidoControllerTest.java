package tqs.project.api.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;

import tqs.project.api.services.PedidoService;
import tqs.project.api.models.Pedido;
import tqs.project.api.others.STATUS;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {
    
    @Autowired
    MockMvc mvc;

    @MockBean
    private PedidoService service;

    Pedido pedido = new Pedido();
    Pedido pedido2 = new Pedido();
    Pedido pedido3 = new Pedido();

    @BeforeEach
    void setUp(){
        pedido.setMesa(0);
        pedido.setStatus(STATUS.PENDING.ordinal());

        pedido2.setMesa(1);
        pedido2.setStatus(STATUS.PENDING.ordinal());

        pedido3.setMesa(2);
        pedido3.setStatus(STATUS.PREPARING.ordinal());
    }


    @Test
    void givenManyPendingPedidos_whenGetPendingPedidos_thenReturnJsonArray(){
        List<Pedido> pedidos = Arrays.asList(pedido, pedido2);

        when(service.getPendingPedidos()).thenReturn(pedidos);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/requests/pending")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", is(2));

        verify(service, times(1)).getPendingPedidos();
    }

    @Test
    void givenManyPreparingPedidos_whenGetPreparingPedidos_thenReturnJsonArray(){
        List<Pedido> pedidos = Arrays.asList(pedido3);

        when(service.getPreparingPedidos()).thenReturn(pedidos);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/requests/preparing")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", is(1));

        verify(service, times(1)).getPreparingPedidos();
    }

    @Test
    void givenManyPedidos_whenGetPedidos_thenReturnJsonArray(){
        List<Pedido> pedidos = Arrays.asList(pedido, pedido2, pedido3);

        when(service.getPedidos()).thenReturn(pedidos);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
            .when()
                .get("/api/requests")
            .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", is(3));

        verify(service, times(1)).getPedidos();        
    }

    @Test
    void givenPedido_whenUpdatePedido_thenReturnPedido(){
        Pedido newPedido = new Pedido();
        newPedido.setMesa(pedido.getMesa());
        newPedido.setStatus(STATUS.PREPARING.ordinal());

        when(service.updatePedido(Mockito.any(), Mockito.any())).thenReturn(newPedido);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
            .body(pedido)
        .when()
            .put("/api/requests/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .assertThat()
            .body("status", is(STATUS.PREPARING.ordinal()));

        verify(service, times(1)).updatePedido(Mockito.any(), Mockito.any());  
    }

    @Test
    void givenPedidoWithWrongStatus_whenUpdatePedido_thenReturn400(){
        Pedido newPedido = new Pedido();
        newPedido.setMesa(pedido.getMesa());
        newPedido.setStatus(10);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
            .body(newPedido)
        .when()
            .put("/api/requests/1")
        .then()
            .statusCode(HttpStatus.SC_BAD_REQUEST);


        newPedido.setStatus(-1);

        RestAssuredMockMvc
            .given()
                .mockMvc(mvc)
                .contentType(ContentType.JSON)
                .body(newPedido)
            .when()
                .put("/api/requests/1")
            .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void givenPedidoWithWrongID_whenUpdatePedido_thenReturn404(){
        Pedido newPedido = new Pedido();
        newPedido.setMesa(pedido.getMesa());
        newPedido.setStatus(STATUS.COMPLETED.ordinal());

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .contentType(ContentType.JSON)
            .body(newPedido)
        .when()
            .put("/api/requests/1")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
