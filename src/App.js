import Todo from './Todo';
import './App.css';
import React from 'react';


class App extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            item: { id: 1, title: "hello react", done: true }
        };
    }

    render() {
        return (
          <div className="App">
              <Todo item={this.state.item}/>
          </div>
        )
    }
}

export default App;
