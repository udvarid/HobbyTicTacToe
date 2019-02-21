import React, {Component} from 'react';
import axios from 'axios';

import './Players.css';
import AddPlayer from "../../components/AddPlayer/AddPlayer";


class Players extends Component {

    state = {
        names: []
    };

    getData = () => {
        axios.get('http://localhost:8080/api/players')
            .then(response => {
                this.setState({names: response.data});
            })
            .catch(error => console.warn(error));


    };

    deleteData = () => {
        localStorage.removeItem('user');
        axios.delete('http://localhost:8080/api/players')
            .then(response => {
                console.log(response);
                this.getData();
            })
            .catch(error => {
                console.log(error.response);
            });

    };

    componentDidMount() {
        this.getData();
        this.timerId = setInterval(this.getData, 1000);
    };

    componentWillUnmount() {
        clearInterval(this.timerId);
    }

    challenge = (name) => {
      alert("I challenge you " + name);
    };

    render() {

        let logger = () => {
            return (
                <AddPlayer/>
            )

        };

        if (localStorage.getItem('user') != null) {
            logger = () => {
                return (
                    <button onClick={this.deleteData} className="reg-page__button">Logout</button>
                )

            };
        }

        const names = this.state.names.map(name => {
            if (name.name !== localStorage.getItem('user')) {
                return (
                    <p key={name.name}><a href="#" onClick={() => {this.challenge(name.name)}}>  {name.name}</a></p>
                )
            }

        });
        return (
            <div className="players__container">
                {logger()}
                {names}
            </div>
        );
    }

}

export default Players;