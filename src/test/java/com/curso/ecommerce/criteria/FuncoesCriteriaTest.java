package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FuncoesCriteriaTest extends EntityManagerTest {

    @Test
    public void aplicarFuncaoString(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.multiselect(root.get("nome"),
                criteriaBuilder.concat("Nome do cliente: ", root.get("nome")),
                criteriaBuilder.length(root.get("nome")),
                criteriaBuilder.locate(root.get("nome"), "a"),
                criteriaBuilder.substring(root.get("nome"), 1, 2),
                criteriaBuilder.lower(root.get("nome")),
                criteriaBuilder.upper(root.get("nome")),
                criteriaBuilder.trim(root.get("nome")) //para remover espacos
        );

        criteriaQuery.where(criteriaBuilder.equal(
                criteriaBuilder.substring(root.get("nome"), 1, 1), "M"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", concat: " + arr[1]
                        + ", length: " + arr[2]
                        + ", locate: " + arr[3]
                        + ", substring: " + arr[4]
                        + ", lower: " + arr[5]
                        + ", upper: " + arr[6]
                        + ", trim: |" + arr[7] + "|"));
    }
}
