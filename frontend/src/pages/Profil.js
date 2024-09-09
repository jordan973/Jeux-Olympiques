import React, { useEffect, useState } from 'react';
import './Profil.css';
import Bouton from '../components/Bouton.js';
import { useNavigate } from 'react-router-dom';

function Profil() {

    const [details, setDetails] = useState(null);
    const navigate = useNavigate();
        
        useEffect(() => {
            const infos = localStorage.getItem('user');
            console.log('Informations récupérées depuis localStorage:', infos);
            if (infos) {
                setDetails(JSON.parse(infos));
            }
        }, []);

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
                                <p>Email : {details.email}</p>
                            </>
                        ) : (
                            <p>Chargement des informations...</p>
                        )}
                    <Bouton text="Se déconnecter" onClick={deconnexion}/>
                </div>
            </section>
        </main>
        </div>
    );
}

export default Profil;