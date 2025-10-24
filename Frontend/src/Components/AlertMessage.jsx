// Componente para mostrar mensajes temporales de éxito o error
import React, { useEffect, useState } from 'react';

export const AlertMessage = ({ type, message }) => {
    const [isVisible, setIsVisible] = useState(true);

    // Oculta el mensaje después de 5 segundos
    useEffect(() => {
        const timer = setTimeout(() => {
            setIsVisible(false);
        }, 5000);
        return () => clearTimeout(timer); // Limpieza del temporizador
    }, [message]);

    if (!isVisible) return null;

    // Clase CSS para estilizar según el tipo (success o error)
    const className = `alert ${type}`; 
    
    return (
        <div className={className} role="alert">
            {message}
        </div>
    );
}