package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PathExpressionsTest extends EntityManagerTest {

    @Test
    public void usarPathExpression(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.like(root.get("cliente").get("nome"), ("M%")));

        TypedQuery<Pedido>typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }
}
