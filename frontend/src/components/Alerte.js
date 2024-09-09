import React from 'react';
import './Alerte.css';

function Alerte({ message, type, onClose }) {
  return (
    <div className={`alerte alerte-${type}`}>
      <span>{message}</span>
      <button className="close-bouton" onClick={onClose}>✖</button>
    </div>
  );
}

export default Alerte;
