import { useContext, useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import * as yup from "yup";
import { useFormik } from "formik";
import { AuthContext } from "./authContext";
import AxiosClient from "../../shared/plugins/axios";
import { Alert } from "bootstrap";
import { Container, Row, Col, Figure, Form } from "react-bootstrap";

export const LoginScreen = () => {
  const navigation = useNavigate();
  const { user, dispatch } = useContext(AuthContext);

  // Formik es una librería que nos permite manejar formularios de manera más sencilla
  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    // validationSchema es una propiedad que nos permite validar los campos del formulario
    validationSchema: yup.object().shape({
      username: yup
        .string()
        .required("Enter your username")
        .min(3, "Three characters minimum"),
      password: yup
        .string()
        .required("Enter your password")
        .min(3, "Three characters minimum"),
    }),
    // onSubmit es una propiedad que nos permite ejecutar código cuando el formulario es enviado
    onSubmit: async (values) => {
      try {
        const URI = "/auth/login"; // ruta de la API
        const response = await AxiosClient({
          URL: URI,
          method: "POST",
          data: JSON.stringify(values),
        });
        // si no hay error, guardamos el token en el localStorage y navegamos a la ruta raíz
        if (!response.error) {
          const action = {
            type: "LOGIN",
            payload: response.data,
          };
          dispatch(action); // actualizamos el estado global
          navigation("/products", { replace: true }); // navegamos a la ruta productos
        }
      } catch (error) {
        // si hay error, mostramos un mensaje de error
        Alert.fire({
          icon: "error",
          title: "Verify your credentials",
          text: "The username or password is incorrect",
          confirmButtoncolor: "#3085d6",
          confirmButtonText: "Ok",
        });
      }
    },
  });

  // handleReturn controla el botón de regreso
  const handleReturn = () => {
    navigation("/"); // navegamos a la ruta raíz
  };
  // useEffect es una función que nos permite ejecutar código cuando se renderiza el componente
  // en este caso, estamos cambiando el título de la página
  useEffect(() => {
    document.title = "MK | Login";
  }, []);

  if (user.isLogged) {
    return <Navigate to={"/"} />;
  }
  return (
    <>
      <section
        className="h-100 gradient-form"
        style={{ backgrouncolor: "#eee" }}
      >
        <Container className="py-5 h-100">
          <Row className="d-flex justify-content-center align-items-center h-100">
            <Col>
              <div className="card rounded-3 text-black">
                <Row className="g-0">
                  <Col className="col-lg-6">
                    <div className="card-body p-md-5 mx-md-4">
                      <div className="text-center">
                        <Figure.Image />
                        <h4 className="mt-1 mb-5 pb-1">Market</h4>
                      </div>
                      <Form onSubmit={formik.handleSubmit}>
                        <Form.Group className="form-outline mb-4">
                          <Form.Label htmlFor="username">
                            Username or email
                          </Form.Label>
                          <Form.Control
                            placeholder="email@example.com"
                            id="username"
                            autoComplete="off"
                            name="username"
                            value={formik.values.username}
                            onChange={formik.handleChange}
                          />
                        </Form.Group>
                      </Form>
                    </div>
                  </Col>
                  <Col className="col-lg-6 d-flex align-items-center gradient-custom-2"></Col>
                </Row>
              </div>
            </Col>
          </Row>
        </Container>
      </section>
    </>
  );
};
