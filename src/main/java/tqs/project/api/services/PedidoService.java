package tqs.project.api.services;

import java.util.List;

import tqs.project.api.dao.PedidoRequest;
import tqs.project.api.models.Pedido;

public interface PedidoService {
    List<Pedido> getPendingPedidos();

    List<Pedido> getPreparingPedidos();

    List<Pedido> getPedidos();

    Pedido updatePedido(Long id, Pedido pedido);

    Pedido createPedido(PedidoRequest pedidoRequest);
}
