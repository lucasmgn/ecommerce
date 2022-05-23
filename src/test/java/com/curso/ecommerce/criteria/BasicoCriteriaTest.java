package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.dto.ProdutoDTO;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class BasicoCriteriaTest extends EntityManagerTest {

    @Test
    public void usarDistinct(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz
        root.join("itens");

        /*
        * Usando o distinct para não repetir valores expercificamente do 'id'
        * */
        criteriaQuery.select(root).distinct(true);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId()));
    }


    @Test
    public void OrdenarResultados(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); //Tabela de crtério

        Root<Cliente> root = criteriaQuery.from((Cliente.class)); //Tabela raiz

        /*
        * Ordenando 'id' dos clietnes em ordem decrescente
        * */
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Cliente> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId() + ", Nome:" + arr.getNome()));
    }


    @Test
    public void projetarResultadoDTO(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProdutoDTO> criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class); //Tabela de crtério

        Root<Produto> root = criteriaQuery.from((Produto.class)); //Tabela raiz

        criteriaQuery.select(criteriaBuilder.construct(ProdutoDTO.class, root.get("id"), root.get("nome")));
        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(criteriaQuery);
        List<ProdutoDTO> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId() + ", Nome:" + arr.getNome()));
    }

    @Test
    public void projetarResultadoTuple(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();//Tabela de crtério

        Root<Produto> root = criteriaQuery.from((Produto.class)); //Tabela raiz

        criteriaQuery.select(criteriaBuilder.tuple(root.get("id").alias("id"), root.get("nome").alias("nome")));

        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Tuple> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        
        lista.forEach(arr -> System.out.println("ID: " + arr.get("id") + ", Nome:" + arr.get("nome")));
    }

    @Test
    public void projetarResultado(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //Tabela de crtério

        Root<Produto> root = criteriaQuery.from((Produto.class)); //Tabela raiz

        criteriaQuery.multiselect(root.get("id"), root.get("nome")); //multi select para pegar mais de uma coluna diferente

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr[0] + ", Nome:" + arr[1]));
    }

    @Test
    public void retornarTodosOsProdutosExercicio(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); //Tabela de crtério

        Root<Produto> root = criteriaQuery.from((Produto.class)); //Tabela raiz

        criteriaQuery.select(root);

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> produtos = typedQuery.getResultList();

        Assert.assertFalse(produtos.isEmpty());
    }

    @Test
    public void selecionarUmAtributoParaRetornoNome(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root.get("cliente"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
        Cliente cliente = typedQuery.getSingleResult();

        Assert.assertEquals("Lucas Magno", cliente.getNome());
    }

    @Test
    public void selecionarUmAtributoParaRetornoTotal(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root.get("total"));

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

        TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(criteriaQuery);
        BigDecimal total = typedQuery.getSingleResult();

        Assert.assertEquals(new BigDecimal("2398.00"), total);
    }

    @Test
    public void buscarPorIdentificador(){
        //Montagem dinâmica e peformance boa
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

//        String jpql ="select p from Pedido p where p.id = 1";
        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        Pedido pedido = typedQuery.getSingleResult();

        Assert.assertNotNull(pedido);
    }
}
