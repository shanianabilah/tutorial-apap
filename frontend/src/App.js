import React from 'react';
import List from "./components/List";
import dummyItems from "./items.json";
import EmptyState from "./components/EmptyState";

export default class App extends React.Component{
  constructor(props){
    super(props)
    this.state = { 
      favItems : [], 
      show : true 
    };
    this.handleToggle = this.handleToggle.bind(this)
  }

  handleItemClick = item => {
    //immutability
    const newItems = [...this.state.favItems];
    const newItem = {...item};
    //find index of item with id
    const targetInd = newItems.findIndex(it => it.id === newItem.id);
    if(targetInd < 0) newItems.push(newItem);
    // else newItems.splice(targetInd,1); //delete 1 item at index targetInd
    // schedule setState to update state with new favItems
    this.setState({favItems:newItems});
  }

  handleRemoveFavorite = item=> {
    //immutability 
    const newItems = [...this.state.favItems];
    const newItem = {...item};
    //find Index of item with id
    const targetInd = newItems.findIndex(it=>it.id===newItem.id);
    if(targetInd<0) newItems.push(newItem);
    else newItems.splice(targetInd, 1);
    this.setState({favItems: newItems});
  };

  handleToggle = () => {
    const {show} = this.state;
    this.setState({show : !show});
  }

  render(){
    const { favItems } = this.state;
    return (
      <div className="container-fluid">
        <h1 className="text-center">
          Welcome!
          <small>Class-based</small>
        </h1>
        <div className="container pt-3">
          <label className="container">
            <input type="checkbox" onChange={this.handleToggle}/> Show Favorite
            <span className="checkmark"></span>
          </label>
          <div className="row">
            <div className="col-sm">
              <List 
                title="Our Menu"
                items={dummyItems}
                onItemClick={this.handleItemClick}
            />
            </div>
            {!this.state.show ? 
            <div className="col-sm">
              {!this.state.favItems.length ? <EmptyState/>:
              <List
                title="My Favorite"
                items={favItems}
                onItemClick={this.handleRemoveFavorite}
              />}
            </div>
            : null}
          </div>
        </div>
      </div>
    );
  }
}
