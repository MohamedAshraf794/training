package com.integrant.trainingtask.contorller;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.models.Product;
import com.integrant.trainingtask.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/products")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping()
    public Product addProduct(@RequestBody Product product){
        return this.productService.addProduct(product);
    }
    @GetMapping("/search")
    public Page<Product> searchProducts(@RequestParam String name,@RequestParam int index,@RequestParam int pageSize){
        return this.productService.searchProductByName(name,index,pageSize);
    }
    @PutMapping()
    public Product updateProduct(@RequestBody Product product){
        return this.productService.updateProduct(product);
    }
    @PutMapping("/update-stock")
    public Product addStock(@RequestBody ProductQuantity productQuantity){
        return this.productService.addStock(productQuantity);
    }
    @DeleteMapping("/{id}")
    public Product deactivateProduct(@PathVariable int id){
        return this.productService.deactivateProduct(id);
    }
}
