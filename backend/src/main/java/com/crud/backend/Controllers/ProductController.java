package com.crud.backend.Controllers;

import com.crud.backend.Entities.Product;
import com.crud.backend.Services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    //Inyeccion de dependencias
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //GET/READ - listar todos los productos - Endpoint: GET /api/v1/products
    @GetMapping
    public List<Product> getAllProducts(){
        return this.productService.getAllProducts();     // El Controller solo llama al servicio y devuelve el resultado, spring se encarga de convertir la List<Product> a JSON.
    }

    //GET/READ - obtener un producto por ID - Endpoint: GET /api/v1/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        return this.productService.getProductById(id);
    }

    //POST/CREATE - crear un nuevo producto - Endpoint: POST /api/v1/products
    @PostMapping
    public ResponseEntity<Object> newProduct(@RequestBody Product product){    // El Controller recibe el objeto Product del cuerpo de la petición. Delega al Service, que maneja la lógica de validación y la respuesta HTTP (201, 400, 409, 500).
        return this.productService.newProduct(product);
    }

    //PUT/UPDATE - actualizar un producto existente - Endpoint: PUT /api/v1/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct){
        updatedProduct.setId(id);
        return this.productService.updateProduct(updatedProduct);
    }

    //DELETE - eliminar un producto existente - Endpoint: DELETE /api/v1/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id){     // @PathVariable extrae el ID de la URL  Delega la eliminación y el manejo de la respuesta (404, 200/204) al Service.
        return this.productService.deleteProduct(id);
    }
}
