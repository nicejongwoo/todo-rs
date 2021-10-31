import Todos from '../todo/Todos';
import AddTodo from '../todo/AddTodo';
import './App.css';
import React from 'react';
import { call } from "./service/ApiService";
import { Paper, List, Container } from "@material-ui/core";


class App extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            items: [
                // {"id": "0", "title": "test", "done": false}
            ]
        }
    }

    successMsg = () => {
        alert("성공");
    }

    add = (item) => {
        call("/todos", "POST", item).then((response) => {
            this.setState({ items: response.data });
        });
        this.successMsg();
    }
    
    delete = (item) => {
        call("/todos", "DELETE", item).then((response) => {
            console.log("delete::", response.data)
            this.setState({ items: response.data });
        });
        this.successMsg();
    }

    update = (item) => {
        call("/todos", "PUT", item).then((response) => {
            this.setState({ items: response.data });
        });
        this.successMsg();
    }

    render() {
        var todoItems = this.state.items.length > 0 && (
            <Paper style={{margin: 16}}>
                <List>
                    {this.state.items.map((item, idx)=> (
                        <Todos
                            item={item} 
                            key={item.id} 
                            delete={this.delete}
                            update={this.update}    
                        />
                    ))}
                </List>
            </Paper>
        );

        return (
            <div className="App">
                <Container maxWidth="md">
                    <AddTodo add={this.add} />
                    <div className="TodoList">{todoItems}</div>
                </Container>
            </div>
        );
        
    }

    componentDidMount() {
        call("/todos", "GET", null).then((response) => {
            this.setState({ items: response.data });
        });              
    }
}

export default App;
