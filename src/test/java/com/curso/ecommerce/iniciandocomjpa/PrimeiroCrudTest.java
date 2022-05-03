package com.curso.ecommerce.iniciandocomjpa;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Cliente;
import org.junit.Assert;
import org.junit.Test;

public class PrimeiroCrudTest extends EntityManagerTest {

    //Inserir
    @Test
    public void inserirObjetoCliente(){
        Cliente cliente = new Cliente();

        //cliente.setId(3);
        cliente.setNome("Isaac Neves");

        entityManager.getTransaction().begin();

        entityManager.merge(cliente);

        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacaoInserir = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNotNull(clienteVerificacaoInserir);
    }

    //Buscar
    @Test
    public void buscandoObjetoCliente(){
        Cliente cliente = entityManager.find(Cliente.class, 1);

        Assert.assertNotNull(cliente);
        Assert.assertEquals("Lucas Magno", cliente.getNome());
    }

    //Atualizar
    @Test
    public void atualizarObjetoCliente(){
        Cliente cliente = new Cliente();

       // cliente.setId(1);
        cliente.setNome("Lucas Magno Peixoto Lima");

        entityManager.getTransaction().begin();
        entityManager.merge(cliente);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clienteVerificacaoAtualizar = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertEquals("Lucas Magno Peixoto Lima", clienteVerificacaoAtualizar.getNome());
    }

    //Remover
    @Test
    public void removerObjetoCliente(){
        Cliente cliente = entityManager.find(Cliente.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();

        //entityManager.clear();

        Cliente clienteVerificacaoRemover = entityManager.find(Cliente.class, cliente.getId());
        Assert.assertNull(clienteVerificacaoRemover);
    }
}
