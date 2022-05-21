package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

    @Test
    public void usarExpressaoDiferente() { //pegando pedidos entre 499 e 2398
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.notEqual(root.get("total"), new BigDecimal(499)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));

    }

    @Test
    public void usarBetweenData() { //pegando pedidos entre 499 e 2398
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        //tirando 5 dias e zerando as horas minutos e segundo para retornar todos de acordo
        criteriaQuery.where(criteriaBuilder.between(root.get("dataCriacao"),
                LocalDateTime.now().minusDays(5).withSecond(0).withMinute(0).withHour(0), LocalDateTime.now()));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));

    }

    @Test
    public void usarBetween(){ //pegando pedidos entre 499 e 2398
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.between(root.get("total"), new BigDecimal(499), new BigDecimal(2398)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));
    }

    @Test
    public void usarMaiorMenorExercicioDatas(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), LocalDateTime.now().minusDays(3)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenor(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); //Tabela de crtério

        Root<Produto> root = criteriaQuery.from((Produto.class)); //Tabela raiz

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("preco"), new BigDecimal(799)),
                criteriaBuilder.lessThanOrEqualTo(root.get("preco"), new BigDecimal(3500)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId() + ", Nome: " + arr.getNome() + ", Preço: " + arr.getPreco()));
    }

    @Test
    public void usarIsNull(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);


    }
}
