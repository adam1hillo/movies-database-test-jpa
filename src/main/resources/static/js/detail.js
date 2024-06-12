"use strict"

import {byId, setText, toon} from "./util.js";

const movieIdEnTitel = JSON.parse(sessionStorage.getItem("movie"));
setText("movieName", movieIdEnTitel.name);
await getDetails(movieIdEnTitel.id);
async function getDetails(id){
    const response = await fetch(`movies/${id}`);
    if (response.ok) {
        toon("movieName");
        toon("movieDetails");
        const movie = await response.json();
        const year = document.createElement("dd");
        year.innerText = movie.year;
        byId("year").appendChild(year);
        const ranking = document.createElement("dd");
        ranking.innerText = movie.ranking;
        byId("ranking").appendChild(ranking);
        const genres = movie.genres;
        for (const genre of genres) {
            const dd = document.createElement("dd");
            dd.innerText = genre;
            byId("genres").appendChild(dd);
        }
        const directors = movie.directors;
        for (const director of directors) {
            const dd = document.createElement("dd");
            dd.innerText = director;
            byId("directors").appendChild(dd);
        }
        await getRoles(movieIdEnTitel.id);
    } else {
        toon("storing");
    }
}
async function getRoles(id) {
    const response = await fetch(`movies/${id}/roles`);
    const roles = await response.json();
    for (const role of roles) {
        const dd = document.createElement("dd");
        dd.innerText = `${role.roleName}: ${role.actorFirstname} ${role.actorLastname} (${role.gender})`;
        byId("roles").appendChild(dd);
    }
}