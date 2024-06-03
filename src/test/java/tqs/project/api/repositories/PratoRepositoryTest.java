package tqs.project.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.api.models.Prato;

@DataJpaTest
class PratoRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PratoRepository repository;

    Prato prato = new Prato();

    @BeforeEach
    void setUp(){
        prato.setNome("Francesinha");
        prato.setPreco(2.5);
        prato.setStock(10);

        entityManager.persistAndFlush(prato);
    }

    @Test
    void givenPrato_whenFindPratoById_thenReturnPrato(){
        Prato found = repository.findById(prato.getId()).get();
        
        assertThat(found.getNome()).isEqualTo("Francesinha");
    }

    @Test
    void givenPrato_whenFindPratoByWrongId_thenReturnNull(){
        Optional<Prato> found = repository.findById(300L);
        
        assertThat(found).isEmpty();
    }
}
