import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Header.css';
import Panier from './Panier';

function Header({ toggleModal, isOpen, panier, setPanier }) {
    const navigate = useNavigate();

    //Fonction pour rediriger l'utilisateur vers son profil ou la page d'inscription en fonction de s'il est connecté ou non
    const redirectionCompte = () => {
        const token = localStorage.getItem('token');
        if (token) {
            navigate('/profil');
        } else {
            navigate('/mon-compte');
        }
    };

    return (
        <header>
            <nav className='navbar'>
                <div className='navlink' onClick={() => navigate('/')}><span>Accueil</span></div>
                <div className='navlink' onClick={() => navigate('/offres')}><span>Offres</span></div>
                <div className='navlink' onClick={redirectionCompte}><span>Mon Compte</span></div>
                <button className='navlinkPanier' onClick={toggleModal}>
                    <i className="fas fa-shopping-cart"></i> Panier
                </button>
            </nav>
            <Panier
                isOpen={isOpen}
                onClose={toggleModal}
                panier={panier}
                setPanier={setPanier}
            />
        </header>
    );
}

export default Header;