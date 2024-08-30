import React from "react";
import './Inscription.css';
import Header from '../components/Header.js';
import Footer from '../components/Footer.js';
import Formulaire from "../components/Formulaire.js";

function Inscription(){

    const apiUrl = process.env.REACT_APP_API_URL;

    const inscriptionSubmit = async (user) => {
        try {
            const response = await fetch(`${apiUrl}/users/inscription`, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(user),
            });
      
            if (response.ok) {
              alert('Inscription réussie !');
            } else {
              alert('Erreur lors de l\'inscription.');
            }
          } catch (error) {
            console.error('Erreur:', error);
            alert('Une erreur est survenue.');
          }
    }

    return(
        <div className="offres">
            <img src="./img/olympics.png" alt="Olympic Games" className="navbar-image" />
            <Header />
            <main>
                <Formulaire 
                title="Inscription"
                submit={inscriptionSubmit}
                buttonText="S'inscrire"
                linkText="Déjà inscrit ? "
                linkHref="/connexion"
                />
            </main>
            <Footer />
        </div>
    )
}

export default Inscription;