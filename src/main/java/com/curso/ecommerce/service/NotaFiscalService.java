package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Pedido;

public class NotaFiscalService {
    public void gerar(Pedido pedido){
        System.out.println("Gerando nota para pedido " + pedido.getId() + ".");
    }
}
