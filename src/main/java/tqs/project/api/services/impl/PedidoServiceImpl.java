package tqs.project.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.api.models.Pedido;
import tqs.project.api.repositories.PedidoRepository;
import tqs.project.api.services.PedidoService;

import tqs.project.api.others.STATUS;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<Pedido> getPendingPedidos() {
        return pedidoRepository.findAllByStatusOrderByLastModifiedAsc(STATUS.PENDING.ordinal());
    }

    @Override
    public List<Pedido> getPreparingPedidos() {
        return pedidoRepository.findAllByStatusOrderByLastModifiedAsc(STATUS.PREPARING.ordinal());
    }

    @Override
    public List<Pedido> getPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido updatePedido(Long id, Pedido pedido) {
        Pedido pedidoToUpdate = pedidoRepository.findById(id).orElse(null);

        if(pedidoToUpdate == null){
            return null;
        }

        pedidoToUpdate.setBebidas(pedido.getBebidas());
        pedidoToUpdate.setPratos(pedido.getPratos());
        pedidoToUpdate.setMesa(pedido.getMesa());
        pedidoToUpdate.setStatus(pedido.getStatus());
        pedidoToUpdate.setLastModified(System.currentTimeMillis() % 1000);

        return pedidoRepository.save(pedidoToUpdate);
    }

}
