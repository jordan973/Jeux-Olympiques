import React, { useEffect } from "react";
import './Inscription.css';
import Formulaire from "../components/Formulaire.js";
import { useNavigate } from "react-router-dom";

function Inscription(){

    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            navigate("/"); 
        }
    }, [navigate]);

    const apiUrl = process.env.REACT_APP_API_URL || 'https://jeux-olympiques-5qjp.onrender.com/api';

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
              navigate("/connexion"); 
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
            <main>
                <Formulaire 
                title="Inscription"
                nameFields={true}
                submit={inscriptionSubmit}
                buttonText="S'inscrire"
                linkDesc="Déjà inscrit ? "
                linkHref="/connexion"
                linkText="Me connecter"
                />
            </main>
        </div>
    )
}

export default Inscription;