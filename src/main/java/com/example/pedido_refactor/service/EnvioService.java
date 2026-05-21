package com.example.pedido_refactor.service;

import com.example.pedido_refactor.model.Pedido;
import com.example.pedido_refactor.strategy.EstrategiaEnvio;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class EnvioService {

    private final Map<String, EstrategiaEnvio> estrategias;

    public EnvioService(Map<String, EstrategiaEnvio> estrategias) {

        this.estrategias = estrategias;
    }

    public double calcularEnvio(Pedido pedido, String tipo) {

        return Optional.ofNullable(estrategias.get(tipo))
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Tipo de envío desconocido: " + tipo
                        ))
                .calcularCosto(pedido);
    }
}