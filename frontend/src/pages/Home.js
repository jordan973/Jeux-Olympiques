import React from 'react';
import Header from '../components/Header.js';
import Epreuve from '../components/Epreuve.js';
import Footer from '../components/Footer.js';
import './Home.css';


function Home(){
    return(
        <div className='home'>
            <img src="./img/olympics.png" alt="Olympic Games" className="navbar-image" />
            <Header />
            <Epreuve />
            <Footer />
        </div>
    )
}

export default Home;
