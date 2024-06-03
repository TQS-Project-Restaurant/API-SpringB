package tqs.project.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import tqs.project.api.dao.PedidoRequest;
import tqs.project.api.models.Pedido;
import tqs.project.api.others.STATUS;
import tqs.project.api.services.PedidoService;

@RestController
@Tag(name = "Request API")
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }


    @Operation(summary = "Get list of all requests", description = "Returns all requests list object")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos(){
        List<Pedido> pedidos = pedidoService.getPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }


    @Operation(summary = "Get list of all pending requests", description = "Returns all pending requests list object")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping("/pending")
    public ResponseEntity<List<Pedido>> getAllPendingPedidos(){
        List<Pedido> pedidos = pedidoService.getPendingPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }


    @Operation(summary = "Get list of all preparing requests", description = "Returns all preparing requests list object")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping("/preparing")
    public ResponseEntity<List<Pedido>> getAllPreparingPedidos(){
        List<Pedido> pedidos = pedidoService.getPreparingPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }


    @Operation(summary = "Update a given request", description = "Updates the details of given request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid status value received"),
        @ApiResponse(responseCode = "404", description = "Given request was not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Long id, @RequestBody Pedido pedido){
        int status = pedido.getStatus();
        if (status > STATUS.CANCELLED.ordinal() || status < STATUS.PENDING.ordinal()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Pedido updatedPedido = pedidoService.updatePedido(id, pedido);

        if(updatedPedido == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
    }


    @Operation(summary = "Create requests", description = "Creates requests based on a list provided by the waiter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid status value received"),
        @ApiResponse(responseCode = "404", description = "Given request was not found")
    })
    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody PedidoRequest pedidoRequest){
        Pedido pedido = pedidoService.createPedido(pedidoRequest);

        if (pedido == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }
}
