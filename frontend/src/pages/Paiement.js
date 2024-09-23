import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './Paiement.css';
import Bouton from '../components/Bouton';

function Paiement() {

    const navigate = useNavigate();
    const [panier, setPanier] = useState([]);
    const [idUtilisateur, setIdUtilisateur] = useState(1);
    const [methodePaiement, setMethodePaiement] = useState('');

    const apiUrl = process.env.REACT_APP_API_URL || 'https://jeux-olympiques-5qjp.onrender.com/api';

    useEffect(() => {
        const panier = JSON.parse(localStorage.getItem('panier')) || [];
        if (panier.length === 0) {
            navigate('/');
            return;
        }
        setPanier(panier);

        const user = JSON.parse(localStorage.getItem('user'));
        if (user && user.id) {
            setIdUtilisateur(user.id);
        } else {
            alert("Veuillez vous connecter pour continuer le paiement.");
            navigate("/connexion");
        }
    }, [navigate]);

    // Fonction pour calculer le prix total du panier
    const calculerPrixTotal = () => {
        return panier.reduce((total, offre) => total + offre.prix * offre.quantite, 0);
    };

    const handleMethodePaiement = (event) => {
        setMethodePaiement(event.target.value);
    };

    // Requête API pour tenter de sauvegarder la commande
    const Payer = async (e) => {
        e.preventDefault();

        if (!idUtilisateur) {
            alert("ID Utilisateur introuvable");
            return;
        }

        try {
            const response = await fetch(`${apiUrl}/commandes?idUtilisateur=${idUtilisateur}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify(
                    panier.map(offre => ({
                        id: offre.id,
                        quantite: offre.quantite
                    }))
                )
            });

            if (response.ok) {
                const commandeSauvegardee = await response.json();
                const idCommande = commandeSauvegardee.id;
                localStorage.removeItem('panier');
                navigate(`/confirmation/${idCommande}`);
            } else {
                const erreur = await response.json();
                alert(`Erreur lors du paiement: ${erreur.message || 'Une erreur est survenue.'}`);
            }
        } catch (error) {
            console.error('Erreur de paiement :', error);
            alert('Une erreur est survenue lors du paiement.');
        }
    };

    return (
        <main>
            <section>
                <h2 className="section-title">Ma commande</h2>
                <div className="page-paiement">
                    <h1>Paiement (Mock)</h1>
                    <div className="panier-content">
                        {panier.map((offre, index) => (
                            <div key={index} className="panier-item">
                                <h2>Offre {offre.titre}</h2>
                                <p>Prix : {offre.prix} €</p>
                                <p>Quantité : {offre.quantite}</p>
                            </div>
                        ))}
                        <p className="total">Total : {calculerPrixTotal()} €</p>
                    </div>
                        <h3>Informations de Paiement</h3>
                        <div className="form-group">
                            <div className="payment-option">
                            <input
                                type="radio"
                                name="methode"
                                value="paypal"
                                onChange={handleMethodePaiement}
                            />
                            <label htmlFor="paypal">
                                <i className="fab fa-paypal"></i>
                            </label>
                            </div>
                            <div className="payment-option">
                            <input
                                type="radio"
                                name="methode"
                                value="visa"
                                onChange={handleMethodePaiement}
                            />
                            <label htmlFor="visa">
                                <i className="fab fa-cc-visa"></i>
                            </label>
                            </div>
                        </div>

                        {methodePaiement === "paypal" && (
                                <form>
                                    <div className="form-group">
                                        <label htmlFor="paypal-email">Email :</label>
                                        <input type="email" id="paypal-email" placeholder="Entrez votre email PayPal" required />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="paypal-mdp">Mot de passe :</label>
                                        <input type="password" id="paypal-mdp" placeholder="Entrez votre mot de passe PayPal" required />
                                    </div>
                                </form>
                        )}

                        {methodePaiement === "visa" && (
                                <form>
                                    <div className="form-group">
                                        <label htmlFor="card-number">Numéro de carte :</label>
                                        <input type="text" placeholder="1234 5678 9012 3456" required />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="expiry-date">Date d'expiration :</label>
                                        <input type="text" placeholder="MM/AA" required />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="cvv">CVV :</label>
                                        <input type="text" placeholder="123" required />
                                    </div>
                                </form>
                        )}
                    <Bouton text="Payer" onClick={Payer} disabled={methodePaiement === ''} />
                </div>
            </section>
        </main>
    );
}

export default Paiement;