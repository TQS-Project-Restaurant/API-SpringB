package tqs.project.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
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
    Utilizador utilizador = new Utilizador();


    @BeforeEach
    void setUp(){
        utilizador.setEmail("nice-email@gmail.com");
        utilizador.setPassword("123123123");
        utilizador.setRole(ROLES.USER);

        reserva.setUtilizador(utilizador);
        reserva.setQuantidadeMesas(2);
        reserva.setStatus(STATUS.COMPLETED.ordinal());
        reserva.setDia(day2);
        reserva.setHora(LocalTime.of(11,00));

        utilizador.setReservas(Arrays.asList(reserva));

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

    @Test
    void givenManyBookings_whenFindByUtilizador_thenReturnUtilizadorBookings(){
        List<Reserva> bookings = repository.findByUtilizador(utilizador);

        assertThat(bookings).hasSize(1);
    }

    @Test
    void givenNoBookings_whenFindByUtilizador_thenReturnEmpty(){
        Utilizador utilizador2 = new Utilizador();

        utilizador2.setEmail("www@gmail.com");
        utilizador2.setPassword("123123123");
        utilizador2.setRole(ROLES.USER);

        entityManager.persistAndFlush(utilizador2);

        List<Reserva> bookings = repository.findByUtilizador(utilizador2);

        assertThat(bookings).isEmpty();
    }
}
