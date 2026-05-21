package com.example.pedido_refactor.repository;

import com.example.pedido_refactor.model.Pedido;
import com.example.pedido_refactor.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    default Producto findProductoById(Long id) {

        // Datos simulados
        if (id == 1L) {
            return new Producto(1L, "Hamburguesa", 20000);
        }

        if (id == 2L) {
            return new Producto(2L, "Pizza", 35000);
        }

        return null;
    }
}