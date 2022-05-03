package com.curso.ecommerce.model;

import com.curso.ecommerce.listener.GenericoListener;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EntityListeners({GenericoListener.class})
@Entity
@Table(name = "produto")
public class Produto extends EntidadeBaseInteger{

    @Column(name="data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name="data_ultima_atualizacao", insertable = false) //para n ter valor na hora da inserção
    private LocalDateTime dataUltimaAtualizacao;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    @Lob
    @Column(length = 65555)
    private byte[] foto;

    @ManyToMany
    @JoinTable(name = "produto_categoria", joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name= "categoria_id"))
    private List<Categoria> categorias;

    @OneToOne(mappedBy = "produto")
    private Estoque estoque;

    @ElementCollection //Para gerenciar a lista de tag
    @CollectionTable(name="produto_tag", joinColumns = @JoinColumn(name = "produto_id")) //Customizar a tabela
    @Column(name = "tag") //coluna para guardar a tag do produto
    private  List<String> tags;

    @ElementCollection
    @CollectionTable(name="produto_atributo", joinColumns = @JoinColumn(name = "produto_id"))
    private List<Atributo> atributos;

}
