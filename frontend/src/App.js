import React from "react";
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import Offres from './pages/Offres';
import Compte from './pages/Inscription';
import './App.css';

function App() {
    return (
        <Router>
            <Routes>
                <Route exact path='/' element={<Home />} />
                <Route path='/offres' element={<Offres />} />
                <Route path='/mon-compte' element={<Compte />} />
            </Routes>
        </Router>
    );
}

export default App;
