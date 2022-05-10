package com.curso.ecommerce.relacionamentos;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Categoria;
import org.junit.Assert;
import org.junit.Test;

public class AutoRelacionamentoTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamento(){
    Categoria categoriaPai = new Categoria();
    categoriaPai.setNome("Economia");

        Categoria categoria = new Categoria();
        categoria.setNome("Livros");
        categoria.setCategoriaPai(categoriaPai);

        entityManager.getTransaction().begin();
        entityManager.persist(categoriaPai);
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        entityManager.clear();

        Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        Assert.assertNotNull(categoriaVerificacao.getCategoriaPai());

    }

}
