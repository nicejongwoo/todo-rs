import React from "react";
import "../../index.css";
import App from "../App";
import Login from "../../user/Login";
import SignUp from "../../user/SignUp";
import { BrowserRouter as Router, Switch, Route, Redirect } from "react-router-dom";
import { Box } from "@material-ui/core";
import { Typography } from "@material-ui/core";

function Copyright () {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {"Copyright c "}
            jongwoo, { new Date().getFullYear() }            
            {"."}
        </Typography>
    );
}

class AppRouter extends React.Component {
    render(){
        return (
            <div>
                <Router>
                    <div>
                        <Switch>
                            <Route 
                                path="/login" 
                                render={props => ( 
                                    localStorage.getItem("ACCESS_TOKEN") ? <Redirect to="/" /> : <Login />
                                )}
                            />                            
                            <Route 
                                path="/signup"
                                render={props => ( 
                                    localStorage.getItem("ACCESS_TOKEN") ? <Redirect to="/" /> : <SignUp />
                                )}
                            />
                            <Route 
                                path="/"
                                render={props => ( 
                                    localStorage.getItem("ACCESS_TOKEN") ? <App /> : <Redirect to="/login" />
                                )}   
                            />
                        </Switch>
                    </div>
                    <Box mt={5}>
                        <Copyright />
                    </Box>
                </Router>
            </div>
        );
    }
}

export default AppRouter;