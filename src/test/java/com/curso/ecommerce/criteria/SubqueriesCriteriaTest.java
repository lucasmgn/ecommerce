package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class SubqueriesCriteriaTest extends EntityManagerTest {

    @Test
    public void pesquisarComAllExercicio() { //Erro n identificado
        /*
        * Todos os produtos que sempre foram vendidos pelo mesmo preço.
        * String jpql = "select distinct p from ItemPedido ip join ip.produto p where " +
        * " ip.precoProduto = ALL " +
        *  " (select precoProduto from ItemPedido where produto = p and id <> ip.id)";
        * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);

        criteriaQuery.select(root.get("produto"));
        criteriaQuery.distinct(true);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get("precoProduto"));

        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get("produto"), root.get("produto")),
                criteriaBuilder.notEqual(subqueryRoot, root)
        );

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get("precoProduto"), criteriaBuilder.all(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComAny02() {
        /*
        * Todos os produtos que já foram vendidos por um preco diferente do atual
        *  String jpql = "select p from Produto p " +
        * " where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";
        *
        * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get("precoProduto"));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get("produto"), root.get("id")));

        criteriaQuery.where(
                criteriaBuilder.notEqual(
                        root.get("preco"), criteriaBuilder.any(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

    }

    @Test
    public void pesquisarComAny01() {
        /*
         * Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual.
         * String jpql = "select p from Produto p " +
         * " where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";
         * Any - qualquer
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get("precoProduto"));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get("produto"), root.get("id")));

        criteriaQuery.where(
                criteriaBuilder.equal(
                        root.get("preco"), criteriaBuilder.any(subquery))
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

    }

    @Test
    public void pesquisarComAll02() {
        /*
        * Todos os produtos não foram vendidos mais depois que encareceram
        * String jpql = "select p from Produto p where " +
        * " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";
        *  " and exists (select 1 from ItemPedido where produto = p)";
        * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get("precoProduto"));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get("produto"), root.get("id")));

        criteriaQuery.where(
                criteriaBuilder.greaterThan(
                        root.get("preco"), criteriaBuilder.all(subquery)),
                criteriaBuilder.exists(subquery)
        );

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComALL() {
        /*
         * Retornar todos os Produtos que sempre foram vendidos pelo preço atual
         * String jpql = "select p from Produto p where " +
         * " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";
         * " and exists (select 1 from ItemPedido where produto = p)";
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(subqueryRoot.get("precoProduto"));

        subquery.where(criteriaBuilder.equal(subqueryRoot.get("produto"), root.get("id")));

        criteriaQuery.where(criteriaBuilder.equal(root.get("preco"), criteriaBuilder.all(subquery)));


        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComSubqueriaExcercicio03Exist() {
        /*
         * Retornar todos os Produtos que ja foram vendidos por um preço diferente do atual
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(
                criteriaBuilder.equal(subqueryRoot.get("produto"), root.get("id")),
                criteriaBuilder.notEqual(
                        subqueryRoot.get("precoProduto"), root.get("preco"))
        );

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComSubqueriaExcercicio02IN() {
        /*
         * Retornar todos os Pedidos que tem algum produto da categoria id = 2
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Produto> JoinProduto = subqueryRoot.join("produto");
        Join<Produto, Categoria> subqueryJoinProdutoCategoria = JoinProduto.join("categorias");

        subquery.select(subqueryRoot.get("id").get("pedidoId"));
        subquery.where(criteriaBuilder.equal(subqueryJoinProdutoCategoria.get("id"), 2));

        criteriaQuery.where(root.get("id").in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }

    @Test
    public void pesquisarComSubqueriaExcercicio01() {
        /*
         * Todos os Clientes que fizeram mais que dois pedidos que já foram vendidos.
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get("cliente").get("id"), root.get("id")));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }


    @Test
    public void pesquisarComExists() {
        /*
         * Todos os produtos que já foram vendidos.
         * String jpql = "select p from Produto p where exists " +
         *  " (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get("produto").get("id"), root.get("id")));

        criteriaQuery.where(criteriaBuilder.exists(subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() ));
    }

    @Test
    public void pesquisarComIN() {
        /*
         * String jpql = "select p from Pedido p where p.id in " +
         * " (select p2.id from ItemPedido i2 " +
         * " join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
        Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
        Join<ItemPedido, Pedido> joinPedido = subqueryRoot.join("pedido");
        Join<ItemPedido, Produto> joinProduto = subqueryRoot.join("produto");

        subquery.select(joinPedido.get("id"));
        subquery.where(criteriaBuilder.greaterThan(joinProduto.get("preco"), new BigDecimal(100)));

        criteriaQuery.where(root.get("id").in(subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() ));
    }

    @Test
    public void pesquisarSubqueries03() {
        /*
        * Bons clientes.
        * String jpql = "select c from Cliente c where " +
        * " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";
        * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> root = criteriaQuery.from(Cliente.class);

        criteriaQuery.select(root);

        /*
        * Tive que utilizar esse trecho, pois estavam dando erro de interpretação:
        * 'subquery.where(criteriaBuilder.equal(root.get("id"), subqueryRoot.get("cliente").get("id")));'
        *
        * O trecho usado por Alex foi:
        * 'subquery.where(criteriaBuilder.equal(root, subqueryRoot.get("cliente")));'
        * */
        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.sum(subqueryRoot.get("total")));
        subquery.where(criteriaBuilder.equal(
                root.get("id"), subqueryRoot.get("cliente").get("id")));

        criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Cliente> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getNome()));
    }

    @Test
    public void pesquisarSubqueries02(){
        /*
         * Irá retornar uma subquery do Produto.preco, por isso que o parametro está como bigdecimal
         * Todos os pedidos acima da média de vendas
         *  String jpql = "select p from Pedido p where " +
         * " p.total > (select avg(total) from Pedido)";
         * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
        subquery.select(criteriaBuilder.avg(subqueryRoot.get("total")).as(BigDecimal.class));

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("total"), subquery));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Total: " + obj.getTotal()));
    }

    @Test
    public void pesquisarSubqueries01(){
        /*
        * Irá retornar uma subquery do Produto.preco, por isso que o parametro está como bigdecimal
        * O produto ou os produtos mais caros da base.
        *  String jpql = "select p from Produto p where " +
        * " p.preco = (select max(preco) from Produto)"
        * */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);

        Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
        Root<Produto> subqueryRoot = subquery.from(Produto.class);
        subquery.select(criteriaBuilder.max(subqueryRoot.get("preco")));

        criteriaQuery.where(criteriaBuilder.equal(root.get("preco"), subquery));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Produto> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println(
                "ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Preço: " + obj.getPreco()));
    }

}
