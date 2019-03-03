import React, {Component, Fragment} from 'react';
import axios from 'axios';
import SockJsClient from 'react-stomp'

import './Players.css';
import AddPlayer from "../../components/AddPlayer/AddPlayer";
import UserStatus from "../../components/UserStatus/UserStatus";


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
            })
            .catch(error => {
                console.log(error.response);
            });

    };



    componentDidMount() {
        this.getData();
        //this.timerId = setInterval(this.getData, 1000);

    };

    componentWillUnmount() {
        //clearInterval(this.timerId);
    }


    challenge = (name) => {

        const data = {
            name: name,
            typeOfGame: 'TicTacToe'
        };

        axios.post('http://localhost:8080/api/challenge', data)
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
                    <Fragment>
                        <button onClick={this.deleteData} className="reg-page__button">Logout</button>
                        <UserStatus data = {this.state.users}/>
                    </Fragment>
                )
            };
        }

        const readyToPlay = this.state.users.some(user =>  {
            return user.name === localStorage.getItem('user') && user.playerStatus === "Free and active player";
            }
        );

        const names = this.state.users.map(user => {
            if (user.name !== localStorage.getItem('user')) {
                if (user.playerStatus === "Free and active player" && localStorage.getItem('user') != null && readyToPlay) {
                    return (
                        <p key={user.name}><a href="#" onClick={() => {
                            this.challenge(user.name)
                        }}>  {user.name + ' - ' + user.playerStatus + ' ' + user.partnerName}</a></p>
                    )
                } else {
                    return (
                        <p key={user.name}>  {user.name + ' - ' + user.playerStatus + ' ' + user.partnerName}</p>
                    )
                }

            }

        });
        return (
            <div className="players__container">
                {logger()}
                {names}
                <SockJsClient
                    url='http://localhost:8080/getlist'
                    topics={['/topic/changeinlist']}
                    onMessage={(message) => {this.getData()}} />
            </div>
        );
    }

}

export default Players;