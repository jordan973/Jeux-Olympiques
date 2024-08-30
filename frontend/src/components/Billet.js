import React from 'react';
import './Billet.css';
import Bouton from './Bouton';

function Billet(){

    const offers = [
        {
            image: '/img/solo.jpg',
            title:'Solo',
            description: 'L\'offre Solo est idéale pour les passionnés de sport souhaitant vivre les Jeux Olympiques en toute liberté.',
            price: '49€'
        },
        {
            image: '/img/duo.jpg',
            title:'Duo',
            description: 'Vivez l\'excitation des Jeux Olympiques à deux avec l\'offre Duo, à un tarif préférentiel pour partager des moments inoubliables.',
            price: '89€'
        },
        {
            image: '/img/family.jpg',
            title:'Familiale',
            description: 'Vivez les Jeux Olympiques en famille avec l\'offre Familiale, conçue pour 4 personnes, et profitez de tarifs réduits pour des moments inoubliables ensemble.',
            price: '199€'
        }
    ]

    return(
            <section>
                <h2 className="section-title">Les Offres</h2>
                <div className='offers-grid'>
                    {offers.map((offer, index) => (
                        <div key={index} className='offer-card'>
                            <img src={offer.image} alt='Offre' className='offer-image'/>
                            <h1 className='offer-title'>{offer.title}</h1>
                            <p className='offer-description'>{offer.description}</p>
                            <h3 className='offer-price'>{offer.price}</h3>
                            <Bouton text="Commander"/>
                        </div>
                    ))}
                </div>
            </section>
    )
}

export default Billet;