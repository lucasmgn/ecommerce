package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class GroupByCriteriaTest extends EntityManagerTest {

    @Test
    public void condicionalDeAgrupamentoHaving(){
        //Total de vendas dentre das categorias que mais vendem
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //Tabela de crtério

        Root<ItemPedido> root = criteriaQuery.from((ItemPedido.class)); //Tabela raiz

        Join<ItemPedido, Produto> joinProduto = root.join("produto");
        Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join("categorias");

        criteriaQuery.multiselect(
                joinProdutoCategoria.get("nome"),
                criteriaBuilder.sum(root.get("precoProduto")),
                criteriaBuilder.avg(root.get("precoProduto"))
        );

        criteriaQuery.groupBy(joinProdutoCategoria.get("id"));

        criteriaQuery.having(criteriaBuilder.greaterThan(
                criteriaBuilder.avg(root.get("precoProduto")).as(BigDecimal.class), new BigDecimal(700)));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("Nome Categoria: " + arr[0] + ", Sum: " + arr[1] + " Média: " + arr[2]));
    }

    @Test
    public void agruparResultadosComFuncoes(){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        Expression<Integer> anoCriacaoPedido = criteriaBuilder.function(
                "year", Integer.class, root.get("dataCriacao"));

        Expression<Integer> mesCriacaoPedido = criteriaBuilder.function(
                "month", Integer.class, root.get("dataCriacao"));

        Expression<String> mesNameCriacaoPedido = criteriaBuilder.function(
                "month", String.class, root.get("dataCriacao"));

        Expression<String> anoMesConcat = criteriaBuilder.concat(
                criteriaBuilder.concat(
                        anoCriacaoPedido.as(String.class), "/"), mesNameCriacaoPedido);

        criteriaQuery.multiselect(
                anoMesConcat,
                criteriaBuilder.sum(root.get("total"))
        );

        criteriaQuery.groupBy(anoCriacaoPedido, mesCriacaoPedido);

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("Ano/Mês: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void agruparResultado03(){
        //Total de vendas por Cliente
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //Tabela de crtério

        Root<ItemPedido> root = criteriaQuery.from((ItemPedido.class)); //Tabela raiz

        Join<ItemPedido, Pedido> joinPedido = root.join("pedido");
        Join<Produto, Cliente> joinCategoriaProduto = joinPedido.join("cliente");

        criteriaQuery.multiselect(
                                    joinCategoriaProduto.get("nome"),
                criteriaBuilder.sum(root.get("precoProduto"))
        );

        criteriaQuery.groupBy( joinCategoriaProduto.get("id"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("Nome Categoria: " + arr[0] + ", Sum: " + arr[1]));
    }

    @Test
    public void agruparResultado02(){
        //Total de vendas por categoria
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //Tabela de crtério

        Root<ItemPedido> root = criteriaQuery.from((ItemPedido.class)); //Tabela raiz

        Join<ItemPedido, Produto> joinProduto = root.join("produto");
        Join<Produto,Categoria> joinCategoriaProduto = joinProduto.join("categorias");

        criteriaQuery.multiselect(
                joinCategoriaProduto.get("nome"),
                criteriaBuilder.sum(root.get("precoProduto"))
        );

        criteriaQuery.groupBy( joinCategoriaProduto.get("id"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("Nome Categoria: " + arr[0] + ", Sum:" + arr[1]));
    }

    @Test
    public void agruparResultado01(){
        //Quantidade de produtos por categoria
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //Tabela de crtério

        Root<Categoria> root = criteriaQuery.from((Categoria.class)); //Tabela raiz

        Join<Categoria, Produto> joinCategoriaProduto = root.join("produtos", JoinType.LEFT);

        criteriaQuery.multiselect(
                root.get("nome"),
                criteriaBuilder.count(joinCategoriaProduto.get("id"))
        );

        criteriaQuery.groupBy(root.get("id"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", Count:" + arr[1]));
    }
}
