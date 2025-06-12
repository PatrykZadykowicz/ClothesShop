package wi.pb.clothesshop;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import wi.pb.clothesshop.dao.ProductDao;
import wi.pb.clothesshop.entity.Product;
import wi.pb.clothesshop.service.impl.ProductServiceImpl;

import java.util.*;

class ProductServiceImplTest {

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Product 1");
        product1.setPrice(100.0);
        product1.setCategory("Category 1");
        product1.setInventory(10);
        products.add(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Product 2");
        product2.setPrice(150.0);
        product2.setCategory("Category 2");
        product2.setInventory(20);
        products.add(product2);
    }

    @Test
    void testGetAllProducts() {
        // Mockowanie odpowiedzi z ProductDao
        when(productDao.getAll()).thenReturn(products);

        List<Product> productList = productService.getAllProducts();

        assertEquals(2, productList.size());  // Sprawdzamy, czy zwrócono dwa produkty
        verify(productDao, times(1)).getAll();  // Sprawdzamy, czy metoda getAll została wywołana
    }

    @Test
    void testInsertProduct() {
        Product product = new Product();
        product.setName("New Product");
        product.setPrice(200.0);
        product.setCategory("Category 3");
        product.setInventory(30);

        when(productDao.save(product)).thenReturn(product);

        Product savedProduct = productService.insertProduct(product);

        assertNotNull(savedProduct);
        assertEquals("New Product", savedProduct.getName());
        verify(productDao, times(1)).save(product);
    }

    @Test
    void testGetProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setPrice(100.0);
        product.setCategory("Category 1");
        product.setInventory(10);

        when(productDao.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProduct(1);

        assertNotNull(foundProduct);
        assertEquals("Product 1", foundProduct.getName());
        verify(productDao, times(1)).findById(1);
    }

    @Test
    void testGetProduct_NotFound() {
        when(productDao.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.getProduct(1);
        });

        assertEquals("Product not found", exception.getMessage());  // Sprawdzamy, czy rzucono odpowiedni wyjątek
    }


    @Test
    void testUpdateProduct_NotFound() {
        when(productDao.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1, new Product());
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Product to delete");
        product.setPrice(100.0);
        product.setCategory("Category 1");
        product.setInventory(10);

        when(productDao.findById(1)).thenReturn(Optional.of(product));

        productService.deleteProduct(1);

        verify(productDao, times(1)).delete(product);  // Sprawdzamy, czy metoda delete została wywołana
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productDao.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(1);
        });

        assertEquals("Product not found", exception.getMessage());  // Sprawdzamy, czy rzucono odpowiedni wyjątek
    }


}
