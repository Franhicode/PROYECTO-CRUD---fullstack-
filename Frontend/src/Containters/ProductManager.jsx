//En react/JavaScript(JSX) este paquete se lo conoce como Containers, y su clase se la debe escribir como  pruductManager || aca esta la logica
//Este componente contiene el estado de la aplicación, llama a las funciones de productApi.js y pasa datos y funciones a los componentes de presentación.
import React, { useEffect, useState } from 'react';

//IMPORT de las funciones AXIOS de la capa api
import { createProduct, fetchProducts, updateProduct, deleteProduct, fetchProductById } from '../Api/ProductApi';

import { Product } from '../Entities/Product';
import { ProductTable } from '../Components/ProductTable';
import { AlertMessage } from '../Components/AlertMessage';
import { ProductForm } from '../Components/ProductForm';


export const ProductManager = () => {
  // 1 Estados principales de la app 
  const [products, setProducts] = useState([]); //almacena los productos
  const [loading, setLoading] = useState(false); //indica si se esta cargando
  const [error, setError] = useState(null); //almacena errores
  const [notification, setNotification] = useState(null); //notificaciones de error/exito
  const [currentProduct, setCurrentProduct] = useState(Product); //producto actual para editar/crear
  const [isEditing, setIsEditing] = useState(false); //indica si se esta editando o creando

  // GET
  const loadProducts = async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await fetchProducts();
      setProducts(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Efecto para cargar datos (carga todos los get por primera vez cunado se monta el componente) al montar el componente
  useEffect(() => {
    loadProducts();
  }, []);

  // POST & PUT
  const handlePOSTorPUT = async (e) => {
    e.preventDefault();
    setNotification(null);
    let result;
    // PUT
    if (isEditing) {
      result = await updateProduct(currentProduct.id, currentProduct);
    } 
    // POST
    else {
      result = await createProduct(currentProduct);
    }
    // Manejo de respuesta
    if (result.error) {
      setNotification({ type: 'error', message: result.message });
    } else {
      setNotification({ type: 'success', message: result.message });
      setCurrentProduct(Product); // Resetea el formulario
      setIsEditing(false); // Resetea el modo de edición
      loadProducts();     // Recarga la lista para ver los cambios
    }
  };

  // DELETE
  const handleDelete = async (id) => {
    const confirmed = window.confirm("¿Estás seguro de que deseas eliminar este producto?");
    if (!confirmed) return;
    setNotification(null);
    const result = await deleteProduct(id);
    if (result.error) {
      setNotification({ type: 'error', message: result.message });
    } else {
      setNotification({ type: 'success', message: result.message });
      loadProducts();
    }
  };

  //MODO EDICION
  const startEdit = (product) => {
    setCurrentProduct(product);
    setIsEditing(true);
  }

  //RENDER CONDICIONAL
  if (loading) return <div className="loading">Cargando productos...</div>;

  return (
    <div className="product-manager">
      <h1>Gestor de Stock - Cosmetica DV</h1>

      {notification && <AlertMessage type={notification.type} message={notification.message}/>}  

      <ProductForm
         product={currentProduct}
         setProduct={setCurrentProduct}
         handleSubmit={handlePOSTorPUT}
         isEditing={isEditing}
         cancelEdit={() => { setIsEditing(false); setCurrentProduct(InitialProductState); }}
        />
        
        {error ? (
            <div className="error-message">Error: {error}</div>
        ) : (
            <ProductTable
            products={products}
            onDelete={handleDelete}
            onEdit={startEdit}
            />
        )}
    </div>
  );
}

