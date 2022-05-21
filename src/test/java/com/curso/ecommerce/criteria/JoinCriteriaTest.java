package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JoinCriteriaTest extends EntityManagerTest {

    @Test
    public void buscasPedidosComProdutoEspecifico(){ //buscar todos os pedidos que tenham um produto de identificador 1
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<ItemPedido, Produto> joinItemProduto = root.join("itens").join("produto");

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarJoinFetch(){ //esconomizando trazendo tudop em uma query só
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);

        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        root.fetch("cliente"); //Inner join utilizando o fetch
        root.fetch("notaFiscal", JoinType.LEFT );
        root.fetch("pagamento", JoinType.LEFT);

        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);
    }

    @Test
    public void fazerJoinComOn() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = root.join("pagamento"); //problema com join
        joinPagamento.on(criteriaBuilder.equal(
                joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

        criteriaQuery.select(root);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertEquals(2, lista.size());
    }



    @Test
    public void fazerJoin(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz
//        Join<Pedido, Pagamento> join = root.join("pagamento"); //Erro no inner join
//        Join<Pedido, ItemPedido> joinItem = root.join("itens");
        criteriaQuery.select(root);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

//        Assert.assertTrue(lista.size() == 5);

        lista.forEach(arr -> System.out.println("ID: " + arr.getId() + ", Nome:" + arr.getCliente().getNome()));
    }

}
