package tqs.project.api.Services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.api.Models.Pedido;
import tqs.project.api.Repositories.PedidoRepository;
import tqs.project.api.Services.PedidoService;

import tqs.project.api.Others.STATUS;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public List<Pedido> getPendingPedidos() {
        return pedidoRepository.findAllByStatus(STATUS.PENDING.ordinal());
    }

    @Override
    public List<Pedido> getPreparingPedidos() {
        return pedidoRepository.findAllByStatus(STATUS.PREPARING.ordinal());
    }

}
