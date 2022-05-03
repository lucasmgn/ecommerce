package com.curso.ecommerce.relacionamentos;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Categoria;
import com.curso.ecommerce.model.Pedido;
import org.junit.Assert;
import org.junit.Test;

public class EagerLazyTest extends EntityManagerTest {

    @Test
    public void verificarComportamento(){
        //Eager é usado em entidades que não são coleções, carregando assim que roda a aplicação
        // e Lazy são utilizados em entidades que são coleções,
        // carregado quando existe algum comando que envolva a coleção
        Pedido pedido = entityManager.find(Pedido.class, 1);

        pedido.getCliente();
        pedido.getItens().isEmpty();
    }

}
