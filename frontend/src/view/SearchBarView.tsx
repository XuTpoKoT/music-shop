import React from 'react';
import '../style/style.css';
import {Box, IconButton, TextField} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import { SearchBarStyle } from '../style/style';

const SearchBarView = (props: {
    handleInputChange: (e: React.ChangeEvent<HTMLInputElement>) => void,
    handleSubmit: (e: React.FormEvent) => void,
}) => {
    console.log("Render SearchBarView")
    return (
        <Box sx={SearchBarStyle}>
            <form onSubmit={props.handleSubmit}>
                <TextField
                    id="search-bar"
                    className="text"
                    onInput={props.handleInputChange}
                    label="Введите название товара"
                    variant="outlined"
                    placeholder="Search..."
                    size="small"
                />
                <IconButton type="submit" aria-label="search">
                    <SearchIcon style={{ fill: "blue" }} />
                </IconButton>
            </form>
        </Box>
    )
}

export default SearchBarView;
