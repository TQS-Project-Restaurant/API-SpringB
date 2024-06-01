package tqs.project.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.api.models.Bebida;

@DataJpaTest
class BebidaRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BebidaRepository repository;

    Bebida beverage = new Bebida();

    @BeforeEach
    void setUp(){
        beverage.setNome("Fanta");
        beverage.setPreco(2.5);
        beverage.setStock(10);

        entityManager.persistAndFlush(beverage);
    }

    @Test
    void givenBebida_whenFindBebidaById_thenReturnBebida(){
        Bebida found = repository.findById(beverage.getId()).get();
        
        assertThat(found.getNome()).isEqualTo("Fanta");
    }

    @Test
    void givenBebida_whenFindBebidaByWrongId_thenReturnNull(){
        Optional<Bebida> found = repository.findById(300L);
        
        assertThat(found).isEmpty();
    }
}
