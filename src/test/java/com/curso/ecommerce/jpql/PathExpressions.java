package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PathExpressions extends EntityManagerTest {

    //pecorrer o caminho até o especificado
    @Test
    public void buscarPedidosComProdutoEspecifico(){
        String jpql = "select p from Pedido p join p.itens i where i.id.produtoId =1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() ==2);
    }

    //pecorrer o caminho até o especificado
    @Test
    public void usarPathExpression(){
        String jpql = "select p from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}
