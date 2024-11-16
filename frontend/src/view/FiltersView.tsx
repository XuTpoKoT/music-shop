import React, {useEffect} from 'react';
import '../style/style.css';
import {Box, FormControlLabel, Radio, RadioGroup, Typography} from "@mui/material";
import { CategoryResponse } from '../service/response/CategoryResponse';
import { CategoriesStyle, FiltersStyle } from '../style/style';

const FiltersView = (props: {
    categories: RequestState<CategoryResponse[]>,
    onComponentDidMount: () => void,
    handleCategoryChange : (event: React.ChangeEvent<HTMLInputElement>) => void,
}) => {
    useEffect(props.onComponentDidMount, [props.onComponentDidMount]);
    console.log("Render FiltersView")

    if (props.categories.status === 'idle' || props.categories.status === 'loading') {
        return <div>Загрузка...</div>;
    }

    if (props.categories.status === 'error') {
        return <div>Ошибка загрузки данных</div>
    }

    return (
        <Box sx={FiltersStyle}>
            <Box sx={CategoriesStyle}>
                <Typography
                    sx={{fontFamily: 'Arial, sans-serif',
                        fontColor: 'blue',
                        fontSize: '24px',}}
                >
                    Категории
                </Typography>
                <RadioGroup defaultValue={""} onChange={props.handleCategoryChange}>
                    <FormControlLabel
                        key={'all'}
                        value={""}
                        control={<Radio />}
                        label={'Все'}
                    />
                    {props.categories.data!.map(category => (
                        <FormControlLabel
                            key={category.id}
                            value={category.id}
                            control={<Radio />}
                            label={category.name}
                        />
                    ))}
                </RadioGroup>
            </Box>
        </Box>
    )
}

export default FiltersView;
