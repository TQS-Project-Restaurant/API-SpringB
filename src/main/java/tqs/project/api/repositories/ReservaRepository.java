package tqs.project.api.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.models.Reserva;
import tqs.project.api.models.Utilizador;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    List<Reserva> findByDia(LocalDate date);

    List<Reserva> findByDiaAndHora(LocalDate date, LocalTime time);

    List<Reserva> findByUtilizador(Utilizador utilizador);

    List<Reserva> findAllByStatus(int status);
}
