import React from 'react';
import SignInPage from "./page/SignInPage";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import SignUpPage from "./page/SignUpPage";
import "bootstrap/dist/css/bootstrap.min.css";
import ProductsPage from "./page/ProductsPage";
import ProductDetailsPage from "./page/ProductDetailsPage";
// import ProductDetailsPage from 

const App = () => {
    console.log('Token:', localStorage.getItem('token'));

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<ProductsPage/>} />
                <Route path="/sign-up" element={<SignUpPage/>} />
                <Route path="/sign-in" element={<SignInPage/>} />
                <Route path="/products/:id" element={<ProductDetailsPage/>} />
            </Routes>
        </BrowserRouter>
    );
};

export default App;



