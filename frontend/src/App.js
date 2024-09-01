import React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Offres from './pages/Offres';
import Inscription from './pages/Inscription';
import './App.css';
import Connexion from "./pages/Connexion";

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home />} />
                <Route path='/offres' element={<Offres />} />
                <Route path='/mon-compte' element={<Inscription />} />
                <Route path='/connexion' element={<Connexion />} />
            </Routes>
        </Router>
    );
}

export default App;
