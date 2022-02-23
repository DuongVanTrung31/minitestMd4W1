package project.repository;



import project.model.Product;

import java.util.List;


public interface IProductRepository {
    List<Product> findAll();

    Product saveProduct(Product product);

    Product deleteProduct(Long id);

    Product findProductById(Long id);

    List<Product> searchProductsByName(String name);
}
