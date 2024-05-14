package tqs.project.api.controllers;

import org.junit.jupiter.api.Test;

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

    @Test
    void givenManyPendingPedidos_whenGetPendingPedidos_thenReturnJsonArray(){
        Pedido pedido1 = new Pedido();
        pedido1.setMesa(0);
        pedido1.setStatus(STATUS.PENDING.ordinal());

        Pedido pedido2 = new Pedido();
        pedido2.setMesa(1);
        pedido2.setStatus(STATUS.PENDING.ordinal());

        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);

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
    void getAllPreparingPedidos(){
        Pedido pedido1 = new Pedido();
        pedido1.setMesa(0);
        pedido1.setStatus(STATUS.PREPARING.ordinal());

        List<Pedido> pedidos = Arrays.asList(pedido1);

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
}
