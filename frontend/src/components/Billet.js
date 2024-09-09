import React from 'react';
import './Billet.css';
import Bouton from './Bouton';
import Alerte from './Alerte';

function Billet({ajouterAuPanier, visible, message, type, onClose}) {

    const offres = [
        {
            id: 1,
            image: '/img/solo.jpg',
            titre: 'Solo',
            description: 'L\'offre Solo est idéale pour les passionnés de sport souhaitant vivre les Jeux Olympiques en toute liberté.',
            prix: '49'
        },
        {
            id: 2,
            image: '/img/duo.jpg',
            titre: 'Duo',
            description: 'Vivez l\'excitation des Jeux Olympiques à deux avec l\'offre Duo, à un tarif préférentiel pour partager des moments inoubliables.',
            prix: '89'
        },
        {
            id: 3,
            image: '/img/family.jpg',
            titre: 'Familiale',
            description: 'Vivez les Jeux Olympiques en famille avec l\'offre Familiale, conçue pour 4 personnes, et profitez de tarifs réduits.',
            prix: '169'
        }
    ];

    return (
        <section>
             {visible && (
                <Alerte message={message} type={type} onClose={onClose} />
            )}
            <h2 className="section-title">Les Offres</h2>
            <div className='offers-grid'>
                {offres.map((offre) => (
                    <div key={offre.id} className='offer-card'>
                        <img src={offre.image} alt='Offre' className='offer-image'/>
                        <h1 className='offer-title'>{offre.titre}</h1>
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