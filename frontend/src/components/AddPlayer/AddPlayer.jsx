import React, {Component} from 'react';
import axios from 'axios';

class AddPlayer extends Component{

    state = {
        name : ''
    };

    handleChange = (event) => {
        this.setState(
            {[event.target.name] : event.target.value}
        );
    };


    formSubmit = (event) => {
        event.preventDefault();
        const data = {...this.state};

        axios.post('http://localhost:8080/api/players/', data)
            .then(response => {

            })
            .catch(error => console.warn(error))
    };

    render() {
        return (
            <div>
                <form onSubmit={this.formSubmit}>

                    <label htmlFor="name">Name:</label>
                    <br/>
                    <input type="text"
                           id="name"
                           name="name"
                           value={this.state.name}
                           onChange={this.handleChange}
                           placeholder="Your name"
                           className="reg-page__input-field--goal"/>
                    <br/>
                    <button type="submit" className="reg-page__button">Register</button>
                </form>
            </div>
        );
    }
}

export default AddPlayer;

