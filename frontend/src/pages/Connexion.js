import React, { useEffect } from "react";
import './Connexion.css';
import Header from '../components/Header.js';
import Footer from '../components/Footer.js';
import Formulaire from "../components/Formulaire.js";
import { useNavigate } from "react-router-dom";

function Connexion(){

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            navigate("/"); 
        }
    }, [navigate]);

    const apiUrl = process.env.REACT_APP_API_URL;

    const connexionSubmit = async (user) => {
        try {
            const response = await fetch(`${apiUrl}/users/connexion`, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(user),
            });
      
            if (response.ok) {
                const token = await response.text();
                alert('Connexion réussie !');
                localStorage.setItem('token', token);
                window.location.href = "/"; 
            } else {
                alert('Email ou mot de passe incorrect.');
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
                title="Connexion"
                nameFields={false}
                submit={connexionSubmit}
                buttonText="Se connecter"
                linkDesc="Pas encore inscrit ? "
                linkHref="/mon-compte"
                linkText="S'inscrire"
                />
            </main>
            <Footer />
        </div>
    )
}

export default Connexion;