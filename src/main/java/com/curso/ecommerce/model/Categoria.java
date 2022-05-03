

package com.curso.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter //Para gerar o getter de todos os atributos com a lib lombok
@Setter //Para gerar o setter de todos os atributos
@Entity
@Table(name = "categoria")
public class Categoria extends EntidadeBaseInteger{

    private String nome;

    @ManyToOne
    @JoinColumn(name="categoria_pai_id")
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    private List<Categoria> categorias;

    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos;

}
