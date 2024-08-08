import React from "react";
import Header from '../components/Header.js';
import Footer from '../components/Footer.js';
import './Offres.css';

function Offres(){
    return(
        <div className="offres">
            <img src="./img/olympics.png" alt="Olympic Games" className="navbar-image" />
            <Header />

            <Footer />
        </div>
    )
}

export default Offres;