package com.curso.ecommerce.util;

import com.curso.ecommerce.model.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class IniciarUnidadeDePersistencia {

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.
                createEntityManagerFactory("Ecommerce-PU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //Faça os testes aqui.

        Produto produto = entityManager.find(Produto.class, 1);
        System.out.println(produto.getNome());

        //Fim do bloco de testes

        entityManager.close();
        entityManagerFactory.close();

    }
}
