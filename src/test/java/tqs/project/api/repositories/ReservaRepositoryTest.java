package tqs.project.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.api.models.Reserva;
import tqs.project.api.models.Utilizador;
import tqs.project.api.others.ROLES;
import tqs.project.api.others.STATUS;

@DataJpaTest
class ReservaRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservaRepository repository;

    LocalDate day = LocalDate.parse("2024-01-01");
    LocalDate day2 = LocalDate.parse("2024-01-02");
    Reserva reserva = new Reserva();


    @BeforeEach
    void setUp(){
        Utilizador utilizador = new Utilizador();
        utilizador.setEmail("nice-email@gmail.com");
        utilizador.setPassword("123123123");
        utilizador.setRole(ROLES.USER.ordinal());

        reserva.setUtilizador(utilizador);
        reserva.setQuantidadeMesas(2);
        reserva.setStatus(STATUS.COMPLETED.ordinal());
        reserva.setDia(day2);
        reserva.setHora(LocalTime.of(11,00));

        entityManager.persist(utilizador);
        entityManager.persistAndFlush(reserva);
    }

    @Test
    void givenNoBookings_whenFindByDay_thenReturnEmpty(){
        List<Reserva> bookings = repository.findByDia(day);

        assertThat(bookings).isEmpty();
    }

    @Test
    void givenBookings_whenFindByDay_thenBookings(){
        List<Reserva> bookings = repository.findByDia(day2);

        assertThat(bookings).hasSize(1);
    }
}
