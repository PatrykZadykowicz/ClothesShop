package wi.pb.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @GetMapping("/all")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/insert")
    public Product insertProduct(@RequestBody Product product)
    {
        try {
            return productService.insertProduct(product);
        }
        catch (Exception e) { e.printStackTrace(); return null;}
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @GetMapping("/find/{id}")
    public Product getProduct(@PathVariable int id) {
        try {
            return productService.getProduct(id);
        }
        catch (Exception e) { e.printStackTrace(); return null;}
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        try {
            return productService.updateProduct(id, product);
        }
        catch (Exception e) { e.printStackTrace(); return null; }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

}
