package tqs.project.api.Services;

import java.util.List;

import tqs.project.api.Models.Pedido;

public interface PedidoService {
    public List<Pedido> getPendingPedidos();

    public List<Pedido> getPreparingPedidos();
}
