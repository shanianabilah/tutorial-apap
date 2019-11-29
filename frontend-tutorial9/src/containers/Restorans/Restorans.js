import React, { Component } from 'react';
import classes from "./Restorans.module.css";
import Restoran from "../../components/Restoran/Restoran";
import axios from "../../axios-restoran";
import Modal from "../../components/UI/Modal/Modal";
import Button from "../../components/UI/Button/Button"
import Pagination from "../../components/UI/Pagination/Pagination";

class Restorans extends Component{
    constructor(props){
        super(props);
        this.state = {
            restorans:[],
            isCreate: false,
            isEdit: false,
            isLoading: true,
            nama:"",
            alamat:"",
            nomorTelepon:"",
            rating:"",
            currPage:1,
            perPage:5
        }
    }

    componentDidMount(){
        this.loadRestorans();
    }

    loadRestorans = async () => {
        const fetchedRestorans = [];
        const response = await axios.get("/restoran");
        for (let key in response.data){
            fetchedRestorans.push({
                ...response.data[key]
            });
        }
        this.setState({
            restorans: fetchedRestorans
        });
    }

    shouldComponentUpdate(nextProps, nextState){
        console.log("shouldComponentUpdate()");
        return true
    }

    loadingHandler = () => {
        const currentIsLoading = this.state.isLoading;
        this.setState({isLoading: !(currentIsLoading)});
        console.log(this.state.isLoading);
    }

    addRestoranHandler = () => {
        this.setState({isCreate:true});
    }

    editRestoranHandler(restoran){
        this.setState({
            isEdit: true,
            idRestoran: restoran.idRestoran,
            nama: restoran.nama,
            nomorTelepon: restoran.nomorTelepon,
            rating: restoran.rating,
            alamat: restoran.alamat
        })
    }

    canceledHandler = () => {
        this.setState({isCreate:false, isEdit: false});
    }

    // handler untuk perubahan setiap value pada form
    changeHandler = event => {
        // name dari prop name di input
        const {name,value} = event.target;
        this.setState({[name]:value});
    }

    handleInputChange = event => {
        const name = event.target.value;
        this.inputChange(name);
    }

    async inputChange(name){
        const fetchedRestorans = [];
        const response = await axios.get("/restoran");
        for (let key in response.data){
            if (response.data[key].nama.toLowerCase().startsWith(name.toLowerCase())){
                fetchedRestorans.push({
                    ...response.data[key]
                });
            }
        }
        this.setState({
            restorans: fetchedRestorans
        });
    }

    submitAddRestoranHandler = event => {
        event.preventDefault();
        this.setState({isLoading:true});
        this.addRestoran();
        this.canceledHandler();
        this.setState({nama:"", alamat:"", nomorTelepon:"", rating:""});
    }

    async addRestoran() {
        const restoranToAdd = {
            nama: this.state.nama,
            alamat: this.state.alamat,
            nomorTelepon: this.state.nomorTelepon,
            rating: this.state.rating
        };

        await axios.post("/restoran", restoranToAdd);
        await this.loadRestorans();
    }

    submitEditRestoranHandler = event => {
        console.log("editing")
        event.preventDefault();
        this.setState({ isLoading: true });
        this.editRestoran();
        this.canceledHandler();
        this.setState({nama:"", alamat:"", nomorTelepon:"", rating:""});
    }

    async editRestoran(){
        const restoranToEdit = {
            idRestoran: this.state.idRestoran,
            nama: this.state.nama,
            alamat: this.state.alamat,
            nomorTelepon: this.state.nomorTelepon,
            rating: this.state.rating
        };

        await axios.put("/restoran/" + this.state.idRestoran, restoranToEdit);
        await this.loadRestorans();
        this.canceledHandler();
    }

    async deleteRestoranHandler(restoranId){
        await axios.delete(`restoran/${restoranId}`);
        await this.loadRestorans();
    }

    renderForm(){
        const { isEdit } = this.state;
        return(
            <form>
                <input
                className={classes.Input}
                name="nama"
                type="text"
                placeholder="Nama"
                value={this.state.nama}
                onChange={this.changeHandler}
                />
                <input
                className={classes.Input}
                name="nomorTelepon"
                type="number"
                placeholder="Nomor Telepon"
                value={this.state.nomorTelepon}
                onChange={this.changeHandler}
                />
                <textarea
                className={classes.TextArea}
                name="alamat"
                type="text"
                placeholder="Alamat"
                value={this.state.alamat}
                onChange={this.changeHandler}
                />
                <input
                className={classes.Input}
                name="rating"
                type="number"
                placeholder="Rating"
                value={this.state.rating}
                onChange={this.changeHandler}
                />
                <Button btnType="Danger" onClick={this.canceledHandler}>
                    CANCEL
                </Button>
                <Button btnType="Success" onClick={isEdit ? this.submitEditRestoranHandler : this.submitAddRestoranHandler}>
                    SUBMIT
                </Button>
            </form>
        );
    }

    render(){
        console.log("render()");
        // pagination
        const lastIndex = this.state.currPage*this.state.perPage;
        const firstIndex = lastIndex - this.state.perPage;
        const restoransPerPage = this.state.restorans.slice(firstIndex,lastIndex);
        const paginate = (pageNumber) =>this.setState({currPage:pageNumber})
        
        return(
            <React.Fragment>
                <Modal show={this.state.isCreate || this.state.isEdit}
                    modalClosed={this.canceledHandler}>
                        {this.renderForm()}
                    </Modal>
                <div className={classes.Title}>All Restoran</div>
                <div className={classes.ButtonLayout}>
                    <button
                        className={classes.addRestoranButton}
                        onClick={this.addRestoranHandler}
                    >
                        + Add New Restoran
                    </button>
                </div>
                <div className={classes.SearchLayout}>
                    <input className={classes.SearchBar} onChange={this.handleInputChange} type="text" placeholder="Search..." aria-label="Search"/>
                </div>
                <div className={classes.Restorans}>
                    {this.state.restorans && 
                    restoransPerPage.map(restoran =>
                        <Restoran
                            key={restoran.id}
                            nama={restoran.nama}
                            alamat={restoran.alamat}
                            nomorTelepon={restoran.nomorTelepon}
                            edit={() => this.editRestoranHandler(restoran)}
                            delete={() => this.deleteRestoranHandler(restoran.idRestoran)}
                            />
                        )}
                </div>
                <div className={classes.PaginationLayout}>
                    <Pagination total={this.state.restorans.length} perPage={this.state.perPage} paginate={paginate}/>
                </div>
            </React.Fragment>
        )
    }
}

export default Restorans;