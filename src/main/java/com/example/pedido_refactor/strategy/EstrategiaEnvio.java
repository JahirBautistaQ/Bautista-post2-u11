package com.example.pedido_refactor.strategy;

import com.example.pedido_refactor.model.Pedido;

public interface EstrategiaEnvio {

    double calcularCosto(Pedido pedido);
}