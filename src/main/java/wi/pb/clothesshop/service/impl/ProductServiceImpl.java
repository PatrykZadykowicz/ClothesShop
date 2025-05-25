package wi.pb.clothesshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.repository.ProductRepo;
import wi.pb.clothesshop.service.ProductService;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts()
    {
        return productRepo.findAll();
    }

    @Override
    public Product insertProduct(Product product) {
        return productRepo.save(product);
    }
}
