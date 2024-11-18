import {Box, Button, Typography} from "@mui/material";
import { AdditionalCharacteristicsBoxStyle, AdditionalCharacteristicsStyle, AddToCartButtonStyle, CharacteristicsStyle, DescriptionBoxStyle, MainCharacteristicsBoxStyle, ProductDetailsImageStyle, ProductDetailsMainStyle } from '@/style/ProductDetailsStyle';
import { ProductResponse } from "@/dto/ProductResponse";
import { RequestStatus } from "@/dto/RequestState";

const ProductDetailsView = (props: {
    status: RequestStatus,
    productDetails: ProductResponse | null,
    isAuth: boolean,
    onAddToCart: (productId: string) => void,
}) => {
    console.log("Render ProductDetailsView")

    if (props.status === RequestStatus.Idle || props.status === 'loading') {
        return <div>Загрузка...</div>
    }

    if (props.status === 'error') {
        return <div>Ошибка загрузки данных</div>
    }
    console.log('Status: ' + props.status)
    console.log('productDetails: ' + props.productDetails)

    const product = props.productDetails!

    return <Box sx={ProductDetailsMainStyle}>
                <Box sx={MainCharacteristicsBoxStyle}>
                    <img src={product.imgRef} alt={product.name} style={ProductDetailsImageStyle} />
                    <Box sx={AdditionalCharacteristicsStyle}>
                        <Typography sx={{ fontSize: '22px',fontWeight: 'bold'}}>
                            {`${product.name}`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`Производитель: ${product.manufacturerName}`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`Цвет: ${product.color}`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`Цена: ${product.price} Р`}</Typography>
                        {props.isAuth && (
                            <Button
                                sx={AddToCartButtonStyle}
                                onClick={() => props.onAddToCart(product.id)}
                            >
                                В корзину
                            </Button>
                        )}
                    </Box>
                </Box>
                <Box sx={AdditionalCharacteristicsBoxStyle}>
                    <Box sx={DescriptionBoxStyle}>
                        <Typography sx={{ fontSize: '24px',fontWeight: 'bold'}}>
                            {`Описание`}</Typography>
                        <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                            {`${product.description}`}</Typography>
                    </Box>
                    <Box sx={CharacteristicsStyle}>
                        <Typography sx={{ fontSize: '24px',fontWeight: 'bold'}}>
                            {`Характеристики`}</Typography>
                        {Object.entries(product.characteristics).map(([key, value]) => (
                            <Typography sx={{ fontSize: '18px',fontWeight: 'regular'}}>
                                {`${key}: ${value}`}</Typography>
                        ))}
                        
                    </Box>
                </Box>
            </Box>
}

export default ProductDetailsView;
