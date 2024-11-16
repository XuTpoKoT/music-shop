import React from 'react';
import {Box} from '@mui/material';
import '../style/style.css';
import { NavBarStyle } from '../style/style';
import CiteName from '../widget/CiteName';
import MenuPresenter from '../presenter/MenuPresenter';

const NavBar = () => {
    return (
        <Box sx={NavBarStyle}>
            <CiteName></CiteName>
            <MenuPresenter></MenuPresenter>
        </Box>
    );
};

export default NavBar;
