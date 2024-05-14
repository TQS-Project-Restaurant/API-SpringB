package tqs.project.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.models.Prato;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Double> {
    
}
