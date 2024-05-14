package tqs.project.api.Services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.api.Repositories.PedidoRepository;
import tqs.project.api.Services.impl.PedidoServiceImpl;
import tqs.project.api.Models.Pedido;
import tqs.project.api.Others.STATUS;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {
    @Mock(lenient = true)
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

        Mockito.when(repository.findAllByStatus(STATUS.PENDING.ordinal())).thenReturn(allPending);
        Mockito.when(repository.findAllByStatus(STATUS.PREPARING.ordinal())).thenReturn(allPreparing);
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
