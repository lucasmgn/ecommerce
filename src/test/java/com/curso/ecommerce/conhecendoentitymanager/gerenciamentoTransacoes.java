package com.curso.ecommerce.conhecendoentitymanager;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.StatusPedido;
import org.junit.Test;

public class gerenciamentoTransacoes extends EntityManagerTest {

    @Test(expected = Exception.class)
    public void abrirFecharCancelarTransacao(){
        try{
            entityManager.getTransaction().begin();
            metodoDeNegocio();
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            throw e;
        }

    }

private void metodoDeNegocio(){
    Pedido pedido = entityManager.find(Pedido.class, 1);
    pedido.setStatus(StatusPedido.PAGO);

    if(pedido.getPagamento() == null){
        throw new RuntimeException("Pedido ainda não foi pago");
    }
}
}
