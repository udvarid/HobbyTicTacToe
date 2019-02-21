import React, {Component} from 'react';
import axios from 'axios';

class AddPlayer extends Component {

    state = {
        name: '',
        isValid: true,
        message: ''
    };

    handleChange = (event) => {
        this.setState(
            {
                [event.target.name]: event.target.value,
                isValid: true,
                message: ''
            }
        );
    };


    formSubmit = (event) => {
        event.preventDefault();
        const data = {name: ''};
        data.name = this.state.name;

        axios.post('http://localhost:8080/api/players/', data)
            .then(response => {
                localStorage.setItem('user', this.state.name);
            })
            .catch(error => this.validationHandler(error));


    };

    validationHandler = (error) => {
        const statePatch = {...this.state};
        for (let fieldError of error.response.data.fieldErrors) {
            statePatch.isValid = false;
            statePatch.message = fieldError.message;
        }

        this.setState(statePatch);
    };

    render() {
        return (
            <div>
                <form onSubmit={this.formSubmit}>

                    <label htmlFor="name">Name:</label>
                    <br/>
                    <input type="text"
                           className={this.state.isValid ? "form-control" : "form-control is-invalid"}
                           id="name"
                           name="name"
                           value={this.state.name}
                           onChange={this.handleChange}
                           placeholder="Your name"/>
                    <br/>
                    <small className="form-text error-block">{this.state.message}</small>
                    <button type="submit" className="reg-page__button">Register</button>
                </form>
            </div>
        );
    }
}

export default AddPlayer;

