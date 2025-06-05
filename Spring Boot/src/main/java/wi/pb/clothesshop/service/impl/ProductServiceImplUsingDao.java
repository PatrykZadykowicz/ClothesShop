package wi.pb.clothesshop.service.impl;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wi.pb.clothesshop.dao.ProductDao;
import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImplUsingDao implements ProductService {

    private ProductDao productDao;

    @Autowired
    public ProductServiceImplUsingDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    @Override
    public Product insertProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    public Product getProduct(int id) {
        return productDao.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product updateProduct(int id, Product product) {
        Product modifiedProduct = productDao.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        modifiedProduct.setName(product.getName());
        modifiedProduct.setPrice(product.getPrice());
        modifiedProduct.setCategory(product.getCategory());
        modifiedProduct.setInventory(product.getInventory());

        return productDao.update(modifiedProduct);
    }

    @Override
    public void deleteProduct(int id) {
        Product product = productDao.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productDao.delete(product);
    }

}
