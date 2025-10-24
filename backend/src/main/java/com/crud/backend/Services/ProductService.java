package com.crud.backend.Services;

import com.crud.backend.Entities.Product;
import com.crud.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    //GET/READ - listar todos los productos
    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    //GET/READ - obtener un producto por ID
    public ResponseEntity<Object> getProductById(Long id){
        Map<String, Object> responseMap = new HashMap<>();

        Optional<Product> res = productRepository.findById(id);

        if(!res.isPresent()){
            responseMap.put("error", true);
            responseMap.put("message", "Producto no encontrado.");
            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.NOT_FOUND     // 404 Not Found: el recurso no existe
            );
        }
        responseMap.put("error", false);
        responseMap.put("data", res.get());
        return new ResponseEntity<>(
                responseMap,
                HttpStatus.OK     // 200 OK: la solicitud fue exitosa
        );
    }

    //POST/CREATE - crear un nuevo producto
    public ResponseEntity<Object> newProduct(Product product){
        Map<String, Object> responseMap = new HashMap<>();     // 1. Prepara el contenedor de la respuesta y errores
        // --- REGLA DE NEGOCIO 1: Verificación de Existencia(Nombre unico)
        Optional<Product> res = productRepository.findProductByName(product.getName());    // Buscar si ya existe un producto con el mismo nombre en la base de datos

        if (res.isPresent()){      // si ya existe un producto con ese nombre
            responseMap.put("error", true);     //la validacion falla
            responseMap.put("message", "Ya tienes un producto con ese nombre!");

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.CONFLICT    //409 Conflict: el recurso ya existe
            );
        }
        //--- REGLA DE NEGOCIO 2: Validación de Datos (Precio no negativo)
        if (product.getPrice()<=0){
            responseMap.put("error", true);     //la validacion falla
            responseMap.put("message", "El valor del producto no puede ser 0 ni negativo!");

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.BAD_REQUEST    //400 Bad request: los datos enviados no son validos
            );
        }
        // --- LÓGICA FINAL: Si todas las validaciones pasan
        try{
            productRepository.save(product);   // Le pide al Repositorio que guarde el objeto Producto
            responseMap.put("error", false);     //la validacion es exitosa
            responseMap.put("message", "Producto creado con exito!");
            responseMap.put("data", product);

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.CREATED    //201 Created: el recurso fue creado exitosamente
            );
        }
        // Manejo de errores inesperados (ej: problemas de conexión a la DB)
        catch (Exception e){
            responseMap.put("error", true);     // la validacion falla
            responseMap.put("message", "Error interno al guardar el producto.");

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.INTERNAL_SERVER_ERROR    //500 Internal Server Error: error inesperado en el servidor
            );
        }
    }

    //PUT/UPDATE - actualizar un producto existente
    public ResponseEntity<Object> updateProduct(Product updatedProduct) {
        Map<String, Object> responseMap = new HashMap<>();

        // --- REGLA DE NEGOCIO 1:  Verificar Existencia (por ID)
        Optional<Product> res = productRepository.findById(updatedProduct.getId());

        if (!res.isPresent()) {    // si no existe un producto con ese id
            responseMap.put("error", true);     //la validacion falla
            responseMap.put("message", "prducto no encontrado.");

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.NOT_FOUND     //404 Not Found: el recurso no existe
            );
        }
        Product existingProduct = res.get();

        // --- REGLA DE NEGOCIO 2:  Nombre Único (si se cambia el nombre)
        Optional<Product> productNameUsed = productRepository.findProductByName(updatedProduct.getName());  // Buscar si ya existe un producto con el mismo nombre en la base de datos
        if (productNameUsed.isPresent() && !productNameUsed.get().getId().equals(existingProduct.getId())) {     //Existe otro producto que ya usa el nuevo nombre que estamos intentando asignar?
            responseMap.put("error", true);     //la validacion falla
            responseMap.put("message", "El nuevo nombre ya pertenece a otro producto!");

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.CONFLICT    //409 Conflict: el recurso ya existe
            );
        }
        // --- REGLA DE NEGOCIO 3:  Validación de Datos (ej: Precio no negativo)
        if (updatedProduct.getPrice() <= 0) {
            responseMap.put("error", true);     //la validacion falla
            responseMap.put("message", "El valor del producto actualizado no puede ser 0 ni negativo!");

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.BAD_REQUEST    //400 Bad request: los datos enviados no son validos
            );
        }

        // --- LÓGICA FINAL: Si todas las validaciones pasan  - aplicar y guardar
        try { // se actualizan solo los campos permitidos, acsa estaran todos, y solo se actualizaran si se modificaron.
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setPublicationDate(updatedProduct.getPublicationDate());

            productRepository.save(existingProduct);        // Le pide al Repositorio que guarde el objeto Producto

            responseMap.put("error", false);
            responseMap.put("message", "Producto actualizado exitosamente.");
            responseMap.put("data", existingProduct);
            return new ResponseEntity<>(responseMap, HttpStatus.OK);       // 200 OK: la solicitud fue exitosa
        }
        // Manejo de errores inesperados (ej: problemas de conexión a la DB)
        catch (Exception e) {
            responseMap.put("error", true);     // la validacion falla
            responseMap.put("message", "Error interno al actualizar el producto.");

            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.INTERNAL_SERVER_ERROR    //500 Internal Server Error: error inesperado en el servidor
            );
        }
    }

    //DELETE - eliminar un producto existente
    public ResponseEntity<Object> deleteProduct(Long id){
        Map<String, Object> responseMap = new HashMap<>();

        // ---REGLA DE NEGOCIO 1: Verificar Existencia (por ID)
        Optional<Product> res = productRepository.findById(id);

        if(!res.isPresent()){
            responseMap.put("error", true);
            responseMap.put("message", "Producto no encontrado para eliminar.");
            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.NOT_FOUND     // 404 Not Found: el recurso no existe
            );
        }
        //Ejecución de la eliminación
        try{
            productRepository.deleteById(id);
            responseMap.put("error",false);
            responseMap.put("message", "Producto eliminado exitosamente.");
            return new ResponseEntity<>(
                    responseMap,
                    HttpStatus.OK     // 200 OK: la solicitud fue exitosa
            );
        }
        // Manejo de errores inesperados (ej: problemas de conexión a la DB)
        catch (Exception e) {
            responseMap.put("error", true);
            responseMap.put("message", "Error interno al intentar eliminar el producto.");
            return new ResponseEntity<>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
