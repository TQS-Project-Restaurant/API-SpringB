package tqs.project.api.services;

import java.util.List;

import tqs.project.api.models.Pedido;

public interface PedidoService {
    public List<Pedido> getPendingPedidos();

    public List<Pedido> getPreparingPedidos();

    public List<Pedido> getPedidos();

    public Pedido updatePedido(Long id, Pedido pedido);
}
