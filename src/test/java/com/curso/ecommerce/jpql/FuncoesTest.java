package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FuncoesTest extends EntityManagerTest {

    @Test
    public void aplicarFuncaoData(){
        //current_date devolve a data, current_time hora minuto e segundo, data junto com a hora atual
        // year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao)
        //hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao)
        //"select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) from Pedido p where hour(p.dataCriacao) > 18"
        String jpql = "select current_date, current_time, current_timestamp from Pedido p";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

    @Test
    public void aplicarFuncaoString() {
        //concat, lenght, locate, substring, lower, upper(DEIXAR TUDO EM CAIXA ALTA), trim(PARA REMOVER ESPAÇOS DO INICIO AO FINAL)

        String jpql = "select c.nome, concat('Categoria: ',c.nome) from Categoria c ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
    }

}
