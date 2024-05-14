package tqs.project.api.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        pedido.setMesa(1);
        pedido.setStatus(STATUS.PENDING.ordinal());

        Pedido pedido2 = new Pedido();
        pedido2.setMesa(2);
        pedido2.setStatus(STATUS.PREPARING.ordinal());

        List<Pedido> allPending = Arrays.asList(pedido);
        List<Pedido> allPreparing = Arrays.asList(pedido2);

        when(repository.findAllByStatus(STATUS.PENDING.ordinal())).thenReturn(allPending);
        when(repository.findAllByStatus(STATUS.PREPARING.ordinal())).thenReturn(allPreparing);
    }

    @Test
    void whenGetPendingPedidos_returnPendingPedidos(){
        List<Pedido> pendingPedidos = service.getPendingPedidos();

        assertThat(pendingPedidos).hasSize(1);
        verify(repository, times(1)).findAllByStatus(STATUS.PENDING.ordinal());
    }

    @Test
    void whenGetPreparingPedidos_returnPreparingPedidos(){
        List<Pedido> preparingPedidos = service.getPreparingPedidos();

        assertThat(preparingPedidos).hasSize(1);
        verify(repository, times(1)).findAllByStatus(STATUS.PREPARING.ordinal());
    }

}
