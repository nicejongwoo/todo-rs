import Todo from './Todo';
import './App.css';
import React from 'react';
import { Paper, List } from "@material-ui/core";


class App extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            items: [
                { id: 1, title: "hello react1", done: true },
                { id: 2, title: "hello react2", done: false }
            ]
        }
    }

    
    render() {
        var todoItems = this.state.items.length > 0 && (
            <Paper stype={{margin: 16}}>
                <List>
                    {this.state.items.map((item, idx)=> (
                        <Todo item={item} key={item.id}/>
                    ))}
                </List>
            </Paper>
        );

        return <div className="App">{todoItems}</div>;
        
    }
}

export default App;
