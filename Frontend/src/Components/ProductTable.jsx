// Este componente solo se encarga de mostrar la tabla y notificar las acciones
import React from 'react';

export const ProductTable = ({ products, onDelete, onEdit }) => {

    // Si no hay productos, mostramos un mensaje
    if (!products || products.length === 0) {
        return <p>Aún no tienes pruductos registrados.</p>;
    }

    return ( 
        <table className="product-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Categoría</th>
                    <th>Fecha Pub.</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                {products.map(p => (
                    <tr key={p.id}>
                        <td>{p.id}</td>
                        <td>{p.name}</td>
                        <td>${p.price ? p.price.toFixed(2) : '0.00'}</td>
                        <td>{p.stock}</td>
                        <td>{p.category}</td>
                        <td>{p.publicationDate}</td>
                        <td>
                            <button className="edit-button" onClick={() => onEdit(p)}>
                                Editar
                            </button>
                            <button className="delete-button" onClick={() => onDelete(p.id)}>
                                Eliminar
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
}