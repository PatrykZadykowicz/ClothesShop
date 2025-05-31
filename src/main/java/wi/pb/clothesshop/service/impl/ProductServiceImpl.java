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

    @Override
    public Product getProduct(int id) {
        return productRepo.findById(id).get();
    }

    @Override
    public Product updateProduct(int id, Product product) {
        Product DBproduct = productRepo.findById(id).get();

        DBproduct.setName(product.getName());
        DBproduct.setPrice(product.getPrice());
        DBproduct.setCategory(product.getCategory());
        productRepo.save(DBproduct);

        return DBproduct;
    }

    @Override
    public Product deleteProduct(int id) {
        Product product = productRepo.findById(id).get();
        productRepo.deleteById(id);
        return product;
    }
}
