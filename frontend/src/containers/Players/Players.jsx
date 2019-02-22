import React, {Component} from 'react';
import axios from 'axios';

import './Players.css';
import AddPlayer from "../../components/AddPlayer/AddPlayer";


class Players extends Component {

    state = {
        users: []
    };

    getData = () => {
        axios.get('http://localhost:8080/api/players')
            .then(response => {
                this.setState({users: response.data});
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

        const data = {
            challenged: name,
            gameType : 'TicTacToe'
        };

        axios.post('http://localhost:8080/api/players/challenge', data)
            .then(response => {
                console.log(response)
            })
            .catch(error => console.log(error));
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

        const names = this.state.users.map(user => {
            if (user.name !== localStorage.getItem('user')) {
                if (user.playerStatus === "Free and active player") {
                    return (
                        <p key={user.name}><a href="#" onClick={() => {
                            this.challenge(user.name)
                        }}>  {user.name + ' - ' + user.playerStatus}</a></p>
                    )
                } else {
                    return (
                        <p key={user.name}>  {user.name + ' - ' + user.playerStatus}</p>
                    )
                }

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