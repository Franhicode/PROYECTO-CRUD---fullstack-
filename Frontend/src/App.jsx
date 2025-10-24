import React from 'react'
import './App.css'
import { ProductManager } from './Containters/ProductManager'

function App() {

  return (
    <>
      <div className='App'>
        <main style={{ padding: '20px' }}>
          <ProductManager />
        </main>
      </div>
    </>
  )
}

export default App;