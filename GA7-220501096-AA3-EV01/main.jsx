import React, { useMemo, useState } from 'react';
import { createRoot } from 'react-dom/client';
import { ArrowRight, Heart, Menu, Search, ShoppingBag } from 'lucide-react';
import './styles.css';

// Productos demostrativos para el catálogo inicial de Aorom Joyería.
const products = [
  { id: 1, name: 'Anillo Aurora', category: 'Anillos', price: 289000, tag: 'Más vendido' },
  { id: 2, name: 'Aretes Luz', category: 'Aretes', price: 195000 },
  { id: 3, name: 'Collar Sol', category: 'Collares', price: 345000, tag: 'Nuevo' },
  { id: 4, name: 'Pulsera Serena', category: 'Pulseras', price: 229000 },
  { id: 5, name: 'Anillo Esencial', category: 'Anillos', price: 175000 },
  { id: 6, name: 'Aretes Alba', category: 'Aretes', price: 215000, tag: 'Favorito' },
  { id: 7, name: 'Dije Celeste', category: 'Collares', price: 265000 },
  { id: 8, name: 'Pulsera Origen', category: 'Pulseras', price: 199000 },
];
const categories = ['Todo', 'Anillos', 'Aretes', 'Collares', 'Pulseras'];
const money = (amount) => new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 0 }).format(amount);

function App() {
  const [category, setCategory] = useState('Todo');
  const [query, setQuery] = useState('');
  const [favorites, setFavorites] = useState([]);
  const [cart, setCart] = useState(0);
  const [notice, setNotice] = useState('');
  const [menuOpen, setMenuOpen] = useState(false);
  // La lista se actualiza al combinar el texto escrito con la categoría seleccionada.
  const visibleProducts = useMemo(() => products.filter((item) => {
    const matchesCategory = category === 'Todo' || category === item.category;
    const matchesQuery = `${item.name} ${item.category}`.toLowerCase().includes(query.trim().toLowerCase());
    return matchesCategory && matchesQuery;
  }), [category, query]);
  const notify = (text) => { setNotice(text); window.setTimeout(() => setNotice(''), 2400); };
  const toggleFavorite = (id) => setFavorites((items) => items.includes(id) ? items.filter((item) => item !== id) : [...items, id]);

  return <>
    <div className="announcement">ENVÍO GRATIS EN COMPRAS DESDE $250.000 <span>·</span> HECHO CON DETALLE, PARA DURAR</div>
    <header className="header">
      <button className="menu" aria-label="Abrir menú" onClick={() => setMenuOpen(!menuOpen)}><Menu size={20}/></button>
      <a className="brand" href="#inicio">AOROM<small>JOYERÍA</small></a>
      <nav className={menuOpen ? 'nav open' : 'nav'}><a href="#coleccion" onClick={() => setMenuOpen(false)}>Colección</a><a href="#historia" onClick={() => setMenuOpen(false)}>Nuestra esencia</a><a href="#contacto" onClick={() => setMenuOpen(false)}>Contacto</a></nav>
      <div className="actions"><label className="search"><Search size={17}/><input aria-label="Buscar productos" placeholder="Buscar" value={query} onChange={(event) => setQuery(event.target.value)}/></label><button className="bag" aria-label={`Bolsa con ${cart} productos`} onClick={() => notify(`Tu bolsa tiene ${cart} producto(s)`)}><ShoppingBag size={19}/><span>Bolsa</span><b>{cart}</b></button></div>
    </header>
    <main>
      <section className="hero" id="inicio"><div className="hero-copy"><p className="eyebrow">JOYAS CON HISTORIA</p><h1>Brilla a<br/><em>tu manera.</em></h1><p className="intro">Piezas especiales para acompañarte hoy<br className="desktop"/> y convertirse en parte de tu historia.</p><a className="button dark" href="#coleccion">Descubrir colección <ArrowRight size={16}/></a><p className="note">— Diseñado para atesorar</p></div><div className="hero-art" aria-label="Ilustración de una joya"><div className="disc"/><div className="pendant"><span/><i/></div><small>COLECCIÓN<br/>ESENCIA · 2026</small></div><a className="scroll" href="#coleccion">↓ &nbsp; DESLIZA PARA EXPLORAR</a></section>
      <section className="collection" id="coleccion"><div className="section-title"><div><p className="eyebrow">UNA PIEZA PARA CADA MOMENTO</p><h2>Encuentra tu próxima favorita</h2><p>Diseños atemporales, hechos para ti.</p></div><a href="#historia">Conoce Aorom ↗</a></div>
        <div className="filters"><div className="categories" role="group" aria-label="Filtrar por categoría">{categories.map((item) => <button key={item} className={category === item ? 'selected' : ''} onClick={() => setCategory(item)}>{item}</button>)}</div><small>{visibleProducts.length} PIEZAS</small></div>
        {visibleProducts.length ? <div className="grid">{visibleProducts.map((item, index) => <article className="product" key={item.id}><div className={`photo photo-${index % 4}`}><div className={`jewel jewel-${item.category.toLowerCase()}`} aria-hidden="true"><i/></div>{item.tag && <span className="tag">{item.tag}</span>}<button className={`favorite ${favorites.includes(item.id) ? 'liked' : ''}`} aria-label={favorites.includes(item.id) ? 'Quitar favorito' : 'Guardar favorito'} onClick={() => toggleFavorite(item.id)}><Heart size={17} fill={favorites.includes(item.id) ? 'currentColor' : 'none'}/></button></div><div className="product-line"><div><small>{item.category}</small><h3>{item.name}</h3></div><button className="add" aria-label={`Agregar ${item.name} a la bolsa`} onClick={() => { setCart((count) => count + 1); notify(`${item.name} se agregó a tu bolsa`); }}>+</button></div><p className="price">{money(item.price)}</p></article>)}</div> : <p className="empty">No encontramos joyas con esa búsqueda. Prueba con otro nombre o categoría.</p>}
        <div className="all"><a className="button outline" href="#contacto">Ver toda la colección <ArrowRight size={15}/></a></div>
      </section>
      <section className="story" id="historia"><div className="story-art"><span>A</span><small>OBJETOS PARA<br/>GUARDAR CERCA</small></div><div className="story-copy"><p className="eyebrow">LA ESENCIA AOROM</p><h2>Pequeños detalles.<br/><em>Grandes significados.</em></h2><p>Creemos que una joya puede guardar un recuerdo, celebrar un comienzo o recordarte quién eres. Creamos piezas para acompañar tu propia historia.</p><a href="#contacto">Conoce nuestra historia ↗</a></div></section>
    </main>
    <footer id="contacto"><a className="brand" href="#inicio">AOROM<small>JOYERÍA</small></a><p>Joyas para acompañar tu historia.</p><div className="footer-line"><span>© 2026 Aorom Joyería</span><span>Hecho con intención en Colombia</span><a href="mailto:hola@aoromjoyeria.com">Escríbenos</a></div></footer>
    {notice && <div className="toast" role="status">{notice}</div>}
  </>;
}

createRoot(document.getElementById('root')).render(<React.StrictMode><App/></React.StrictMode>);
