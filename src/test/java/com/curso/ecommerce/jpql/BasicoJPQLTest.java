package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.dto.ProdutoDTO;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Pedido;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void usarDistinct(){
        String jpql = "select distinct p from Pedido p join p.itens i join i.produto pro where pro.id in (1, 2, 3, 4)";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql,Pedido.class);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        System.out.println(lista.size());

        lista.forEach(arr -> System.out.println(arr.getId() + ", "+ arr.getCliente().getNome()));
    }

    @Test
    public void ordenarResultados(){
        String jpql = "select c from Cliente c order by c.nome desc"; //asc e desc

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql,Cliente.class);
        List<Cliente> produtos = typedQuery.getResultList();

        Assert.assertFalse(produtos.isEmpty());

        produtos.forEach(arr -> System.out.println(arr.getId() + "-" + arr.getNome()));
    }

    @Test
    public void projetoDTO(){
        String jpql = "select new com.curso.ecommerce.dto.ProdutoDTO(id, nome) from Produto";

        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
        List<ProdutoDTO> produtos = typedQuery.getResultList();

        Assert.assertFalse(produtos.isEmpty());

        produtos.forEach(arr -> System.out.println(arr.getId() + ", " + arr.getNome()));
    }

    @Test
    public void projetarResultado(){
        String jpql = "select id, nome from Produto";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> list = typedQuery.getResultList();

        Assert.assertTrue(list.get(0).length == 2);

        list.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void selecionarUmAtributoParaRetorno(){
        String jpql = "select p.nome from Produto p";

        TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
        List<String> produtos = typedQuery.getResultList();

        Assert.assertTrue(String.class.equals(produtos.get(0).getClass()));

        String jpqlCliente = "select p.cliente from Pedido p";
        TypedQuery<Cliente> typedQuery1 = entityManager.createQuery(jpqlCliente, Cliente.class);
        List<Cliente> listaClientes = typedQuery1.getResultList();
        Assert.assertTrue(Cliente.class.equals(listaClientes.get(0).getClass()));

    }

    @Test
    public void buscarPorIdentificador(){
        //Java Persistence Query Language - JPQL

        //JPQL - select p from Pedido p where p.id = 1
//        entityManager.find(Pedido.class, 1);

        TypedQuery<Pedido> typedQuery =
                entityManager.createQuery(" select p from Pedido p where p.id = 1", Pedido.class);

        //você precisa garantir que retornará apenas um resultado para o getSingleResult
        Pedido pedido = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido);

//        List<Pedido> pedidos = typedQuery.getResultList(); pode retornar vários valores
//        Assert.assertFalse(pedidos.isEmpty());
    }


    @Test
    public void mostraDifeencaQueries(){
        String jpql = "select p from Pedido p where p.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery( jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        Assert.assertNotNull(pedido1);

        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        Assert.assertNotNull(pedido2);
    }
}
