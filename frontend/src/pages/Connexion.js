import React, { useEffect } from "react";
import './Connexion.css';
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
                const infosUtilisateur = await response.json();
                const { token, id, prenom, nom, email } = infosUtilisateur;

                localStorage.setItem('token', token);
                localStorage.setItem('user', JSON.stringify({id, prenom, nom, email }));
                
                navigate("/profil"); 
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
        </div>
    )
}

export default Connexion;