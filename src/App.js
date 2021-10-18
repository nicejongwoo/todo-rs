import Todo from './Todo';
import './App.css';
import React from 'react';


class App extends React.Component {

    constructor(props){
        super(props);
        // this.state = {
        //     item: { id: 1, title: "hello react", done: true }
        // };
        this.state = {
            items: [
                { id: 1, title: "hello react1", done: true },
                { id: 2, title: "hello react2", done: false }
            ]
        }
    }

    
    render() {
        var todoItems = this.state.items.map((item, index) => (
            <Todo item={item} key={item.id} />
        ));

        return <div className="App">{todoItems}</div>;
        
    }
}

export default App;
