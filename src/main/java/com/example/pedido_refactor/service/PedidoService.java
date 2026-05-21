package com.example.pedido_refactor.service;

import com.example.pedido_refactor.model.Cliente;
import com.example.pedido_refactor.model.DatosCliente;
import com.example.pedido_refactor.model.Pedido;
import com.example.pedido_refactor.model.Producto;
import com.example.pedido_refactor.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repo;
    private final NotificacionService notificacionService;

    public PedidoService(PedidoRepository repo,
                         NotificacionService notificacionService) {

        this.repo = repo;
        this.notificacionService = notificacionService;
    }

    public String procesarPedido(Long clienteId,
                                 DatosCliente datosCliente,
                                 List<Long> productosIds,
                                 List<Integer> cantidades,
                                 String metodoPago,
                                 boolean esUrgente,
                                 String codigoDescuento) {

        double total = calcularTotal(productosIds, cantidades);

        if (total == -1) {
            return "ERROR_PRODUCTO";
        }

        double totalConDescuento =
                aplicarDescuento(total, codigoDescuento);

        notificacionService.notificarPedido(
                datosCliente,
                esUrgente
        );

        return persistirPedido(
                clienteId,
                datosCliente,
                totalConDescuento
        );
    }

    private double calcularTotal(List<Long> productosIds,
                                 List<Integer> cantidades) {

        double total = 0;

        for (int i = 0; i < productosIds.size(); i++) {

            Producto producto =
                    repo.findProductoById(productosIds.get(i));

            if (producto == null) {
                return -1;
            }

            total += producto.getPrecio() * cantidades.get(i);
        }

        return total;
    }

    private double aplicarDescuento(double total,
                                    String codigoDescuento) {

        if ("VIP10".equals(codigoDescuento)) {
            return total * 0.90;
        }

        if ("NEW20".equals(codigoDescuento)) {
            return total * 0.80;
        }

        return total;
    }

    private String persistirPedido(Long clienteId,
                                   DatosCliente datosCliente,
                                   double total) {

        Pedido pedido = new Pedido(
                clienteId,
                datosCliente.getNombre(),
                total
        );

        return "OK_" + repo.save(pedido).getId();
    }

    // Switch Statement smell — CC = 5
    public double calcularEnvio(Pedido pedido,
                                String tipoEnvio) {

        switch (tipoEnvio) {

            case "ESTANDAR":
                return pedido.getTotal() > 50 ? 0 : 5.99;

            case "EXPRESS":
                return 12.99;

            case "MISMO_DIA":
                return 24.99;

            case "GRATIS":
                return 0;

            default:
                throw new IllegalArgumentException(
                        "Tipo de envio desconocido: "
                                + tipoEnvio
                );
        }
    }

    // Arrow code — CC = 6
    public String aprobarCredito(Cliente c,
                                 double monto) {

        if (c != null) {

            if (c.isActivo()) {

                if (c.getScore() >= 600) {

                    if (monto > 0) {

                        if (monto <= c.getLimiteCredito()) {

                            return "APROBADO";
                        }
                    }
                }
            }
        }

        return "RECHAZADO";
    }
}