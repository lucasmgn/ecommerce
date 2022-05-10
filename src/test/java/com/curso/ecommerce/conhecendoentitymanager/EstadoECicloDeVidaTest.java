package com.curso.ecommerce.conhecendoentitymanager;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Categoria;
import org.junit.Assert;
import org.junit.Test;

public class EstadoECicloDeVidaTest extends EntityManagerTest {

    @Test
    public void analisandoEstados(){
        Categoria categoriaNovo = new Categoria(); //Estado transiante ou novo
        categoriaNovo.setNome("Eletrônicos");

        Categoria categoriaGerenciadaMerge = entityManager.merge(categoriaNovo); //O retorno passa ser gerenciado

        Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1); //Categoria Gerenciada

        entityManager.remove(categoriaGerenciada); //Movendo para o estado remove
        entityManager.persist(categoriaGerenciada); //Voltando a ser gerenciada
        entityManager.detach(categoriaGerenciadaMerge); //Desanexando
    }
}
