package tqs.project.api.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.api.Models.Pedido;
import tqs.project.api.Services.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Pedido>> getAllPendingPedidos(){
        List<Pedido> pedidos = pedidoService.getPendingPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<Pedido>> getAllOnGoingPedidos(){
        List<Pedido> pedidos = pedidoService.getPreparingPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }
}
