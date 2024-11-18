import React, {useEffect} from 'react';
import {Box, FormControlLabel, Radio, RadioGroup, Typography} from "@mui/material";
import { RequestStatus } from '@/dto/RequestState';
import { CategoryResponse } from '@/dto/CategoryResponse';
import { FiltersStyle, CategoriesStyle } from '@/style/style';

const FiltersView = (props: {
    status: RequestStatus,
    categories: CategoryResponse[] | null,
    onComponentDidMount: () => void,
    handleCategoryChange : (event: React.ChangeEvent<HTMLInputElement>) => void,
}) => {
    useEffect(props.onComponentDidMount, [props.onComponentDidMount]);
    console.log("Render FiltersView")

    if (props.status === RequestStatus.Idle || props.status === RequestStatus.Loading) {
        return <div>Загрузка...</div>;
    }

    if (props.status === RequestStatus.Error) {
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
                    {props.categories?.map(category => (
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
