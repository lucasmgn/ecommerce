package com.curso.ecommerce.mapeamentosavancado;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Atributo;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class ElementCollectionTest extends EntityManagerTest {

    @Test
    public void aplicarTags() {
        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class,1);
        produto.setTags(Arrays.asList("e-book","livro-digital"));

        entityManager.getTransaction().commit();
        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertFalse(produtoVerificacao.getTags().isEmpty());

    }

    @Test
    public void aplicarAtributos() {
        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class,1);
        produto.setAtributos(Arrays.asList(new Atributo("Tela", "320x600")));

        entityManager.getTransaction().commit();
        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertFalse(produtoVerificacao.getAtributos().isEmpty());

    }

    @Test
    public void aplicarContato() {
        entityManager.getTransaction().begin();

       Cliente cliente = entityManager.find(Cliente.class, 1);
        cliente.setContatos(Collections.singletonMap("email", "lucasmagno@gmail.com"));

        entityManager.getTransaction().commit();
        entityManager.clear();

        Cliente clienteVerificacao = entityManager.find( Cliente.class, cliente.getId());
        Assert.assertEquals("lucasmagno@gmail.com", clienteVerificacao.getContatos().get("email"));

    }
}
