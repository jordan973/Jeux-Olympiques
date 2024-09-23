import React, { useEffect, useState } from 'react';
import './Profil.css';
import Bouton from '../components/Bouton.js';
import { useNavigate } from 'react-router-dom';

function Profil() {
    const [details, setDetails] = useState(null);
    const [commandes, setCommandes] = useState([]);
    const [offres, setOffres] = useState([]);
    const [nouvelleOffre, setNouvelleOffre] = useState({ nom: '', prix: '', stock: '', description: '' });
    const [modif, setModif] = useState(false);
    const [offreEnCours, setOffreEnCours] = useState(null);

    const navigate = useNavigate();
    const apiUrl = process.env.REACT_APP_API_URL || 'https://jeux-olympiques-5qjp.onrender.com/api';

    useEffect(() => {
        const infos = localStorage.getItem('user');

        if (infos) {
            const userDetails = JSON.parse(infos);
            fetchProfil(userDetails.id);
        }
    }, []);

    // Fonction pour récupérer le rôle de l'utilisateur
    const fetchProfil = (idUtilisateur) => {
        fetch(`${apiUrl}/users/profile/${idUtilisateur}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then(response => response.json())
        .then(userProfile => {
            setDetails(userProfile);
            fetchCommandes(userProfile.id);
            if (userProfile.role === 'Administrateur') {
                fetchOffres();
            }
        })
        .catch(error => console.error('Erreur lors de la récupération du profil utilisateur :', error));
    };

    // Fonction pour récupérer les commandes d'un utilisateur
    const fetchCommandes = (idUtilisateur) => {
        fetch(`${apiUrl}/commandes/utilisateur/${idUtilisateur}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then(response => response.json())
        .then(commandes => { setCommandes(commandes); })
        .catch(error => console.error('Erreur lors de la récupération des commandes :', error));
    };

    // Fonction pour télécharger le PDF des billets
    const telechargerBillet = (idCommande) => {
        fetch(`${apiUrl}/pdf/generer/${idCommande}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/pdf',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
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
        .catch(error => console.error('Erreur lors du téléchargement des billets :', error));
    };

    // Fonction de déconnexion de l'utilisateur
    const deconnexion = () => {
        localStorage.clear();
        navigate('/connexion');
    };

    // FONCTIONS ADMINISTRATEUR
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

    // Fonction pour ajouter ou modifier une offre
    const ajouterOuModifierOffre = () => {
        if (modif) {
            modifierOffre(offreEnCours.id, details.id);
        } else {
            ajouterOffre(details.id);
        }
    };

    // Fonction pour ajouter une offre
    const ajouterOffre = (idUtilisateur) => {
        fetch(`${apiUrl}/offres?idUtilisateur=${idUtilisateur}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(nouvelleOffre)
        })
        .then(response => response.json())
        .then(offre => {
            setOffres([...offres, offre]);
            resetForm();
        })
        .catch(error => console.error('Erreur lors de l\'ajout de l\'offre :', error));
    };

    // Fonction pour modifier une offre
    const modifierOffre = (idOffre, idUtilisateur) => {
        fetch(`${apiUrl}/offres/${idOffre}?idUtilisateur=${idUtilisateur}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            },
            body: JSON.stringify(nouvelleOffre)
        })
        .then(response => response.json())
        .then(updatedOffre => {
            setOffres(offres.map(o => (o.id === updatedOffre.id ? updatedOffre : o)));
            resetForm();
        })
        .catch(error => console.error('Erreur lors de la mise à jour de l\'offre :', error));
    };

    // Fonction pour remplir le formulaire avec les données de l'offre à modifier
    const remplirFormulairePourModifier = (offre) => {
        setNouvelleOffre(offre);
        setOffreEnCours(offre);
        setModif(true);
    };

    // Fonction pour réinitialiser le formulaire
    const resetForm = () => {
        setNouvelleOffre({ nom: '', prix: '', stock: '', description: '' });
        setModif(false);
        setOffreEnCours(null);
    };

    // Fonction pour supprimer une offre
    const supprimerOffre = (idOffre, idUtilisateur) => {
        fetch(`${apiUrl}/offres/${idOffre}?idUtilisateur=${idUtilisateur}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then(() => {
            setOffres(offres.filter(offre => offre.id !== idOffre));
        })
        .catch(error => console.error('Erreur lors de la suppression de l\'offre :', error));
    };

    return (
        <div>
            <main>
                <section>
                    <h2 className='section-title'>Mon compte</h2>
                    <div className='section-profile'>
                        {details ? (
                            <>
                                <h3>Bienvenue, {details.prenom} {details.nom} !</h3>
                                <p>Adresse email : {details.email}</p>
                                <h4>Mes commandes :</h4>
                                {commandes.length > 0 ? (
                                <table className='commandes-tableau'>
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
                                            <td data-label="ID Commande">#{commande.id}</td>
                                            <td data-label="Montant">{commande.montant}€</td>
                                            <td data-label="Date">{new Date(commande.date).toLocaleDateString()}</td>
                                            <td className='flex-btn'>
                                                <button 
                                                    className='telecharger-billet-btn'
                                                    onClick={() => telechargerBillet(commande.id)}>
                                                    Télécharger les billets
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                                </table>
                                ) : (
                                    <p>Aucune commande effectuée.</p>
                                )}

                                {details.role === 'Administrateur' && (
                                    <>
                                        <h4>Gestion des Offres :</h4>
                                        <div className='admin-section'>
                                            <h5>{modif ? "Modifier l'Offre" : "Ajouter une Offre"}</h5>
                                            <form className='ajout-offre-formulaire' onSubmit={(e) => {e.preventDefault(); ajouterOuModifierOffre();}}>
                                                <input 
                                                    type='text'
                                                    placeholder='Nom'
                                                    value={nouvelleOffre.nom}
                                                    onChange={(e) => setNouvelleOffre({ ...nouvelleOffre, nom: e.target.value })}
                                                    className='formulaire-input'
                                                    required
                                                />
                                                <input 
                                                    type='number'
                                                    placeholder='Prix'
                                                    value={nouvelleOffre.prix}
                                                    onChange={(e) => setNouvelleOffre({ ...nouvelleOffre, prix: e.target.value })}
                                                    className='formulaire-input'
                                                    required
                                                />
                                                <input 
                                                    type='number'
                                                    placeholder='Stock'
                                                    value={nouvelleOffre.stock}
                                                    onChange={(e) => setNouvelleOffre({ ...nouvelleOffre, stock: e.target.value })}
                                                    className='formulaire-input'
                                                    required
                                                />
                                                <textarea 
                                                    placeholder="Description"
                                                    value={nouvelleOffre.description}
                                                    onChange={(e) => setNouvelleOffre({ ...nouvelleOffre, description: e.target.value })}
                                                    className='formulaire-input'
                                                    required
                                                ></textarea>
                                                <Bouton type='submit' text={modif ? "Modifier l'offre" : "Ajouter l'offre"} className='btn-ajouter-offre' />
                                                {modif && <button type='button' onClick={resetForm} className='annuler-btn'>Annuler</button>}
                                            </form>

                                            <h5>Offres Existantes :</h5>
                                            {offres.length > 0 ? (
                                                <table className='offres-tableau'>
                                                    <thead>
                                                        <tr>
                                                            <th>ID Offre</th>
                                                            <th>Nom</th>
                                                            <th>Prix</th>
                                                            <th>Stock</th>
                                                            <th>Ventes</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {offres.map(offre => (
                                                            <tr key={offre.id}>
                                                                <td data-label="ID Offre">#{offre.id}</td>
                                                                <td data-label="Nom">{offre.nom}</td>
                                                                <td data-label="Prix">{offre.prix}€</td>
                                                                <td data-label="Stock">{offre.stock}</td>
                                                                <td data-label="Ventes">{offre.vente}</td>
                                                                <td className='flex-btn'>
                                                                    <button 
                                                                        className='modifier-offre-btn'
                                                                        onClick={() => remplirFormulairePourModifier(offre)}
                                                                    >
                                                                        Modifier
                                                                    </button>
                                                                    <button 
                                                                        className='supprimer-offre-btn'
                                                                        onClick={() => supprimerOffre(offre.id, details.id)}
                                                                    >
                                                                        Supprimer
                                                                    </button>
                                                                </td>
                                                            </tr>
                                                        ))}
                                                    </tbody>
                                                </table>
                                            ) : (
                                                <p>Aucune offre disponible pour le moment.</p>
                                            )}
                                        </div>
                                    </>
                                )}
                            </>
                        ) : (
                            <p>Chargement...</p>
                        )}
                        <Bouton text='Se déconnecter' onClick={deconnexion} />
                    </div>
                </section>
            </main>
        </div>
    );
}

export default Profil;