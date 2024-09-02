import React from 'react';
import './Bouton.css';

function Bouton({text, type="button", onClick}){
    return(
        <button className='bouton' type={type} onClick={onClick}>{text}</button>
    )
}

export default Bouton;