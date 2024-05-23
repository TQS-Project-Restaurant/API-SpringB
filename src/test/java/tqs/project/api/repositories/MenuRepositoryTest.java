package tqs.project.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.api.models.Menu;

@DataJpaTest
class MenuRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MenuRepository repository;

    Menu menu = new Menu();
    Menu menu2 = new Menu();
    Menu menu3 = new Menu();

    @BeforeEach
    void setUp(){
        menu.setDia(LocalDate.now());
        menu2.setDia(LocalDate.now().plusDays(1));
        menu3.setDia(LocalDate.now().plusDays(2));

        entityManager.persist(menu);
        entityManager.persist(menu2);
        entityManager.persistAndFlush(menu3);
    }

    @Test
    void givenSetOfMenus_whenFindDailyMenu_thenReturnDailyMenu(){
        Menu menu = repository.findByDia(LocalDate.now());

        assertThat(menu.getDia()).isEqualTo(LocalDate.now().toString());
    }
}
