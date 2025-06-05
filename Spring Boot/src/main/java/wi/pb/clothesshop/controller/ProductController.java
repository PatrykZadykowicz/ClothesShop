package wi.pb.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    @Qualifier("productServiceImpl")
    private ProductService productService;

    @GetMapping("/all")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @PostMapping("/insert")
    public Product insertProduct(@RequestBody Product product)
    {
        return productService.insertProduct(product);
    }

    @GetMapping("/find/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
    }

}
