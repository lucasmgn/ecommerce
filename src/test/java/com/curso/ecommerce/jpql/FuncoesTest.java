package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FuncoesTest extends EntityManagerTest {

    @Test
    public void aplicarFuncaoAgregacao(){
        //avg para tirar média, count contar a quantidade de registro que está retornando,
        // min pega o valor minimo,max pega o valor maximo, sum soma tudo

        String jpql = "select count(p.id) from Categoria p where p.nome like concat(:nome, '%') ";

        TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);
        typedQuery.setParameter("nome", "E");

        List<Number> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr));
    }

    @Test
    public void comoUsarFuncaoNativa(){

        String jpql = "select p from Pedido p where function('acima_media_faturamento', p.total) = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr));
    }

    @Test
    public void aplicarFuncaoColecoes(){

        String jpql = "select size(p.itens) from Pedido p where size(p.itens) > 1";

        TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql,Integer.class);

        List<Integer> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr));
    }

    @Test
    public void aplicarFuncaoNumeros(){
        //abs() numero absoluto, mod() retorna o resto da divisao, sqrt() retorna a raíz quadrada
        String jpql = "select abs(p.total), mod(p.id,2), sqrt(p.total) from Pedido p where abs(p.total) > 1000";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
    }

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
