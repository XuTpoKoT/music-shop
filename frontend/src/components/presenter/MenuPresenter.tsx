import MenuView from '@/components/view/MenuView';
import useAuthStore from '@/store/AuthStore';

const MenuPresenter = () => {
    const isAuth = useAuthStore((state) => state.isAuth);
    const setIsAuth = useAuthStore((state) => state.setIsAuth);
    const onClickSignOut = () => {
        setIsAuth(false);
        localStorage.removeItem('token');
    }

    return (
        <MenuView
            isAuth={isAuth}
            onClickSignOut={onClickSignOut}
        ></MenuView>
    );
};

export default MenuPresenter;
