package network.webtech.labspringmvc.controllers;

import org.springframework.web.bind.annotation.RestController;

import network.webtech.labspringmvc.models.Produto;
import network.webtech.labspringmvc.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    @PostMapping
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    // Implemente métodos para atualizar, deletar e buscar produtos...
}
