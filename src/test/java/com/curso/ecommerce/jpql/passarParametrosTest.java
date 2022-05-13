package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.NotaFiscal;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.StatusPagamento;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class passarParametrosTest extends EntityManagerTest {

    @Test
    public void passarParametroDate(){
        String jpql = "select nf from NotaFiscal nf where  nf.dataEmissao <= ?1";

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(jpql, NotaFiscal.class);
        typedQuery.setParameter(1,new Date(), TemporalType.TIMESTAMP);

        List<NotaFiscal> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 1);
    }

    @Test
    public void passarParametro(){
        String jpql = "select p from Pedido p join p.pagamento pag where p.id = :pedidoId and pag.status = ?1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("pedidoId",2);
        typedQuery.setParameter(1, StatusPagamento.PROCESSANDO);

        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertTrue(lista.size() == 1);
    }


}
