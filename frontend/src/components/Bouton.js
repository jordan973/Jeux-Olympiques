import React from 'react';
import './Bouton.css';

function Bouton({text, type="button"}){
    return(
        <button className='bouton' type={type}>{text}</button>
    )
}

export default Bouton;