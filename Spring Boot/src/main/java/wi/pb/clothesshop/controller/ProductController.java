package wi.pb.clothesshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/insert")
    public ResponseEntity<Product> insertProduct(@RequestBody Product product)
    {
        try {
            Product newProduct = productService.insertProduct(product);
            return ResponseEntity.ok(product);
        }
        catch (Exception e) { return ResponseEntity.noContent().build(); }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @GetMapping("/find/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        try {
            Product product = productService.getProduct(id);
            return ResponseEntity.ok(product);
        }
        catch (Exception e) { return ResponseEntity.noContent().build(); }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(product);
        }
        catch (Exception e) {  return ResponseEntity.noContent().build(); }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        }
        catch (Exception e) { return ResponseEntity.status(400).body("Product not found."); }
    }

}
