package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.NotaFiscal;
import com.curso.ecommerce.model.Pedido;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PassandoParametrosCriteriaTest extends EntityManagerTest { // não é muito usado

    @Test
    public void passarParametroDate(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotaFiscal> criteriaQuery = criteriaBuilder.createQuery(NotaFiscal.class);
        Root<NotaFiscal> root = criteriaQuery.from(NotaFiscal.class);

        criteriaQuery.select(root);

        ParameterExpression<Date> parameterExpressionData = criteriaBuilder.parameter(Date.class, "dataInical");

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("dataEmissao"), parameterExpressionData));//seja maior

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(criteriaQuery);

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.add(Calendar.DATE, -30);

        typedQuery.setParameter("dataInical",dataInicial.getTime(), TemporalType.TIMESTAMP);

        List<NotaFiscal> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void passarParametro(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        ParameterExpression<Integer> parameterExpressionId = criteriaBuilder.parameter(Integer.class, "id");

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), parameterExpressionId));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setParameter("id",2);

        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);
    }
}
