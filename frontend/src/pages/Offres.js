import React from "react";
import './Offres.css';
import Header from '../components/Header.js';
import Billet from "../components/Billet.js";
import Footer from '../components/Footer.js';

function Offres(){
    return(
        <div className="offres">
            <img src="./img/olympics.png" alt="Olympic Games" className="navbar-image" />
            <Header />
            <main>
                <Billet />
            </main>
            <Footer />
        </div>
    )
}

export default Offres;