package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Categoria;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PaginacaoJPQLTest extends EntityManagerTest {

    @Test
    public void ordenarResultados(){
        String jpql = "select c from Categoria c order by c.nome";

        TypedQuery<Categoria> typedQuery = entityManager.createQuery(jpql,Categoria.class);
        //FIRST_RESULT = MAX_RESULTS * (página - 1)
        typedQuery.setFirstResult(5); //primeira página =0
        typedQuery.setMaxResults(5); // vai aparecer 2 linhas por página

        List<Categoria> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr.getId() + ", " + arr.getNome()));
    }

}
