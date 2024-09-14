import React, { useEffect, useState } from 'react';
import './Profil.css';
import Bouton from '../components/Bouton.js';
import { useNavigate } from 'react-router-dom';

function Profil() {
    const [details, setDetails] = useState(null);
    const [commandes, setCommandes] = useState([]);
    const navigate = useNavigate();
    const apiUrl = process.env.REACT_APP_API_URL;

    useEffect(() => {
        const infos = localStorage.getItem('user');

        if (infos) {
            const userDetails = JSON.parse(infos);
            setDetails(userDetails);
            if (userDetails.id) {
                fetchCommandes(userDetails.id);
            } else {
                console.error("Aucun ID d'utilisateur trouvé dans les détails de l'utilisateur.");
            }
        }
    }, []);

    // Fonction pour récupérer les commandes d'un utilisateur
    const fetchCommandes = (idUtilisateur) => {
        fetch(`${apiUrl}/commandes/utilisateur/${idUtilisateur}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // 'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de la récupération des commandes.');
            }
            return response.json();
        })
        .then(commandes => {
            console.log('Commandes reçues:', commandes);
            setCommandes(commandes);
        })
        .catch(error => {
            console.error('Erreur lors de la récupération des commandes :', error);
        });
    };

    // Fonction pour télécharger le PDF des billets
    const telechargerBillet = (idCommande) => {
        fetch(`${apiUrl}/pdf/generer/${idCommande}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/pdf',
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors du téléchargement des billets.');
            }
            return response.blob();
        })
        .then(blob => {
            const url = window.URL.createObjectURL(new Blob([blob]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `billets-commande-${idCommande}.pdf`);
            document.body.appendChild(link);
            link.click();
            link.parentNode.removeChild(link);
        })
        .catch(error => {
            console.error('Erreur lors du téléchargement des billets :', error);
        });
    };

    // Fonction de déconnexion de l'utilisateur
    const deconnexion = () => {
        localStorage.clear();
        navigate("/connexion");
    };

    return (
        <div>
            <main>
                <section>
                    <h2 className="section-title">Mon compte</h2>
                    <div className='section-profile'>
                        {details ? (
                            <>
                                <h3>Bienvenue, {details.prenom} {details.nom} !</h3>
                                <p>Adresse email : {details.email}</p>
                                <h4>Mes commandes :</h4>
                                {commandes.length > 0 ? (
                                <table className="commandes-table">
                                <thead>
                                    <tr>
                                        <th>ID Commande</th>
                                        <th>Montant</th>
                                        <th>Date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {commandes.map((commande, index) => (
                                        <tr key={index}>
                                            <td>#{commande.id}</td>
                                            <td>{commande.montant}€</td>
                                            <td>{new Date(commande.date).toLocaleDateString()}</td>
                                            <td className="flex-btn">
                                                <button 
                                                    className="telecharger-billet-btn"
                                                    onClick={() => telechargerBillet(commande.id)}
                                                >
                                                    Télécharger les billets
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                                </table>
                                ) : (
                                    <p>Vous n'avez pas encore passé de commandes.</p>
                                )}
                            </>
                        ) : (
                            <p>Chargement des informations...</p>
                        )}
                        <Bouton text="Se déconnecter" onClick={deconnexion} />
                    </div>
                </section>
            </main>
        </div>
    );
}

export default Profil;
