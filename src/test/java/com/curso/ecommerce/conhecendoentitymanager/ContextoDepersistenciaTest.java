package com.curso.ecommerce.conhecendoentitymanager;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Produto;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContextoDepersistenciaTest extends EntityManagerTest {

    @Test
    public void usarContextoDepersistencia() {

        entityManager.getTransaction().begin();

        Produto produto = entityManager.find(Produto.class,1);
        produto.setPreco(new BigDecimal(100.0));

        Produto produto2 = new Produto();
        produto2.setNome("Caneca Star Wars Personalizada com o Yodinha");
        produto2.setPreco(new BigDecimal(63.85));
        produto2.setDescricao("Caneca temática de Star Wars, com imagem do pequeno Yoda");
        produto2.setDataCriacao(LocalDateTime.now());
        entityManager.persist(produto2);

        Produto produto3 = new Produto();
        produto3.setNome("Capa de celular (Preta)");
        produto3.setPreco(new BigDecimal(35.63));
        produto3.setDescricao("Para modelos de Iphone 7 e 8, Composição: cilicone");
        produto3.setDataCriacao(LocalDateTime.now());

        produto3 = entityManager.merge(produto3);

        entityManager.flush();

        produto3.setDescricao("Alterando Descrição");

        entityManager.getTransaction().commit();

    }
}
