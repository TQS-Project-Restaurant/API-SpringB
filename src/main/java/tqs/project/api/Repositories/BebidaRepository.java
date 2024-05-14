package tqs.project.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.Models.Bebida;

@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Double> {
    
}
