import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './Confirmation.css';

function Confirmation() {

  const navigate = useNavigate();
  const { idCommande } = useParams();
  const [commande, setCommande] = useState(null);
  const apiUrl = process.env.REACT_APP_API_URL;

  useEffect(() => {
    const fetchCommande = async () => {
      try {
        const response = await fetch(`${apiUrl}/commandes/${idCommande}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });

        if (response.ok) {
          const data = await response.json();
          setCommande(data);
        } else {
          alert("Erreur lors de la récupération de la commande.");
          navigate('/');
        }
      } catch (error) {
        console.error("Erreur lors de la récupération des détails de la commande :", error);
        alert("Une erreur est survenue.");
        navigate('/');
      }
    };

    fetchCommande();
  }, [idCommande, navigate, apiUrl]);

  if (!commande) return <p>Chargement des détails de la commande...</p>;

  return (
    <main>
        <section>
            <h2 className="section-title">Ma commande</h2>
                <div className="page-confirmation">
                    <h1>Merci d'avoir commandé !</h1>
                    <p>Votre commande a bien été prise en compte.</p>
                    <p>Vous pouvez désormais télécharger vos e-billets depuis votre espace <a href="/profil">Mon Compte</a>.</p>
                    <div className="commande-details">
                        <p>Achat effectué le : <b>{new Date(commande.date).toLocaleDateString()}</b></p>
                        <p>Montant total : <b>{commande.montant} €</b></p>
                    </div>
                </div>
        </section>
    </main>
  );
}

export default Confirmation;