import React from 'react';

function UserStatus(props) {
    const myStatus = props.data.filter(user => {
            return user.name === localStorage.getItem('user');
        }
    );

    if (myStatus[0] != undefined) {

        console.log(myStatus[0].playerStatus);
    }
    return (
        <div>
            You are {localStorage.getItem('user')}
            <br/>
            Your status is

        </div>
    )
}

export default UserStatus;
