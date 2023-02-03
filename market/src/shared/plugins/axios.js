import * as instance from "axios";

const AxiosClient = instance.create({
  baseURL: "http://localhost:8080/api-firstapp",
});

const requestHandler = (request) => {
  request.headers["Accept"] = "application/json";
  request.headers["Content-Type"] = "application/json";

  const session = JSON.parse(localStorage.getItem("user") || null); // Si no existe el usuario, se envia null
  if (session?.isLogged)
    request.headers["Authorization"] = `Bearer ${session.token}`; // Si existe el usuario, se envia el token
  return request;
};

const errorResponseHandler = (error) => {
  return Promise.reject(error); // Retorna una promesa rechazada con el valor de error
};

const successResponseHandler = (response) => Promise.resolve(response.data); // Retorna una promesa resuelta con el valor de response

// Interceptor para las peticiones, se ejecuta antes de enviar la peticion
AxiosClient.interceptors.request.use(requestHandler, (error) =>
  Promise.reject(error)
);

// Interceptor para las respuestas, se ejecuta antes de recibir la respuesta
AxiosClient.interceptors.response.use(successResponseHandler, (error) =>
  errorResponseHandler(error)
);

export default AxiosClient;