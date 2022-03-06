import React, {useContext} from "react";
import "./Navbar.css";
import {NavLink} from "react-router-dom";
import {AuthContext} from "../../Context/AuthContext";

export default function Navbar(){

    const {user, addUser} = useContext(AuthContext);

    return (

        <header className="mb-auto bg-dark text-white px-3 pt-3">
            <h3 className="float-md-start mb-0">Skill-Reader</h3>
            <nav className="nav nav-masthead justify-content-center">
                <NavLink className="nav-link"
                         activeClassName={"active"}
                         aria-current="page"
                         to={"/"}>Accueil</NavLink>

                {user === null &&
                    <>
                        <NavLink className="nav-link"
                                 activeClassName={"active"}
                                 to={"/login"}>Connexion</NavLink>
                        <NavLink className="nav-link"
                                 activeClassName={"active"}
                                 to={"/register"}>Inscription</NavLink>
                    </>
                }

                { user != null && user.role !== 'basic' &&

                    <>
                        <li className="nav-item dropdown" style={{marginLeft: "1rem"}}
                        >
                            <a className="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                Gestion
                            </a>
                            <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li>

                                    <NavLink className="dropdown-item"
                                             to={"/domain/add"}>Ajouter un domaine</NavLink>
                                </li>
                                <li>
                                    <NavLink className="dropdown-item"
                                             to={"/question/add"}>Ajouter une question</NavLink>
                                </li>
                                <li>
                                    <NavLink className="dropdown-item"
                                             to={"/skill/add"}>Ajouter une compétence</NavLink>
                                </li>
                            </ul>
                        </li>
                    </>
                }

                <NavLink className="nav-link"
                         activeClassName={"active"}
                         style={{marginLeft:"1rem"}}
                         to={"/contact"}>Contact</NavLink>

                {user != null &&
                    <>
                        <NavLink className="nav-link"
                                 activeClassName={"active"}
                                 to={"/profil"}>Profil</NavLink>
                        <NavLink className="nav-link"
                                 to={"/logout"}>Déconnexion</NavLink>
                    </>
                }

            </nav>
        </header>

    );

}