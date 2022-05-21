package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class AbordagemHibridaTest extends EntityManagerTest {

    @BeforeClass
    public static void setUpBeforeClass(){
        entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");

        EntityManager em = entityManagerFactory.createEntityManager();

        String jpql = "select c from Categoria c";
        TypedQuery<Categoria> typedQuery = em.createQuery(jpql, Categoria.class);

        entityManagerFactory.addNamedQuery("Categoria.listar", typedQuery);
    }

    @Test
    public void usarAbordagemHibrida() {
        TypedQuery<Categoria> typedQuery = entityManager.createNamedQuery("Categoria.listar", Categoria.class);

        List<Categoria> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

    }
}
