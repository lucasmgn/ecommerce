package com.curso.ecommerce.mapeamentobasico;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.EnderecoEntregaPedido;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.StatusPedido;
import com.mysql.cj.xdevapi.Client;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MapeamentoObjetoEmbutidoTest extends EntityManagerTest{

    @Test
    public void analisarmapeamentoObjetoEmbutido(){
        Cliente cliente = entityManager.find(Cliente.class, 1);

        EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
        endereco.setCep("40430-390");
        endereco.setLogradouro("Rua 7 de abril");
        endereco.setNumero("9A");
        endereco.setBairro("Vila Ruy Barbosa");
        endereco.setCidade("Salvador");
        endereco.setEstado("BA");

        Pedido pedido = new Pedido();
//        pedido.setId(1); Utilizando o  IDENTITY
        pedido.setDataCriacao(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO);
        pedido.setTotal(new BigDecimal(1000));
        pedido.setEnderecoEntrega(endereco);
        pedido.setCliente(cliente);

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Pedido pedidoVerificacoa = entityManager.find(Pedido.class, pedido.getId());
        Assert.assertNotNull(pedidoVerificacoa);
        Assert.assertNotNull(pedidoVerificacoa.getEnderecoEntrega());
        Assert.assertNotNull(pedidoVerificacoa.getEnderecoEntrega().getCep());

    }
}
