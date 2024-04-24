package network.webtech.labspringmvc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import network.webtech.labspringmvc.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    
}
