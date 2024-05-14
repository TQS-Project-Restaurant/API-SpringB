package tqs.project.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.models.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Double> {
    
}
