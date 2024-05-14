package tqs.project.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.Models.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Double> {
    
}
