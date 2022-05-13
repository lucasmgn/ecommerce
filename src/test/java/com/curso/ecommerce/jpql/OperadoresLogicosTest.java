package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OperadoresLogicosTest extends EntityManagerTest {

    @Test
    public void usarOperadorAnd() {
        String jpql = "select p from Pedido p where p.total > 500 and p.status = 'AGUARDANDO' and p.cliente.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarOperadorOr() {
        String jpql = "select p from Pedido p where p.status = 'AGUARDANDO' or p.status = 'PAGO'";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }
}
