import React, { useEffect, useState } from 'react';
import './Billet.css';
import Bouton from './Bouton';
import Alerte from './Alerte';

function Billet({ajouterAuPanier, visible, message, type, onClose}) {
    const [offres, setOffres] = useState([]);

    const apiUrl = process.env.REACT_APP_API_URL || 'https://jeux-olympiques-5qjp.onrender.com/api';

    useEffect(() => {
        fetchOffres();
    }, []);

        // Fonction pour récupérer les offres
        const fetchOffres = () => {
            fetch(`${apiUrl}/offres`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            .then(response => response.json())
            .then((data) => {
                if (Array.isArray(data)) {
                    const sortedData = data.sort((a, b) => a.id - b.id);
                    setOffres(sortedData);
                } else {
                    console.error('Les données reçues ne sont pas un tableau:', data);
                    setOffres([]);
                }
            })
            .catch(error => console.error('Erreur lors de la récupération des offres :', error));
        };

    return (
        <section>
             {visible && (
                <Alerte message={message} type={type} onClose={onClose} />
            )}
            <h2 className='section-title'>Les Offres</h2>
            <div className='offers-grid'>
                {offres.map((offre) => (
                    <div key={offre.id} className='offer-card'>
                        <h1 className='offer-title'>{offre.nom}</h1>
                        <p className='offer-description'>{offre.description}</p>
                        <h3 className='offer-price'>{offre.prix}€</h3>
                        <Bouton text="Ajouter au panier" onClick={() => ajouterAuPanier(offre)} />
                    </div>
                ))}
            </div>
        </section>
    );
}

export default Billet;