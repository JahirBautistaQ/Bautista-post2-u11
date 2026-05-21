package com.example.pedido_refactor.strategy;

import com.example.pedido_refactor.model.Pedido;
import org.springframework.stereotype.Component;

@Component("ESTANDAR")
public class EnvioEstandar implements EstrategiaEnvio {

    @Override
    public double calcularCosto(Pedido pedido) {

        return pedido.getTotal() > 50 ? 0.0 : 5.99;
    }
}