package com.curso.ecommerce.iniciandocomjpa;

import com.curso.ecommerce.EntityManagerTest;
import com.curso.ecommerce.model.Produto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OperacoesComTransacaoTest extends EntityManagerTest {

    @Test
    public void impedirOperacaoComBancoDeDados(){
        Produto produto = entityManager.find(Produto.class, 1);
        entityManager.detach(produto); //O método detach impede a operação com o Banco de dados ocorra


        entityManager.getTransaction().begin();
        produto.setNome("Kindle PaperWhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals("Kindle", produtoVerificacao.getNome());
    }

    @Test
    public void MostrarDiferencaPersistMerge(){
        Produto produtoPersiste = new Produto();

        //produtoPersiste.setId(5);
        produtoPersiste.setNome("Smartphone One Plus");
        produtoPersiste.setDescricao("O processador mais rápido.");
        produtoPersiste.setPreco(new BigDecimal(2000));

        entityManager.getTransaction().begin();

        entityManager.persist(produtoPersiste); //O método persist só serve para persistir
        produtoPersiste.setNome("Smartphone Two Plus");
        entityManager.getTransaction().commit();

        entityManager.clear(); //Limpar a entidade que está na memória

        Produto produtoVerificacaoPersist = entityManager.find(Produto.class, produtoPersiste.getId());
        Assert.assertNotNull(produtoVerificacaoPersist);


        Produto produtoMerge = new Produto();

        //produtoMerge.setId(6);
        produtoMerge.setNome("Notebook Dell");
        produtoMerge.setDescricao("O melhor da categoria.");
        produtoMerge.setPreco(new BigDecimal(3000));

        entityManager.getTransaction().begin();

        produtoMerge = entityManager.merge(produtoMerge); //O método merge faz uma cópia e manda para o entityManager, para pegar a cópia deve se fazer dessa forma
        produtoMerge.setNome("Notebook Dell 2");
        entityManager.getTransaction().commit();

        entityManager.clear(); //Limpar a entidade que está na memória

        Produto produtoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
        Assert.assertNotNull(produtoVerificacaoMerge);
    }

    @Test
    public void inserirObjetoComMerge(){
        Produto produto = new Produto();

        //produto.setId(4);
        produto.setNome("Microfone Rode Videmic");
        produto.setDescricao("A melhor qualidade de som");
        produto.setPreco(new BigDecimal(1000));

        entityManager.getTransaction().begin();

        entityManager.merge(produto); //O método merge pode ser utilizado tanto para atualizar quanto para inserir

        entityManager.getTransaction().commit();

        entityManager.clear(); //Limpar a entidade que está na memória

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
    }

    @Test
    public void atualizarObjetoGerenciado(){
        //Querendo mudar algumas colunas ou apenas uma e mantendo o valor das restantes
        Produto produto = entityManager.find(Produto.class, 1);

        entityManager.getTransaction().begin();
        produto.setNome("Kindle PaperWhite 2ª Geração");
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertEquals("Kindle PaperWhite 2ª Geração", produtoVerificacao.getNome());
    }

    @Test
    public void atualizarObjeto(){
        Produto produto = new Produto();

        //É preciso que todos os atributos sejam preenchidos se não o entityManager vai colocar como null
        //produto.setId(1); //O único que não pode ser alterado
        produto.setNome("Kindle PaperWhite");
        produto.setDescricao("Conheça o novo Kindle");
        produto.setPreco(new BigDecimal(599));

        entityManager.getTransaction().begin();
        entityManager.merge(produto); //Para fazer alteração no objeto
        entityManager.getTransaction().commit();

        entityManager.clear();

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
        Assert.assertEquals("Kindle PaperWhite", produtoVerificacao.getNome());
    }

    @Test
    public void removerObjeto(){
        Produto produto = entityManager.find(Produto.class, 3);

        entityManager.getTransaction().begin();
        entityManager.remove(produto);//Para remover um registro
        entityManager.getTransaction().commit();

        entityManager.clear(); //não é necessário na inseção para operação de remoção

        Produto produtoVerificacao = entityManager.find(Produto.class, 3);
        Assert.assertNull(produtoVerificacao);
    }

    @Test
    public void inserirOPrimeiroObjeto(){
        Produto produto = new Produto();

        //produto.setId(2);
        produto.setNome("Câmera Canon");
        produto.setDescricao("A melhor definição para suas fotos");
        produto.setPreco(new BigDecimal(5000));

        entityManager.getTransaction().begin();

        entityManager.persist(produto); //Quando chamado a entidade fica na memória do entityManager

        //entityManager.flush(); // Força jogar a entidade no BD

        entityManager.getTransaction().commit();

        entityManager.clear(); //Limpar a entidade que está na memória

        Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
        Assert.assertNotNull(produtoVerificacao);
    }


    @Test
    public void abrirFecharTransacao(){
        Produto produto = new Produto(); //Somente para o metódos não mostrarem erros

        //Marco de inicio a transação, numa aplicação profissional isso é automatizado
        entityManager.getTransaction().begin();

        //Persistir o objeto
//        entityManager.persist(produto);
//
//        entityManager.merge(produto);
//
//        entityManager.remove(produto);

        //Final da transação, numa aplicação profissional isso é automatizado
        entityManager.getTransaction().commit();
    }
}
