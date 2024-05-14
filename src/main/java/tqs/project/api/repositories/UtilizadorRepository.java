package tqs.project.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.models.Utilizador;

@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Double> {
    
}
