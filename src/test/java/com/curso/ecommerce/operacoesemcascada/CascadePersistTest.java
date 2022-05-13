package com.curso.ecommerce.operacoesemcascada;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CascadePersistTest extends EntityManagerTest {

     //@Test
    public void persistirProdutoComCategoria() {
        Produto produto = new Produto();
        produto.setDataCriacao(LocalDateTime.now());
        produto.setPreco(BigDecimal.TEN);
        produto.setNome("Fones de Ouvido");
        produto.setDescricao("A melhor qualidade de som");

        Categoria categoria = new Categoria();
        categoria.setNome("Áudio");

        produto.setCategorias(Arrays.asList(categoria)); // CascadeType.PERSIST

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertNotNull(categoriaVerificacao);
    }

   // @Test
    public void persistirPedidoComItens(){
        Cliente cliente = entityManager.find(Cliente.class,1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(produto.getPreco());
        pedido.setStatus(StatusPedido.AGUARDANDO);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1);
        itemPedido.setPrecoProduto(produto.getPreco());

        pedido.setItens(Arrays.asList(itemPedido)); // cascadeType.PERSIST

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacao);
        Assert.assertFalse(pedidoVerificacao.getItens().isEmpty());

    }

   // @Test
    public void persistirPedidoComCliente(){
        Cliente cliente = new Cliente();
        cliente.setNome("Isadora Levi");
        cliente.setSexo(SexoCliente.FEMENINO);
        cliente.setCpf("011.569.663-57");
        cliente.setDataNascimento(LocalDate.of(2001, 5, 17));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setStatus(StatusPedido.AGUARDANDO);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido); //cascadeType.Persist
        entityManager.getTransaction().commit();

        entityManager.clear();

       Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacao);

    }

     @Test
    public void persistirItemPedidoComPedido(){
        Cliente cliente = entityManager.find(Cliente.class,1);
        Produto produto = entityManager.find(Produto.class, 1);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setTotal(produto.getPreco());
        pedido.setStatus(StatusPedido.AGUARDANDO);

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(new ItemPedidoId());
        itemPedido.setPedido(pedido); //não é necessário o cascade pq possui o MapsId
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1);
        itemPedido.setPrecoProduto(produto.getPreco());

        pedido.setItens(Arrays.asList(itemPedido)); // cascadeType.PERSIST

        entityManager.getTransaction().begin();
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

         ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
        Assert.assertNotNull(itemPedidoVerificacao);

    }
}
