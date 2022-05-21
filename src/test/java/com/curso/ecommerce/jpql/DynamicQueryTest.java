package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.dto.ProdutoDTO;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DynamicQueryTest extends EntityManagerTest {

    @Test
    public void executarConsultaDinamica() {
        Produto consultado = new Produto();
        consultado.setNome("Câmera GoPro");

        List<Produto> lista = pesquisar(consultado);

        Assert.assertFalse(lista.isEmpty());
        Assert.assertEquals("Câmera GoPro Hero 7", lista.get(0).getNome());
    }

    private List<Produto> pesquisar(Produto consultado) {
        StringBuilder jpql = new StringBuilder("select p from Produto p where 1 = 1");

        if (consultado.getNome() != null) {
            jpql.append(" and p.nome like concat('%', :nome, '%')");
        }

        if (consultado.getDescricao() != null) {
            jpql.append(" and p.descricao like concat('%', :descricao, '%')");
        }

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql.toString(), Produto.class);

        if (consultado.getNome() != null) {
            typedQuery.setParameter("nome", consultado.getNome());
        }

        if (consultado.getDescricao() != null) {
            typedQuery.setParameter("descricao", consultado.getDescricao());
        }

        return typedQuery.getResultList();
    }
}
