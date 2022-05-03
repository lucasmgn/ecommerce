package com.curso.ecommerce.mapeamentosavancado;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class PropriedadesTransienteTest extends EntityManagerTest {

    @Test
    public void validarPrimeiroNome() {
        Cliente cliente = entityManager.find(Cliente.class, 1);

        Assert.assertEquals("Lucas", cliente.getPrimeiroNome());
    }
}