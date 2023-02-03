import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

const Alert = withReactContent(Swal);

export const confirmMsg = "Wait for the action to be completed";
export const confirmTitle = "Are you sure?";
export const successMsg = "Action completed successfully";
export const successTitle = "Success";
export const errorMsg = "An error has occurred while performing the action";
export const errorTitle = "Error";

export default Alert;