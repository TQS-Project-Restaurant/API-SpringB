package tqs.project.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.project.api.Models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Double> {
    List<Pedido> findAllByStatus(int status);
}
