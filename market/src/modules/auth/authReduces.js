export const authReducer = (state = {}, action) => {
    switch (action.type) {
        case "LOGIN":
            return {
                ...action.payload, // ... = spread operator, se usa para separar los elementos de un array o propiedades de un objeto
                isLogged: true,
            };
        case "LOGOUT":
            return {
                isLogged: false,
            };
        default:
            return state;
    }
};