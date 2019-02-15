import React, {Component} from 'react';
import axios from 'axios';

import './Players.css';
import AddPlayer from "../../components/AddPlayer/AddPlayer";
import {Link} from "react-router-dom";


class Players extends Component {

    state = {
        names: []
    }

    getData = () => {
        axios.get('http://localhost:8080/api/players')
            .then(response => {
                this.setState({names: response.data});
            })
            .catch(error => console.warn(error));


    };

    deleteData = (name) => {

        axios.delete('http://localhost:8080/api/players/' + name)
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

    render() {

        const names = this.state.names.map(name => {
            const nameToLink = "javascript:deleteData(" + name.name + ")";
            console.log(nameToLink);
            return (
                <p key={name.name}>
                    <a key={name.name} href="#" onClick={() => {
                        this.deleteData(name.name)
                    }}>{name.name}</a>
                </p>
            )
        });
        return (
            <div className="players__container">
                <AddPlayer/>
                {names}
            </div>
        );
    }

}

export default Players;