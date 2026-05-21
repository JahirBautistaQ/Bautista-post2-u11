package com.example.pedido_refactor.service;

import com.example.pedido_refactor.model.Cliente;
import com.example.pedido_refactor.model.Pedido;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EnvioService envioService;

    @Test
    void calcularEnvio_estandar_conTotalAlto_debeSerGratis() {

        Pedido pedido = new Pedido();
        pedido.setTotal(60.0);

        double resultado =
                envioService.calcularEnvio(
                        pedido,
                        "ESTANDAR"
                );

        assertEquals(0.0, resultado, 0.001);
    }

    @Test
    void calcularEnvio_express_debeCobrar1299() {

        Pedido pedido = new Pedido();

        double resultado =
                envioService.calcularEnvio(
                        pedido,
                        "EXPRESS"
                );

        assertEquals(12.99, resultado, 0.001);
    }

    @Test
    void calcularEnvio_mismoDia_debeCobrar2499() {

        Pedido pedido = new Pedido();

        double resultado =
                envioService.calcularEnvio(
                        pedido,
                        "MISMO_DIA"
                );

        assertEquals(24.99, resultado, 0.001);
    }

    @Test
    void aprobarCredito_clienteNulo_debeRechazar() {

        String resultado =
                pedidoService.aprobarCredito(
                        null,
                        1000
                );

        assertEquals("RECHAZADO", resultado);
    }

    @Test
    void aprobarCredito_clienteActivoYValido_debeAprobar() {

        Cliente cliente = new Cliente(
                true,
                700,
                5000
        );

        String resultado =
                pedidoService.aprobarCredito(
                        cliente,
                        1000
                );

        assertEquals("APROBADO", resultado);
    }
}