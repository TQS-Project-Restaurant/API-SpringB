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

    Pedido pedidoPending = new Pedido();
    Pedido pedidoPending2 = new Pedido();
    Pedido pedidoPreparing = new Pedido();


    @BeforeEach
    void setUp(){
        pedidoPending.setId(1L);
        pedidoPending.setMesa(1);
        pedidoPending.setStatus(STATUS.PENDING.ordinal());
        pedidoPending.setLastModified(System.currentTimeMillis() % 1000);

        pedidoPending2.setId(2L);
        pedidoPending2.setMesa(1);
        pedidoPending2.setStatus(STATUS.PENDING.ordinal());
        pedidoPending2.setLastModified(System.currentTimeMillis() % 1000);

        pedidoPreparing.setMesa(2);
        pedidoPreparing.setStatus(STATUS.PREPARING.ordinal());
        pedidoPreparing.setLastModified(System.currentTimeMillis() % 1000);

        List<Pedido> allPending = Arrays.asList(pedidoPending, pedidoPending2);
        List<Pedido> allPreparing = Arrays.asList(pedidoPreparing);
        List<Pedido> allPedidos = Arrays.asList(pedidoPending, pedidoPending2, pedidoPreparing);

        when(repository.findAllByStatusOrderByLastModifiedAsc(STATUS.PENDING.ordinal())).thenReturn(allPending);
        when(repository.findAllByStatusOrderByLastModifiedAsc(STATUS.PREPARING.ordinal())).thenReturn(allPreparing);
        when(repository.findAll()).thenReturn(allPedidos);
        when(repository.findById(1L)).thenReturn(Optional.of(pedidoPending));
    }

    @Test
    void whenGetPendingPedidos_returnPendingPedidos(){
        List<Pedido> pedidos = service.getPendingPedidos();

        assertThat(pedidos).hasSize(2);
        verify(repository, times(1)).findAllByStatusOrderByLastModifiedAsc(STATUS.PENDING.ordinal());
    }

    @Test
    void whenGetPreparingPedidos_returnPreparingPedidos(){
        List<Pedido> pedidos = service.getPreparingPedidos();

        assertThat(pedidos).hasSize(1);
        verify(repository, times(1)).findAllByStatusOrderByLastModifiedAsc(STATUS.PREPARING.ordinal());
    }

    @Test
    void whenGetAllPedidos_returnAllPedidos(){
        List<Pedido> pedidos = service.getPedidos();

        assertThat(pedidos).hasSize(3);
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

        assertThat(pedidoUpdated).isNull();
        verify(repository, times(1)).findById(2L);
    }
}
