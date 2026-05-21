package com.example.pedido_refactor.strategy;

import com.example.pedido_refactor.model.Pedido;
import org.springframework.stereotype.Component;

@Component("EXPRESS")
public class EnvioExpress implements EstrategiaEnvio {

    @Override
    public double calcularCosto(Pedido pedido) {

        return 12.99;
    }
}