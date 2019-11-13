import React from 'react';
import List from "./components/List";
import dummyItems from "./items.json";

export default class App extends React.Component{
  state = {
    favItems: []
  };

  handleItemClick = item => {
    //immutability
    const newItems = [...this.state.favItems];
    const newItem = {...item};
    //find index of item with id
    const targetInd = newItems.findIndex(it => it.id === newItem.id);
    if(targetInd < 0) newItems.push(newItem);
    else newItems.splice(targetInd,1); //delete 1 item at index targetInd
    // schedule setState to update state with new favItems
    this.setState({favItems:newItems});
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
          <div className="row">
            <div className="col-sm">
              <List 
                title="Our Menu"
                items={dummyItems}
                onItemClick={this.handleItemClick}
            />
            </div>
            <div className="col-sm">
              <List
                title="My Favorite"
                items={favItems}
                onItemClick={this.handleItemClick}
              />
            </div>
          </div>
        </div>
      </div>
    );
  }
}
