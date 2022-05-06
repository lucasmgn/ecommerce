

package com.curso.ecommerce.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter //Para gerar o getter de todos os atributos com a lib lombok
@Setter //Para gerar o setter de todos os atributos
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //Para gerar o Equals e o HashCode
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

    @EmbeddedId//o id incorporado
    private ItemPedidoId id;

    @MapsId("pedidoId")
    @ManyToOne(optional = false)
    @JoinColumn(name="pedido_id",nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"))
    private Pedido pedido;

    @MapsId("produtoId")
    @ManyToOne(optional = false)
    @JoinColumn(name="produto_id",nullable = false,
            foreignKey = @ForeignKey(name = "fk_item_pedido_produto"))
    private Produto produto;

    @Column(name="preco_produto")
    private BigDecimal precoProduto;

    private Integer quantidade;

}
