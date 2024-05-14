package tqs.project.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.Models.Prato;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Double> {
    
}
