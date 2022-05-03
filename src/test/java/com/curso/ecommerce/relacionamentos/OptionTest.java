package com.curso.ecommerce.relacionamentos;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import org.junit.Test;

public class OptionTest extends EntityManagerTest {

    @Test
    public void verificarComportamento(){
        Pedido pedido = entityManager.find(Pedido.class, 1);

    }

}
