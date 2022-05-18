package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SubqueriesTest extends EntityManagerTest {

    @Test
    public void ExercicioPesquisarComAny() {
        // Todos os produtos que sempre foram vendidos pelo mesmo preço.
        String jpql = "select distinct p from ItemPedido ip join ip.produto p where " +
                " ip.precoProduto = ALL " +
                " (select precoProduto from ItemPedido where produto = p and id <> ip.id)";

        //problema para criar query com esse jpql
        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny() {
        //Todos os produtos que já foram vendidos por um preço diferente do atual
//        String jpql ="select p from Produto p where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";

        //Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual
        String jpql = "select p from Produto p where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComALL() {
        //Todos os produtos que não foram vendidos depois que encareceram
        String jpql ="select p from Produto p where p.preco > ALL(select precoProduto from ItemPedido where produto = p)";

        //Todos os produtos que sempre foram vendidos pelo preço atual
//        String jpql = "select p from Produto p where p.preco = ALL(select precoProduto from ItemPedido where produto = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComExistsExercicio() {
        String jpql = "select p from Produto p " +
                " where exists " +
                " (select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
//        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void perquisarComSubqueryExercicio() {
        String jpql = "select c from Cliente c where " +
                " (select count(cliente) from Pedido where cliente = c) >= 2";

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComINExercicio() {
        String jpql = "select p from Pedido p where p.id in " +
                "(select p2.id from ItemPedido ip2" +
                " join ip2.pedido p2" +
                " join ip2.produto pro2" +
                " join pro2.categorias c2" +
                " where c2.id = 2)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComExists(){
        String jpql = "select p from Produto p where exists " +
                "(select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId()));
    }

    @Test
    public void pesquisarSubqueriesIN(){
        String jpql = "select p from Pedido p where" +
                " p.id in " +
                "(select p2.id from ItemPedido i2" +
                " join i2.pedido p2 " +
                "join i2.produto pro2 " +
                "where pro2.preco > 100) ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId()));
    }

    @Test
    public void pesquisarSubqueries(){ //gerar informação a partir dos dados
        //Bons clientes. Versão 2
        String jpql = "select c from Cliente c where " +
                " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";

        //Bons clientes(clientes que compram mais de 500 reais). Versão 1
        /*
        String jpql = "select c from Cliente c where " +
                " 500 < (select sum(p.total) from c.pedidos p)";
        * */

        //Todos os pedidos acima da média de vendas
        /*
        *  String jpql = "select p from Pedido p where " +
                "p.total > (select avg(total) from Pedido)";*
        * */


        //O produto ou os produtos mais caros da base
        /*
        String jpql = "select p from Produto p where " +
                      "p.preco = (select max(preco) from Produto)";
        * */


        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId() + ", Nome: " + arr.getNome()));
    }
}
