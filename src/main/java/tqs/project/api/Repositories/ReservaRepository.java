package tqs.project.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.Models.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Double>{
    
}
