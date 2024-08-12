import React from 'react';
import Slider from 'react-slick';
import "slick-carousel/slick/slick.css"; 
import "slick-carousel/slick/slick-theme.css";
import './Epreuve.css';

function Epreuve(){

    const settings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay: true,
      autoplaySpeed: 4000,
      pauseOnHover: false
  }
  
    const events = [
        {
          title: 'Natation',
          description: 'La natation est une compétition aquatique où les athlètes s\'affrontent sur différentes distances et styles de nage, comme la nage libre, le dos, la brasse et le papillon.',
          image: '/img/natation.jpg',
        },
        {
          title: 'Course à Pied',
          description: 'La course à pied est une compétition où les athlètes courent sur des distances variées, allant du sprint de 100 mètres aux courses de fond comme le marathon.',
          image: '/img/course.jpg',
        },
        {
          title: 'Saut en Longueur',
          description: 'Le saut en longueur est une discipline d\'athlétisme où les athlètes prennent de l\'élan pour sauter le plus loin possible dans une zone de sable.',
          image: '/img/longueur.jpg',
        },
        {
          title: 'Saut en Hauteur',
          description: 'Le saut en hauteur est une épreuve d\'athlétisme où les athlètes doivent franchir une barre placée à une certaine hauteur sans la faire tomber.',
          image: '/img/hauteur.jpg',
        },
        {
          title: 'Lancer du Javelot',
          description: 'Le lancer du javelot est une épreuve d\'athlétisme où les athlètes doivent lancer un javelot le plus loin possible.',
          image: '/img/lancer.jpg',
        },
        {
          title: 'Cyclisme sur Route',
          description: 'Le cyclisme sur route est une compétition où les cyclistes parcourent de longues distances sur des routes goudronnées, souvent avec des étapes de montagne.',
          image: '/img/cyclisme.jpg',
        },
        {
          title: 'Gymnastique Artistique',
          description: 'La gymnastique artistique est un sport où les athlètes exécutent des routines sur différents agrès comme le sol, la poutre, les barres asymétriques et les anneaux.',
          image: '/img/gymnastique.jpg',
        },
        {
          title: 'Escrime',
          description: 'L\'escrime est un sport de combat où les athlètes s\'affrontent en duel avec des épées, des fleurets ou des sabres, en essayant de toucher leur adversaire sans être touchés.',
          image: '/img/escrime.jpg',
        },
        {
          title: 'Haltérophilie',
          description: 'L\'haltérophilie est un sport où les athlètes tentent de soulever le plus de poids possible en utilisant deux techniques : l\'arraché et l\'épaulé-jeté.',
          image: '/img/halterophilie.jpg',
        },
      ];

    return(
      <div className='test'>
            <div className='slider-container'>
                <Slider {...settings}>
                    <div>
                        <img src="/img/billets.jpg" alt="Billets" className='header-image' />
                    </div>
                    <div>
                        <img src="/img/olympics-paris.jpg" alt="Épreuves" className='header-image' />
                    </div>
                    <div>
                        <img src="./img/nage.jpg" alt="Plus" className='header-image' />
                    </div>
                </Slider>
            </div>
            <div className='paris-image-container'>
                <img src="/img/paris.jpg" alt="Paris 2024" className='paris-image' />
            </div>
            <main>
              <section className='intro-section'>
                <h2 className="section-title">L'Histoire</h2>
                <div className='intro-content'>
                  <p className='intro-description'>Les Jeux Olympiques trouvent leur origine dans la <b>Grèce antique</b>, où ils étaient organisés en l'honneur de Zeus à Olympie, d'où ils tirent leur nom. Ces premiers jeux, qui remontent à <b>776 av. J.-C.</b>, étaient à l'époque un événement <b>religieux et sportif majeur</b>, réunissant les cités-États grecques dans une trêve sacrée pour célébrer la force, l'endurance et l'esprit de compétition des athlètes.<br/><br/>
                  Après plusieurs siècles de gloire, les Jeux Olympiques antiques furent interdits en <b>393 apr. J.-C.</b> par l'empereur romain <b>Théodose Ier</b>, marquant ainsi la fin de cette tradition. Il fallut attendre le <b>XIXe siècle</b> pour que l'idée des Jeux Olympiques soit ressuscitée par le baron <b>Pierre de Coubertin</b>, un éducateur français visionnaire. En 1896, les premiers Jeux Olympiques modernes furent organisés à <b>Athènes</b>, renouant avec les racines grecques de la compétition tout en intégrant une perspective internationale.<br/><br/>
                  Depuis lors, les Jeux Olympiques ont évolué pour devenir l'un des événements les plus emblématiques du monde, accueillant des milliers d'athlètes représentant presque tous les pays du globe. Les Jeux sont aujourd'hui un <b>symbole de paix, d'unité et de fraternité</b>, mettant en avant non seulement les <b>prouesses physiques</b>, mais aussi <b>l'esprit de collaboration et de respect mutuel</b> entre les nations.</p>
                  <img src='/img/history.jpg' alt='Histoire des JO' className='histoire-image' />
                </div>
              </section>
              <section className='events-section'>
                <h2 className="section-title">Les Épreuves</h2>
                <div className="events-grid">
                    {events.map((event, index) => (
                    <div key={index} className="event-card">
                        <img src={event.image} alt={event.title} className="event-image" />
                        <h3 className="event-title">{event.title}</h3>
                        <p className="event-description">{event.description}</p>
                    </div>
                    ))}
                </div>
              </section>  
            </main>      
      </div>
    )
}

export default Epreuve;