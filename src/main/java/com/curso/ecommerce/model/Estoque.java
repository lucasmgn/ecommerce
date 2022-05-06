

package com.curso.ecommerce.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter //Para gerar o getter de todos os atributos com a lib lombok
@Setter //Para gerar o setter de todos os atributos
@Entity
@Table(name = "estoque")
public class Estoque extends EntidadeBaseInteger{

    @OneToOne(optional = false)
    @JoinColumn(name="produto_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_estoque_produto"))

    private Produto produto;

    private Integer quantidade;

}
