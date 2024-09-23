import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Header.css';
import Panier from './Panier';

function Header({ toggleModal, isOpen, panier, setPanier }) {
    const navigate = useNavigate();
    const [ouvert, setOuvert] = useState(false);

    const toggleMenu = () => {
        setOuvert(!ouvert);
    };

    const fermerMenu = () => {
        setOuvert(false);
    };

    useEffect(() => {
        const handleResize = () => {
            if (window.innerWidth > 768) {
                setOuvert(false);
            }
        };

        window.addEventListener('resize', handleResize);

        // Nettoyage de l'écouteur lors du démontage du composant
        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

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
                <div className='menu-principal'>
                    <div className='navlink' onClick={() => navigate('/')}><span>Accueil</span></div>
                    <div className='navlink' onClick={() => navigate('/offres')}><span>Offres</span></div>
                    <div className='navlink' onClick={redirectionCompte}><span>Mon Compte</span></div>
                    <button className='navlinkPanier' onClick={toggleModal}>
                        <i className="fas fa-shopping-cart"></i>Panier
                    </button>
                </div>
                
                <div className={`burger-menu ${ouvert ? 'open' : ''}`} onClick={toggleMenu}>
                    <div className='bar'></div>
                    <div className='bar'></div>
                    <div className='bar'></div>
                </div>

                <div className={`mobile-nav ${ouvert ? 'open' : ''}`}>
                    <div className='navlink' onClick={() => { navigate('/'); fermerMenu(); }}>Accueil</div>
                    <div className='navlink' onClick={() => { navigate('/offres'); fermerMenu(); }}>Offres</div>
                    <div className='navlink' onClick={() => { redirectionCompte(); fermerMenu(); }}>Mon Compte</div>
                    <div className='navlink' onClick={toggleModal}><i className="fas fa-shopping-cart"></i>Panier</div>
                </div>
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