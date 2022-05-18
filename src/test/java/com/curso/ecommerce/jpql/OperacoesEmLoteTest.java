package com.curso.ecommerce.jpql;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.Query;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class OperacoesEmLoteTest extends EntityManagerTest {

    private static final int LIMITE_INSERCOES = 4;

    @Test
    public void atualizarEmLote(){
        entityManager.getTransaction().begin();

        String jpql = "update Produto p set p.preco = p.preco + (p.preco * :porcentagem) " +
                " where exists (select 1 from p.categorias c2 where c2.id = :categoria)";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("categoria", 2);
        query.setParameter("porcentagem", new BigDecimal(0.1));
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

    @Test
    public void inserirEmLote() throws IOException {
        InputStream in = OperacoesEmLoteTest.class.getClassLoader().getResourceAsStream("produtos/importar.txt");

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        entityManager.getTransaction().begin();

        int contInsercoes = 0;

        for (String linha : reader.lines().collect(Collectors.toList())){
            if(linha.isBlank()){
                continue;
            }

            String [] produtoColuna = linha.split(";");
            Produto produto = new Produto();
            produto.setNome(produtoColuna[0]);
            produto.setDescricao(produtoColuna[1]);
            produto.setPreco(new BigDecimal(produtoColuna[2]));
            produto.setDataCriacao(LocalDateTime.now());

            entityManager.persist(produto);

            if(++contInsercoes == LIMITE_INSERCOES){
                //quando chegar no limete de inserções será enviado ao bd e será limpa a memória do entitymanager
                entityManager.flush();
                entityManager.clear();

                contInsercoes = 0;

                System.out.println("-------------------------------------");
            }
        }
        entityManager.getTransaction().commit();
    }
}
