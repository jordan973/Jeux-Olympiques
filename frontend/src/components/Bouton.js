import React from 'react';
import './Bouton.css';

function Bouton({text, type="button", onClick, disabled}){
    return(
        <button className={`bouton ${disabled ? 'bouton-disabled' : ''}`} type={type} onClick={onClick} disabled={disabled}>{text}</button>
    )
}

export default Bouton;