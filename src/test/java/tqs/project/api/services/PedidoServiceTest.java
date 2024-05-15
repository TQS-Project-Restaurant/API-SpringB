package tqs.project.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.api.repositories.PedidoRepository;
import tqs.project.api.services.impl.PedidoServiceImpl;
import tqs.project.api.models.Pedido;
import tqs.project.api.others.STATUS;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @Mock(strictness = Mock.Strictness.LENIENT )
    private PedidoRepository repository;

    @InjectMocks
    private PedidoServiceImpl service;

    @BeforeEach
    void setUp(){
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setMesa(1);
        pedido.setStatus(STATUS.PENDING.ordinal());

        Pedido pedido2 = new Pedido();
        pedido2.setMesa(2);
        pedido2.setStatus(STATUS.PREPARING.ordinal());

        List<Pedido> allPending = Arrays.asList(pedido);
        List<Pedido> allPreparing = Arrays.asList(pedido2);
        List<Pedido> allPedidos = Arrays.asList(pedido, pedido2);

        when(repository.findAllByStatus(STATUS.PENDING.ordinal())).thenReturn(allPending);
        when(repository.findAllByStatus(STATUS.PREPARING.ordinal())).thenReturn(allPreparing);
        when(repository.findAll()).thenReturn(allPedidos);
        when(repository.findById(1L)).thenReturn(Optional.of(pedido));
    }

    @Test
    void whenGetPendingPedidos_returnPendingPedidos(){
        List<Pedido> pedidos = service.getPendingPedidos();

        assertThat(pedidos).hasSize(1);
        verify(repository, times(1)).findAllByStatus(STATUS.PENDING.ordinal());
    }

    @Test
    void whenGetPreparingPedidos_returnPreparingPedidos(){
        List<Pedido> pedidos = service.getPreparingPedidos();

        assertThat(pedidos).hasSize(1);
        verify(repository, times(1)).findAllByStatus(STATUS.PREPARING.ordinal());
    }

    @Test
    void whenGetAllPedidos_returnAllPedidos(){
        List<Pedido> pedidos = service.getPedidos();

        assertThat(pedidos).hasSize(2);
        verify(repository, times(1)).findAll();
    }

    @Test
    void whenUpdatePedido_thenReturnPedidoUpdated(){
        Pedido pedidoCompleted = new Pedido();
        pedidoCompleted.setMesa(1);
        pedidoCompleted.setStatus(STATUS.COMPLETED.ordinal());

        when(repository.save(Mockito.any())).thenReturn(pedidoCompleted);
        Pedido pedidoUpdated = service.updatePedido(1L, pedidoCompleted);

        assertThat(pedidoUpdated.getStatus()).isEqualTo(STATUS.COMPLETED.ordinal());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void whenUpdateNonExistentPedido_thenReturnNull(){
        Pedido pedidoCompleted = new Pedido();
        pedidoCompleted.setMesa(1);
        pedidoCompleted.setStatus(STATUS.COMPLETED.ordinal());

        Pedido pedidoUpdated = service.updatePedido(2L, pedidoCompleted);

        assertThat(pedidoUpdated).isEqualTo(null);
        verify(repository, times(1)).findById(2L);
    }
}
