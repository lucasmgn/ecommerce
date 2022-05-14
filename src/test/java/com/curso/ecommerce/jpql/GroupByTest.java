package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.StatusPagamento;
import com.curso.ecommerce.model.StatusPedido;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class GroupByTest  extends EntityManagerTest {

    @Test
    public void agruparFiltrarResultado(){
//        Quantidade de produtos por categoria.
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

//        Total de vendas por mês
//        String jpql = "select concat(year(p.dataCriacao), function('monthname', p.dataCriacao)), sum(p.total)" +
//                " from Pedido p where year(p.dataCriacao) = year(current_date) and p.status = :status " +
//                "group by year(p.dataCriacao), month(p.dataCriacao)";


//        Total de vendas por categoria
//        String jpql =  "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
//                " join ip.produto pro join pro.categorias c join ip.pedido p " +
//                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) = month(current_date) " +
//                " group by c.id";

//        Total de vendas por cliente
//        String jpql ="select pc.nome, sum(ip.precoProduto) from ItemPedido ip join ip.pedido pro join pro.cliente pc group by pc.id";

//      Total de vendas por dia e por categoria
//        String jpql = "select " +
//                " concat(day(p.dataCriacao), '/', month(p.dataCriacao), '/', year(p.dataCriacao)), " +
//                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
//                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
//                " group by day(p.dataCriacao), month(p.dataCriacao), year(p.dataCriacao), c.id " +
//                " order by p.dataCriacao, c.nome ";


        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
//        typedQuery.setParameter("status", StatusPedido.PAGO);
        List<Object[]> lista = typedQuery.getResultList();


        Assert.assertFalse(lista.isEmpty());
        lista.forEach(lis -> System.out.println(lis[0] + ", " + lis[1]));
    }

    @Test
    public void agruparResultado(){
        //Quantidade de produtos por categoria.
        //String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

        //Total de vendas por mês
//        String jpql = "select concat(year(p.dataCriacao), function('monthname', p.dataCriacao)), sum(p.total)" +
//                " from Pedido p group by year(p.dataCriacao), month(p.dataCriacao)";

        //Total de vendas por categoria
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip" +
//                " join ip.produto pro" +
//                " join pro.categorias c group by c.id";

//        //Total de vendas por cliente
//        String jpql ="select pc.nome, sum(ip.precoProduto) from ItemPedido ip join ip.pedido pro join pro.cliente pc group by pc.id";

        //Total de vendas por dia e por categoria
        String jpql = "select " +
                " concat(day(p.dataCriacao), '/', month(p.dataCriacao), '/', year(p.dataCriacao)), " +
                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
                " group by day(p.dataCriacao), month(p.dataCriacao), year(p.dataCriacao), c.id " +
                " order by p.dataCriacao, c.nome ";


        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(lis -> System.out.println(lis[0] + ", " + lis[1]));
    }
}
