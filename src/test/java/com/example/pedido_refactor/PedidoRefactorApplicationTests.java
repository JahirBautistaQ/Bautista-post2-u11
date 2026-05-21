package com.example.pedido_refactor;

import com.example.pedido_refactor.model.Cliente;
import com.example.pedido_refactor.model.Pedido;
import com.example.pedido_refactor.service.EnvioService;
import com.example.pedido_refactor.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PedidoRefactorApplicationTests {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private PedidoService pedidoService;

    @Test
    void calcularEnvio_estandar_conTotalAlto_debeSerGratis() {

        Pedido pedido = new Pedido();
        pedido.setTotal(60.0);

        double costo =
                envioService.calcularEnvio(
                        pedido,
                        "ESTANDAR"
                );

        assertEquals(0.0, costo, 0.001);
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
    void calcularEnvio_express_debeRetornar1299() {

        Pedido pedido = new Pedido();
        pedido.setTotal(20.0);

        double costo =
                envioService.calcularEnvio(
                        pedido,
                        "EXPRESS"
                );

        assertEquals(12.99, costo, 0.001);
    }

    @Test
    void calcularEnvio_mismoDia_debeRetornar2499() {

        Pedido pedido = new Pedido();
        pedido.setTotal(20.0);

        double costo =
                envioService.calcularEnvio(
                        pedido,
                        "MISMO_DIA"
                );

        assertEquals(24.99, costo, 0.001);
    }

    @Test
    void aprobarCredito_clienteValido_debeAprobar() {

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