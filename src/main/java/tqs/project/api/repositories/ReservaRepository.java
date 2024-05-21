package tqs.project.api.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.models.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long>{
    List<Reserva> findByDia(LocalDate date);
}
