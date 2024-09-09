import React from "react";
import './Offres.css';
import Billet from "../components/Billet.js";

function Offres({ajouterAuPanier, visible, message, type, onClose}) {
    return (
        <div className="offres">
            <main>
                <Billet ajouterAuPanier={ajouterAuPanier} visible={visible} message={message} type={type} onClose={onClose} />
            </main>
        </div>
    );
}

export default Offres;