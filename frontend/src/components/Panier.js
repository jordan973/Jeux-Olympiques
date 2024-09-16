// Panier.js
import React from 'react';
import './Panier.css';
import Bouton from './Bouton';
import { useNavigate } from 'react-router-dom';

function Panier({ isOpen, onClose, panier, setPanier }) {
    
    const navigate = useNavigate();

    //Fonction pour rediriger l'utilisateur vers la page de paiement ou de connexion en fonction de s'il est connecté ou non
    const redirectionCompte = () => {
        const token = localStorage.getItem('token');
        if (token) {
            onClose();
            navigate('/paiement');
        } else {
            alert('Veuillez vous connecter pour procéder au paiement.');
            onClose();
            navigate('/connexion');
        }
    };

    //Fonction pour retirer une offre du panier
    const reduireQuantite = (index) => {
        const nouveauPanier = [...panier];
        if (nouveauPanier[index].quantite > 1) {
            nouveauPanier[index].quantite -= 1;
        } else {
            nouveauPanier.splice(index, 1);
        }
        setPanier(nouveauPanier);
        localStorage.setItem('panier', JSON.stringify(nouveauPanier));
    };

    // Fonction pour calculer le prix total du panier
    const calculerPrixTotal = () => {
        return panier.reduce((total, offre) => total + offre.prix * offre.quantite, 0);
    };

    if (!isOpen) return null;

    return (
        <>
            <div className='overlay-panier' onClick={onClose}></div>
            <div className='fenetre-panier'>
                <button onClick={onClose} className='close-panier'>✖</button>
                <h1 className='panier-title'>Mon Panier</h1>
                <div className='panier-content'>
                {panier.length === 0 ? (
                    <p>Votre panier est vide.</p>
                ) : (
                    panier.map((offre, index) => (
                        <div key={index} className='panier-item'>
                            <h2>Offre {offre.nom}</h2>
                            <p>Prix : <span className='panier-price'>{offre.prix} €</span></p>
                            <div className='panier-quantite'>
                                <span>Quantité : {offre.quantite} </span>
                                <button onClick={() => reduireQuantite(index)} className='reduce-quantity'>
                                    <i className='fas fa-minus'></i>
                                </button>
                            </div>
                        </div>
                    ))
                )}
                    <p>Total : {calculerPrixTotal()} €</p>
                </div>
                <Bouton text='Payer' onClick={redirectionCompte} disabled={panier.length === 0} />
            </div>
        </>
    );
}

export default Panier;