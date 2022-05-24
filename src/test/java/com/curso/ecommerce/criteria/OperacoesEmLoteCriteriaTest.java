package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.dto.ProdutoDTO;
import com.curso.ecommerce.model.Categoria;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

public class OperacoesEmLoteCriteriaTest extends EntityManagerTest {

    @Test
    public void removerEmLoteExercicio() {
        entityManager.getTransaction().begin();

//        String jpql = "delete from Produto p where p.id between 5 and 12";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Produto> criteriaDelete = criteriaBuilder.createCriteriaDelete(Produto.class);
        Root<Produto> root = criteriaDelete.from(Produto.class);

        criteriaDelete.where(criteriaBuilder.between(root.get("id"), 5, 12));

        Query query = entityManager.createQuery(criteriaDelete);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }


    @Test
    public void OperacoesEmLoteCriteria(){
        entityManager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Produto> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Produto.class);
        Root<Produto> root = criteriaUpdate.from(Produto.class);

//        criteriaUpdate.set(root.get("preco"), criteriaBuilder.prod(root.get("preco"), new BigDecimal("1.1")));

        Subquery<Integer> subquery = criteriaUpdate.subquery(Integer.class);
        Root<Produto> subqueryRoot = subquery.correlate(root);
        Join<Produto, Categoria> joinCategoria = subqueryRoot.join("categorias");
        subquery.select(criteriaBuilder.literal(1));
        subquery.where(criteriaBuilder.equal(joinCategoria.get("id"), 2));

        criteriaUpdate.where(criteriaBuilder.exists(subquery));

        Query query = entityManager.createQuery(criteriaUpdate);
        query.executeUpdate();

        entityManager.getTransaction().commit();
    }

}
