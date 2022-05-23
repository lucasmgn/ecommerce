package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FuncoesCriteriaTest extends EntityManagerTest {

    @Test
    public void aplicarFuncaoAgregacao(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        /*
        * Criando funções por cima da coluna total da tabela pedido,
        * funções count, avg, sum, min e max
        * */
        criteriaQuery.multiselect(
                criteriaBuilder.count(root.get("id")),
                criteriaBuilder.avg(root.get("total")),
                criteriaBuilder.sum(root.get("total")),
                criteriaBuilder.min(root.get("total")),
                criteriaBuilder.max(root.get("total"))


        );

        criteriaQuery.where(criteriaBuilder.isTrue(
                criteriaBuilder.function("acima_media_faturamento", Boolean.class, root.get("total"))));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                       "avg: " + arr[0]
                        + ", count: " + arr[1]
                        + ", sum: " + arr[2]
                        + ", min: " + arr[3]
                        + ", max: " + arr[4]));
    }

    @Test
    public void aplicarFuncaoNativas(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(root.get("id"),
                criteriaBuilder.function("dayname", String.class, root.get("dataCriacao"))
        );

        criteriaQuery.where(criteriaBuilder.isTrue(
                criteriaBuilder.function("acima_media_faturamento", Boolean.class, root.get("total"))));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                arr[0] + ", dayname: " + arr[1])); //dayname função nativa do mysql
    }

    @Test
    public void aplicarFuncaoColecao(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(root.get("id"),
                criteriaBuilder.size(root.get("itens"))
        );

        criteriaQuery.where(criteriaBuilder.greaterThan(criteriaBuilder.size(root.get("itens")),1)); //size > 1

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", size: " + arr[1]));
    }

    @Test
    public void aplicarFuncaoNumero(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(root.get("id"),
                criteriaBuilder.abs(criteriaBuilder.prod(root.get("id"), -1)), //produto de id * -1
                criteriaBuilder.mod(root.get("id"), 2), //divindindo o id por 2 e mostrando o resto
                criteriaBuilder.sqrt(root.get("total"))
        );

        criteriaQuery.where(criteriaBuilder.greaterThan(criteriaBuilder.sqrt(root.get("total")), 10.0)); //total > 10

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", abs: " + arr[1]
                        + ", mod: " + arr[2]
                        + ", sqrt: " + arr[3]));
    }

    @Test
    public void aplicarFuncaoData(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento"); //fazendo um join para pedido e pagamento

        Join<Pedido, PagamentoBoleto> joinPagamentoBoleto = //Fazendo um treat(tratando o joiPagamento como um Pagamento Boleto)
                criteriaBuilder.treat(joinPagamento, PagamentoBoleto.class);


        criteriaQuery.multiselect(root.get("id"),
                criteriaBuilder.currentDate(),
                criteriaBuilder.currentTime(),
                criteriaBuilder.currentTimestamp()
        );

        criteriaQuery.where(
                criteriaBuilder.between(criteriaBuilder.currentDate(),
                    root.get("dataCriacao").as(java.sql.Date.class),
                    joinPagamentoBoleto.get("dataVencimento").as(java.sql.Date.class)),
                criteriaBuilder.equal(root.get("status"), StatusPedido.AGUARDANDO)
        );



        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty()); //Erro de asserção, não encontra o pedido

        lista.forEach(arr -> System.out.println(
                arr[0]
                        + ", currentDate: " + arr[1]
                        + ", currentTime: " + arr[2]
                        + ", currentTimestamp: " + arr[3]   ));
    }

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
