package tqs.project.api.Repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.api.Models.Pedido;
import tqs.project.api.Others.STATUS;

@DataJpaTest
class PedidoRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PedidoRepository repository;

    @Test
    void givenSetOfPendingPedidos_whenFindPending_thenReturnAllPending() {
        Pedido pedido = new Pedido();
        pedido.setMesa(0);
        pedido.setStatus(STATUS.PENDING.ordinal());

        entityManager.persistAndFlush(pedido);

        List<Pedido> pendingPedidos = repository.findAllByStatus(STATUS.PENDING.ordinal());
        
        assertThat(pendingPedidos).hasSize(1);
    }
}
