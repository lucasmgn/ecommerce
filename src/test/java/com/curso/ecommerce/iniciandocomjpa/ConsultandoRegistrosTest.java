package com.curso.ecommerce.iniciandocomjpa;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

public class ConsultandoRegistrosTest extends EntityManagerTest {

    @Test
    public void buscarIdentificador(){
//        Produto produto = entityManager.find(Produto.class, 1);
// Usando o getReference e utilizando o primeiro Assert não mostra o sql no console pq precisa da propriedade nome,
// então usamos o segundo Assert para mostrar o sql
        Produto produto = entityManager.getReference(Produto.class, 1);

        Assert.assertNotNull(produto); //não pode chegar como null, pq ele realmente existe
        Assert.assertEquals("Kindle", produto.getNome()); //Esperando palavra Kindle do produto.getNome()
    }

    @Test
    public void AtualizarReferencia(){
        //O nome do ID:1 é Kindle, estou mudando para Microfone Samson
        Produto produto = entityManager.find(Produto.class, 1);
        produto.setNome("Microfone Samson");

        //Estou reiniciando ao valor original, para ID:1 = Kindle
        entityManager.refresh(produto);

        Assert.assertEquals("Kindle", produto.getNome());
    }

}
