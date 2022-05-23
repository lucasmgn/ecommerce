package com.curso.ecommerce.criteria;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Cliente;
import com.curso.ecommerce.model.Pedido;
import com.curso.ecommerce.model.Produto;
import com.curso.ecommerce.model.StatusPedido;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

    @Test
    public void usarExpressaoINComEntidades() {
        /*
         *Passando uma lista de clientes para fazer o IN direto da entidade
         * Criando os objetos clientes / buscando no bd
         * */
        Cliente cliente01 = entityManager.find(Cliente.class, 1);
        Cliente cliente02 = entityManager.find(Cliente.class, 2);

        /*
         * Para retornar os identificadores (id)
         * List<Cliente> colecaoCliente = Arrays.asList(cliente01.getId(), cliente02.getId());
         * */
        List<Cliente> colecaoCliente = Arrays.asList(cliente01, cliente02);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);

        Root<Pedido> root = criteriaQuery.from((Pedido.class));

        criteriaQuery.select(root);

        /*
         *Retornando os Clientes com Id respectivo ao da 'colecaoCliente'
         *
         * Para retornar o id com os valores
         * criteriaQuery.where(root.get("cliente").get("id").in(colecaoCliente));
         * */
        criteriaQuery.where(root.get("cliente").in(colecaoCliente));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        Assert.assertFalse(lista.isEmpty());

//        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));

    }


    @Test
    public void usarExpressaoIN() {
        /*
        *Identificadores de pedido, preciso devolver uma lista de quatro pedidos com todos eles
        * preenchidos
        * */
        List<Integer> colecaoIds = Arrays.asList(1, 3, 4, 6);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);

        Root<Pedido> root = criteriaQuery.from((Pedido.class));

        criteriaQuery.select(root);

        /*
         *Retornando os IDs com valores respectivos ao da 'colecaoIds'
         * */
        criteriaQuery.where(root.get("id").in(colecaoIds));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));

    }

    @Test
    public void usarExpressoesCase(){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        /*
        * Quando o pedido for 'PAGO' mostrará a 'String' "Foi pago", quando for 'AGUARDANDO' mostrará a
        * 'String' '"Está aguardando pagamento"'.
        * Caso seja usado um metaModel eu teria que passar em uppercase o atributo String, 'Pedido_STATUS' no root.get()
        * , e teria que transformar 'StatusPedido.PAGO.toString' para poder entregar o que eu desejo no console.
        * */
        criteriaQuery.multiselect(
                root.get("id"),
                criteriaBuilder.selectCase(
                        root.get("status"))
                        .when(StatusPedido.PAGO, "Foi pago.")
                        .when(StatusPedido.AGUARDANDO, "Está aguardando pagamento.")
                        .otherwise(root.get("status"))

//                criteriaBuilder.selectCase(root.get("pagamento").type().as(String.class))
//                        .when("boleto", "Foi pago com boleto.")
//                        .when("cartao", "Foi pago com cartão")
//                        .otherwise("Não identificado")
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Object[]> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr[0] + ", Status: " + arr[1]));
    }

    @Test
    public void usarExpressaoDiferente() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        /*
        * Filtrando todos valores da coluna total e condiconando eles retornarem
        * apenas valores maiores que 499
        * */
        criteriaQuery.where(criteriaBuilder.notEqual(root.get("total"), new BigDecimal(499)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));

    }

    @Test
    public void usarBetweenData() { //pegando pedidos entre 499 e 2398
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        /*
        * tirando 5 dias e zerando as horas minutos e segundo para retornar todos de acordo
        * */
        criteriaQuery.where(criteriaBuilder.between(root.get("dataCriacao"),
                LocalDateTime.now().minusDays(5).withSecond(0).withMinute(0).withHour(0), LocalDateTime.now()));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));

    }

    @Test
    public void usarBetween(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        /*
         * Filtrando todos valores da coluna total e condiconando eles retornarem
         * apenas valores entre 499 e 2398, incluindo eles dois
         * */
        criteriaQuery.where(criteriaBuilder.between(root.get("total"), new BigDecimal(499), new BigDecimal(2398)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
        lista.forEach(arr -> System.out.println("ID: " + arr.getId() +  " Total: " + arr.getTotal()));
    }

    @Test
    public void usarMaiorMenorExercicioDatas(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class); //Tabela de crtério

        Root<Pedido> root = criteriaQuery.from((Pedido.class)); //Tabela raiz

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get("dataCriacao"), LocalDateTime.now().minusDays(3)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenor(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class); //Tabela de crtério

        Root<Produto> root = criteriaQuery.from((Produto.class)); //Tabela raiz

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.greaterThan(root.get("preco"), new BigDecimal(799)),
                criteriaBuilder.lessThanOrEqualTo(root.get("preco"), new BigDecimal(3500)));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Produto> lista = typedQuery.getResultList();

        Assert.assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println("ID: " + arr.getId() + ", Nome: " + arr.getNome() + ", Preço: " + arr.getPreco()));
    }

    @Test
    public void usarIsNull(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> root = criteriaQuery.from(Produto.class);

        criteriaQuery.select(root);


    }
}
