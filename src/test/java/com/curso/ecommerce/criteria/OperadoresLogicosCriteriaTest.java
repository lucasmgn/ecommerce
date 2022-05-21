package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.StatusPedido;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OperadoresLogicosCriteriaTest extends EntityManagerTest {

    @Test
    public void usarOperadoresLogicosOr() {// Valores maiores que 799
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);

        Root<Pedido> root = criteriaQuery.from((Pedido.class));

        criteriaQuery.select(root);

        //utilizando o operador lógico or
        criteriaQuery.where(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("status"), StatusPedido.AGUARDANDO),
                        criteriaBuilder.equal(root.get("status"), StatusPedido.PAGO)),
                criteriaBuilder.greaterThan(root.get("total"), new BigDecimal(499)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal() + " Status: " + arr.getStatus()));

    }

    @Test
    public void usarOperadoresLogicosAnd() {// Valores maiores que 799
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);

        Root<Pedido> root = criteriaQuery.from((Pedido.class));

        criteriaQuery.select(root);

        //utilizando o operador lógico and
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.greaterThan(root.get("total"), new BigDecimal(799)),
                criteriaBuilder.equal(root.get("status"), StatusPedido.PAGO)),
                criteriaBuilder.greaterThan(root.get("dataCriacao"), LocalDateTime.now().minusDays(5)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));

    }
}
