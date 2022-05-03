package com.curso.ecommerce.conhecendoentitymanager;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Produto;
import org.junit.Test;

public class CatchDePrimeiroNivel extends EntityManagerTest {

    @Test
    public void verificarCache(){
        Produto produto = entityManager.find(Produto.class,1);

        System.out.println(produto.getNome());
        System.out.println("----------------------------");

        Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());

        System.out.println(produtoResgatado.getNome());
    }
}
