import * as React from 'react';
import Stack from '@mui/material/Stack';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';

const Alert = React.forwardRef(function Alert(props, ref) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />
});

class CustomSnackBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: false
        }
    }

    render() {
        return (
            <Stack spacing={2} sx={{ width: '100%' }}>
                <Snackbar open={this.props.open} autoHideDuration={2000} onClose={this.props.handleClose}>
                    <Alert onClose={this.props.handleClose} severity="success" sx={{ width: '100%' }}>
                        {this.props.message}
                    </Alert>
                </Snackbar>
            </Stack>
        );
    }



}

export default CustomSnackBar;
