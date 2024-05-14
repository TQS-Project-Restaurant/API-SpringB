package tqs.project.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.Models.Utilizador;

@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Double> {
    
}
