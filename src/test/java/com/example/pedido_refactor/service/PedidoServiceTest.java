package com.example.pedido_refactor.service;

import com.example.pedido_refactor.model.Cliente;
import com.example.pedido_refactor.model.Pedido;
import com.example.pedido_refactor.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PedidoServiceTest {

    private PedidoService service;

    @BeforeEach
    void setUp() {

        PedidoRepository repo =
                mock(PedidoRepository.class);

        NotificacionService notificacionService =
                mock(NotificacionService.class);

        service = new PedidoService(
                repo,
                notificacionService
        );
    }

    @Test
    void calcularEnvio_estandar_conTotalAlto_debeSerGratis() {

        Pedido pedido = new Pedido();
        pedido.setTotal(60.0);

        double resultado =
                service.calcularEnvio(
                        pedido,
                        "ESTANDAR"
                );

        assertEquals(
                0.0,
                resultado,
                0.001
        );
    }

    @Test
    void calcularEnvio_express_debeCobrar1299() {

        Pedido pedido = new Pedido();

        double resultado =
                service.calcularEnvio(
                        pedido,
                        "EXPRESS"
                );

        assertEquals(
                12.99,
                resultado,
                0.001
        );
    }

    @Test
    void aprobarCredito_clienteNulo_debeRechazar() {

        String resultado =
                service.aprobarCredito(
                        null,
                        1000
                );

        assertEquals(
                "RECHAZADO",
                resultado
        );
    }

    @Test
    void aprobarCredito_clienteValido_debeAprobar() {

        Cliente cliente = new Cliente(
                true,
                700,
                5000
        );

        String resultado =
                service.aprobarCredito(
                        cliente,
                        1000
                );

        assertEquals(
                "APROBADO",
                resultado
        );
    }
}