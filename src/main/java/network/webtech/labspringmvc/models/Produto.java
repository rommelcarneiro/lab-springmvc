package network.webtech.labspringmvc.models;

import jakarta.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String decricao;

    private String marca;

    private double preco;

    // getters e setters
}