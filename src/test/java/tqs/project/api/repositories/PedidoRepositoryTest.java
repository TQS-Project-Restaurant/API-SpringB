package tqs.project.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.api.models.Pedido;
import tqs.project.api.others.STATUS;

@DataJpaTest
class PedidoRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PedidoRepository repository;

    Pedido pedido = new Pedido();
    Pedido pedido2 = new Pedido();
    Pedido pedido3 = new Pedido();
    Pedido pedido4 = new Pedido();

    @BeforeEach
    void setUp(){
        pedido.setMesa(0);
        pedido.setStatus(STATUS.PENDING.ordinal());

        pedido2.setMesa(1);
        pedido2.setStatus(STATUS.PREPARING.ordinal());

        pedido3.setMesa(2);
        pedido3.setStatus(STATUS.COMPLETED.ordinal());

        pedido4.setMesa(3);
        pedido4.setStatus(STATUS.CANCELED.ordinal());

        entityManager.persist(pedido);
        entityManager.persist(pedido2);
        entityManager.persist(pedido3);
        entityManager.persistAndFlush(pedido4);
    }


    @Test
    void givenSetOfPendingPedidos_whenFindPending_thenReturnAllPending() {
        List<Pedido> pedidos = repository.findAllByStatus(STATUS.PENDING.ordinal());
        
        assertThat(pedidos).hasSize(1);
    }

    @Test
    void givenSetOfPreparingPedidos_whenFindPreparing_thenReturnAllPreparing() {
        List<Pedido> pedidos = repository.findAllByStatus(STATUS.PREPARING.ordinal());
        
        assertThat(pedidos).hasSize(1);
    }

    @Test
    void givenSetOfCompletedPedidos_whenFindCompleted_thenReturnAllCompleted() {
        List<Pedido> pedidos = repository.findAllByStatus(STATUS.COMPLETED.ordinal());
        
        assertThat(pedidos).hasSize(1);
    }

    @Test
    void givenSetOfCanceledPedidos_whenFindCanceled_thenReturnAllCanceled() {
        List<Pedido> pedidos = repository.findAllByStatus(STATUS.CANCELED.ordinal());
        
        assertThat(pedidos).hasSize(1);
    }

    @Test
    void givenSetOfPedidos_whenFindAll_thenReturnAll() {
        List<Pedido> pedidos = repository.findAll();
        
        assertThat(pedidos).hasSize(4);
    }
}
