import React from 'react';
//este componente se encarga del input y el boton

export const ProductForm = ({ product, setProduct, handleSubmit, isEditing , cancelEdit }) => {
    // Función genérica para manejar los cambios en cualquier input
    const handleChange = (e) => {
        const {name, value } = e.target;
        setProduct(prev => ({
            ...prev,
            [name]: (name === 'price' || name === 'stock') ? Number(value) : value
        }));
    };

    return (
        <form onSubmit={handleSubmit} className="product-form">
            <h2>{isEditing ? "Editar Producto" : "Crear Nuevo Producto"}</h2>
            
            <input name="name" type="text" placeholder="Nombre" value={product.name} onChange={handleChange} required />
            <textarea name="description" placeholder="Descripción" value={product.description} onChange={handleChange} required />
            <label htmlFor="price">Precio
                <input name="price" type="number" step="0.01" placeholder="Precio" value={product.price} onChange={handleChange} required min="0.01" />
            </label>
            <label htmlFor="stock">Stock / Unidades
                <input name="stock" type="number" placeholder="Stock" value={product.stock} onChange={handleChange} required min="1" />
            </label>
            <input name="category" type="text" placeholder="Categoría" value={product.category} onChange={handleChange} required />
            <label>
                Fecha de Publicación:
                <input name="publicationDate" type="date" value={product.publicationDate} onChange={handleChange} required />
            </label>
            
            <button type="submit">
                {isEditing ? "Guardar Cambios" : "Crear Producto"}
            </button>
            
            {isEditing && (
                <button type="button" onClick={cancelEdit} className="cancel-button">
                    Cancelar Edición
                </button>
            )}
        </form>
    )
}