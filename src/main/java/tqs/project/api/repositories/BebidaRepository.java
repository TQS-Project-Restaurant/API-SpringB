package tqs.project.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.models.Bebida;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Double> {
    
}
