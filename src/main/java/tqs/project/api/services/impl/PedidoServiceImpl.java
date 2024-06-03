package tqs.project.api.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.api.dao.PedidoItem;
import tqs.project.api.dao.PedidoRequest;
import tqs.project.api.models.Bebida;
import tqs.project.api.models.Pedido;
import tqs.project.api.models.Prato;
import tqs.project.api.repositories.BebidaRepository;
import tqs.project.api.repositories.PedidoRepository;
import tqs.project.api.repositories.PratoRepository;
import tqs.project.api.services.PedidoService;

import tqs.project.api.others.STATUS;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final BebidaRepository bebidaRepository;
    private final PratoRepository pratoRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, BebidaRepository bebidaRepository, PratoRepository pratoRepository){
        this.pedidoRepository = pedidoRepository;
        this.bebidaRepository = bebidaRepository;
        this.pratoRepository = pratoRepository;
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

    @Override
    public Pedido createPedido(PedidoRequest pedidoRequest) {
        int itemNumber = pedidoRequest.getPratos().size();
        Pedido pedido = new Pedido();

        List<Bebida> bebidas = new ArrayList<>();
        List<Prato> pratos = new ArrayList<>();

        for(int i = 0; i < itemNumber; i++){
            PedidoItem bebidaItem = pedidoRequest.getBebidas().get(i);
            PedidoItem pratoItem = pedidoRequest.getPratos().get(i);

            Bebida bebida = bebidaRepository.findById(bebidaItem.getId()).get();
            
            if(bebidaItem.getQuantidade() > bebida.getStock()){
                return null;
            }

            Prato prato = new Prato();

            bebidas.add(bebida);
            pratos.add(prato);
        }

        return pedido;
    }

}
