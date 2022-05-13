package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JoinTest extends EntityManagerTest {

    @Test
    public void usarJoinFetch(){
        String jpql7 =  "select p from Pedido p "
                + " left join fetch p.pagamento "
                + " join fetch p.cliente "
                + " left join fetch p.notaFiscal "
                ;

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql7, Pedido.class);

        List<Pedido> pedidos = typedQuery.getResultList();
        Assert.assertFalse(pedidos.isEmpty());

    }

    @Test
    public void fazerLeftJoin(){
        String jpql = "select p from Pedido p left join p.pagamento pag on pag.status = 'PROCESSANDO'";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> pedidos = typedQuery.getResultList();
        Assert.assertFalse(pedidos.isEmpty());

    }

    @Test
    public void fazerJoin(){
        String jpql = "select p from Pedido p join p.pagamento pag";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> pedidos = typedQuery.getResultList();
        Assert.assertTrue(pedidos.size() == 0);
    }
}
