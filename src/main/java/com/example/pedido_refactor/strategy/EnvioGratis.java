package com.example.pedido_refactor.strategy;

import com.example.pedido_refactor.model.Pedido;
import org.springframework.stereotype.Component;

@Component("GRATIS")
public class EnvioGratis implements EstrategiaEnvio {

    @Override
    public double calcularCosto(Pedido pedido) {

        return 0.0;
    }
}