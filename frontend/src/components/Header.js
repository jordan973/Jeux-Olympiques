import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Header.css';

function Header(){

    const navigate = useNavigate();

    const redirectionCompte = () => {
        
        const token = localStorage.getItem('token');

        if (token) {
            navigate('/profil')
        } else {
            navigate('/mon-compte');
        }
    }


    return(
        <header>
            <nav className='navbar'>
                <div className='navlink' onClick={() => navigate('/')}><span>Accueil</span></div>
                <div className='navlink' onClick={() => navigate('/offres')}><span>Offres</span></div>
                <div className='navlink' onClick={redirectionCompte}><span>Mon Compte</span></div>
            </nav>
        </header>
    )
}

export default Header;