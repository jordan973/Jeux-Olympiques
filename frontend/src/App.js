import React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Offres from './pages/Offres';
import './App.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home />} />
                <Route path='/offres' element={<Offres />} />
                <Route path='/mon-compte' element={<div>Mon Compte</div>} />
            </Routes>
        </Router>
    );
}

export default App;
