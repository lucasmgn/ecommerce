package com.curso.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter //Para gerar o getter de todos os atributos com a lib lombok
@Setter //Para gerar o setter de todos os atributos

@Embeddable //Ela é imbutivel em outra classe
public class EnderecoEntregaPedido {

    @Column(length = 9)
    private String cep;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 50)
    private String complemento;

    @Column(length = 50)
    private String bairro;

    @Column(length = 50)
    private String cidade;

    @Column(length = 2)
    private String estado;
}
