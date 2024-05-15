package tqs.project.api.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bebida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = true)
    private Double hidratosCarbono;

    @Column(nullable = true)
    private Double proteina;

    @Column(nullable = true)
    private int kcal;

    @Column(nullable = false)
    private int stock;

    @ManyToMany
    private List<Menu> menus;

    @ManyToMany
    private List<Pedido> pedidos;

    @Column(nullable = true)
    private String imagemUrl;
}
