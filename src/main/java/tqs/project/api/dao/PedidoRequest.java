package tqs.project.api.dao;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PedidoRequest {
    List<PedidoItem> bebidas;
    
    List<PedidoItem> pratos;

    int mesa;
}
