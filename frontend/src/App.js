import React, {Component} from 'react';
import {BrowserRouter, Route, Link, Redirect} from 'react-router-dom';

import './App.css';
import Players from "./containers/Players/Players";
import axios from "axios";

class App extends Component {

    
   
    render() {
        return (
            <BrowserRouter>
                <div>
                    <nav className="navbar navbar-dark bg-dark navbar-expand-lg fixed-top">
                        <button type="button" className="navbar-toggler collapsed" data-toggle="collapse"
                                data-target="#navbar"
                                aria-expanded="false" aria-controls="navbar" aria-label="Toggle navigation">
                        </button>
                        <div id="navbar" className="navbar-collapse collapse">
                            <ul className="navbar-nav mr-auto mt-2 mt-lg-0">
                                <li className="nav-item"><Link className="nav-link" to="/players">players</Link></li>
                            </ul>
                        </div>
                    </nav>

                    <div className="App">
                        <Route path='/players' component={Players}/>
                        <Redirect from="/" to="/players"/>
                    </div>
                </div>

            </BrowserRouter>
        );
    }
}

export default App;


