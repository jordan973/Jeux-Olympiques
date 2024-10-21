import React, { useState } from 'react';
import './Formulaire.css';
import Bouton from './Bouton';

function Formulaire({ title, nameFields, submit, buttonText, linkDesc, linkHref, linkText }) {
  const [prenom, setPrenom] = useState('');
  const [nom, setNom] = useState('');
  const [email, setEmail] = useState('');
  const [motDePasse, setMotDePasse] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();

    const user = {
      prenom: prenom,
      nom: nom,
      email: email,
      motDePasse: motDePasse,
    };

    try {
      await submit(user, setErrorMessage);
    } catch (error) {
      setErrorMessage("Une erreur est survenue.");
    }
  };

  return (
    <section>
      <h2 className="section-title">Mon Compte</h2>
      <form onSubmit={handleSubmit} className="form-container">
        <h1>{title}</h1>
        {nameFields && (
          <>
            <div>
              <label>Prénom :</label>
              <input
                type="text"
                value={prenom}
                onChange={(e) => setPrenom(e.target.value)}
                required
              />
            </div>
            <div>
              <label>Nom :</label>
              <input
                type="text"
                value={nom}
                onChange={(e) => setNom(e.target.value)}
                required
              />
            </div>
          </>
        )}
        <div>
          <label>Email :</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Mot de passe :</label>
          <input
            type="password"
            value={motDePasse}
            onChange={(e) => setMotDePasse(e.target.value)}
            required
          />
        </div>

        {errorMessage && <p className="error-message">{errorMessage}</p>}
        
        <Bouton type="submit" text={buttonText} />
        <p className="connexion-link">
          {linkDesc}
          <a href={linkHref}>{linkText}</a>
        </p>
      </form>
    </section>
  );
}

export default Formulaire;