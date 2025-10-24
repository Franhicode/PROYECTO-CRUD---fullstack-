// aca estaria la logica para consumir una API RESTful de productos 

import axios from 'axios';

const Backend_URL = 'http://localhost:8080/api/v1/products';   // se setea la URL del backend

//FUNCIONES ASINCRONAS Y PROMESAS
//- OBTENER TODOS - GET
export const fetchProducts = async () => {    //esta peticion web al servidor de spring es una operacion asincrona
    try {  //potencialmente podría fallar || Si la llamada es exitosa, el código dentro de try se ejecuta hasta el final.
        const response = await axios.get(Backend_URL);   // como await solo puede esatar dentro de async, en este caso detiene la ejecucion solo si se cumple la promesa: y la promesa es: que la petición HTTP termine y Axios obtenga una respuesta).
        return response.data; //recien despues de eso devuelvelos datos..  la lista (List<Product>)
    } catch (error) { 
        console.error("Error al obtener productos! ", error);
        throw error;   // Propaga el error para que el componente React lo maneje
    } 
};

//- OBTENER TODOS - GET /{id}
export const fetchProductById = async (id) => {
    try {
        // La URL es con el ID al final
        const response = await axios.get(`${API_BASE_URL}/${id}`);
        // Tu Service devuelve un objeto con {error, data} en un 200
        return response.data; 
    } catch (error) {
        // Capturamos el 404 de tu Service
        return error.response 
            ? error.response.data 
            : { error: true, message: "Error de red o servidor." };
    }
};

//- CREAR PRODUCTO - POST
export const createProduct = async (productData) => {
    try {
        const response = await axios.post(Backend_URL, productData); //envía los datos del formulario (el nuevo producto) a tu backend de Spring Boot para que sean guardados en la base de datos.
        return response.data;
    } catch (error) {
        // El backend devuelve errores 409, 400. Capturamos esos detalles
        return error.response  //Cuando tu servidor Spring Boot devuelve un error HTTP (ej. 409 Conflict o 400 Bad Request), Axios lo captura y establece la propiedad error.response
            ? error.response.data   //Al devolver esto se envia el mensaje de error personalizado de backend a frontend.
            : { error: true, message: "Error de red o servidor." };
    }
};

//- ACTUALICAR PRODUCTOS -PUT
export const updateProduct = async (id, productData) => {
    try {
        const response = await axios.put(`${Backend_URL}/${id}`, productData);
        return response.data;
    } catch (error) {
        return error.response
            ? error.response.data 
            : { error: true, message: "Error de red o servidor." };
    }
};

//- ELIMINAR PRODUCTO - DELETE
export const deleteProduct = async (id) => {
    try {
        const response = await axios.delete(`${Backend_URL}/${id}`);
        return response.data;
    } catch (error) {
        return error.response ? error.response.data : { error: true, message: "Error de red o servidor." };
    }
};

        