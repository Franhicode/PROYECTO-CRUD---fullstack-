//En react/JavaScript(JSX) esta entidad, se la conoce como Initial State y su paquete como models
//Define la estructura de datos que se enviará al backend

export const Product = {

    id: null,           // null, ya que la DB lo gener
    name: '',
    description: '',    // String inicializado a vacío
    price: 0.0,         // Float/Number inicializado a 0
    stock: 0,           // Integer/Number inicializado a 0
    category: '',
    publicationDate: new Date().toISOString().slice(0,10) // Formato 'YYYY-MM-DD' para coincidir con tu backend  
}
