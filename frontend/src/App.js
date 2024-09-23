import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Offres from './pages/Offres';
import Inscription from './pages/Inscription';
import Connexion from "./pages/Connexion";
import Profil from './pages/Profil';
import PrivateRoute from './components/PrivateRoute';

import './App.css';
import Header from "./components/Header";
import Footer from "./components/Footer";
import Paiement from "./pages/Paiement";
import Confirmation from "./pages/Confirmation";

function App() {

    const [panier, setPanier] = useState([]);
    const [ouvert, setOuvert] = useState(false);
    const [alerte, setAlerte] = useState({ visible: false, message: '', type: '' });


    useEffect(() => {
        const savedPanier = JSON.parse(localStorage.getItem('panier')) || [];
        setPanier(savedPanier);
    }, []);

    //Fonction pour ajouter une offre au panier à partir de son ID
    const ajouterAuPanier = (offre) => {
        const offreIndex = panier.findIndex((item) => item.id === offre.id);
        let updatedPanier;

        if (offreIndex !== -1) {
            updatedPanier = [...panier];
            updatedPanier[offreIndex].quantite += 1;
        } else {
            updatedPanier = [...panier, { ...offre, quantite: 1 }];
        }

        setPanier(updatedPanier);
        localStorage.setItem('panier', JSON.stringify(updatedPanier));
        
        setAlerte({ visible: true, message: 'Offre ajoutée au panier avec succès !', type: 'success' });
        
        //Fonction pour masquer l'alerte après 3 secondes
        setTimeout(() => {
            setAlerte({ visible: false, message: '', type: '' });
        }, 3000);
    };

    const closeAlerte = () => {
        setAlerte({ visible: false, message: '', type: '' });
    };

    const toggleModal = () => {
        setOuvert(!ouvert);
    };

    return (
        <Router>
            <img src="./img/olympics.png" alt="Olympic Games" className="navbar-image" />
            <Header panier={panier} isOpen={ouvert} toggleModal={toggleModal} setPanier={setPanier} />
            <Routes>
                <Route exact path='/' element={<Home />} />
                <Route path='/offres' element={<Offres ajouterAuPanier={ajouterAuPanier} visible={alerte.visible} message={alerte.message} type={alerte.type} onClose={closeAlerte} />} />
                <Route path='/mon-compte' element={<Inscription />} />
                <Route path='/connexion' element={<Connexion />} />
                <Route path="/profil" element={<PrivateRoute><Profil /></PrivateRoute>} />
                <Route path="/paiement" element={<PrivateRoute><Paiement /></PrivateRoute>} />
                <Route path="/confirmation/:idCommande" element={<PrivateRoute><Confirmation /></PrivateRoute>} />
            </Routes>
            <Footer />
        </Router>
    );
}

export default App;