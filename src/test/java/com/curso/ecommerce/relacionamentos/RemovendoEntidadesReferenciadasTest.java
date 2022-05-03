package com.curso.ecommerce.relacionamentos;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

public class RemovendoEntidadesReferenciadasTest extends EntityManagerTest {

    @Test
    public void removendoEtidadesRelacionada(){
        Pedido pedido = entityManager.find(Pedido.class, 1);

        Assert.assertFalse(pedido.getItens().isEmpty());


        entityManager.getTransaction().begin();
        pedido.getItens().forEach(i -> entityManager.remove(i)); //Removendo todos os itens da lista itens para depois remover o pedido
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();

        Pedido pedidoVerificao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNull(pedidoVerificao);

    }

}
