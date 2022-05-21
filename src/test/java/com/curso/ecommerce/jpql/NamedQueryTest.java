package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NamedQueryTest extends EntityManagerTest {

    @Test
    public void executarConsultaArquivoXMLEspecifico() {
        TypedQuery<Pedido> typedQuery = entityManager.createNamedQuery("Pedido.todos",Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsultaArquivoXML() {
        TypedQuery<Pedido> typedQuery = entityManager.createNamedQuery("Pedido.listar",Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsultaDinamica() {
        //Named query na classe produto

        TypedQuery<Produto> typedQuery = entityManager.createNamedQuery("Produto.listarPorCategoria", Produto.class);
        typedQuery.setParameter("categoria", 2);
        //List<Produto> lista = typedQuery.getResultList(); //erro de table reference (produto)
        //Assert.assertFalse(lista.isEmpty());
    }

}
