import Todo from './Todo';
import AddTodo from "./AddTodo";
import './App.css';
import React from 'react';
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

    add = (item) => {
        const thisItems = this.state.items;
        item.id = "ID-" + thisItems.length;
        item.done = false;
        thisItems.push(item);
        this.setState({ items: thisItems });
        console.log("items: ", this.state.items);
    }
    
    delete = (item) => {
        const thisItems = this.state.items;
        const newItems = thisItems.filter(e => e.id !== item.id);
        this.setState({ items: newItems }, () => {
            //디버깅 콜백
            console.log("update items: ", this.state.items);
        })
    }

    render() {
        var todoItems = this.state.items.length > 0 && (
            <Paper style={{margin: 16}}>
                <List>
                    {this.state.items.map((item, idx)=> (
                        <Todo item={item} key={item.id} delete={this.delete}/>
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
        const requestOptions = {
            method: "GET",
            headers: {"Content-Type": "application/json"},
        };

        fetch("http://localhost:8080/todos", requestOptions)
        .then((response) => response.json())
        .then(
            (response) => {
                console.log(response.data)
                this.setState({
                    items: response.data
                });
            },
            (error) => {
                this.setState({
                    error
                });
            }
        );
    }
}

export default App;
