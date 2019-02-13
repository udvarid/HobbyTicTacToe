
import React, {Component} from 'react';
import axios from 'axios';

import './Players.css';
import AddPlayer from "../../components/AddPlayer/AddPlayer";



class Players extends Component {

    state = {
        names : []
    }

    getData = () => {
        axios.get('http://localhost:8080/api/players')
            .then(response => {
                this.setState({names: response.data});
            })
            .catch(error => console.warn(error));


    };

    componentDidMount() {
        this.getData();
        this.timerId = setInterval(this.getData, 10000);
    };

    componentWillUnmount() {
        clearInterval(this.timerId);
    }

    render() {

        const names = this.state.names.map(name => {
            return (
                <p key={name.name}> {name.name}
                </p>
            )
        });
        return (
            <div className="players__container">
                <AddPlayer />
                {names}
            </div>
        );
    }

}

export default Players;