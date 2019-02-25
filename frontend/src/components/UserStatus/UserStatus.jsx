import React, {Fragment} from 'react';
import axios from "axios";

function UserStatus(props) {

    const myStatus = props.data.find(user =>
        user.name === localStorage.getItem('user')
    );

    const acceptInvitation = () => {
        axios.get('http://localhost:8080/api/challenge/accept')
            .then(response => {
                console.log(response)
            })
            .catch(error => console.warn(error));

    };

    const declineInvitation = () => {
        axios.get('http://localhost:8080/api/challenge/cancel')
            .then(response => {
                console.log(response)
            })
            .catch(error => console.warn(error));
    };

    const buttonToChallenge = () => {

        if (myStatus !== undefined && myStatus.playerStatus === 'Invited by') {
            return (
                <Fragment>
                    <button onClick={acceptInvitation} className="reg-page__button">Accept</button>
                    <button onClick={declineInvitation} className="reg-page__button">Decline</button>
                </Fragment>
            );
        } else if (myStatus !== undefined && myStatus.playerStatus === 'Invited') {
            return (
                <button onClick={declineInvitation} className="reg-page__button">Decline</button>
            );
        } else {
            return (
                <p></p>
            );
        }

    };

    let playerStatus = '';
    let opponentName = '';
    if (myStatus !== undefined) {
        playerStatus = myStatus.playerStatus;
        opponentName = myStatus.partnerName;
    }

    return (
        <div>
            <p>------------------------------</p>
            You are {localStorage.getItem('user')}
            <br/>
            Your status is : {playerStatus + ' ' + opponentName}
            <br/>
            {buttonToChallenge()}
            <p>------------------------------</p>

        </div>
    )
}

export default UserStatus;
