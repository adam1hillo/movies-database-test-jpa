"use strict"

import {byId, toon, verberg, verwijderChildElementenVan} from "./util.js";

const response = await fetch("movies/years");
if (response.ok) {
    const ul = byId("years");
    const years = await response.json();
    for (const year of years) {
        const li = document.createElement("li");
        const hyperlink = document.createElement("a");
        hyperlink.innerText = year;
        hyperlink.href = "#";
        hyperlink.onclick = async function () {
            verbergAlleMoviesEnFouten()
            await findMoviesFromYear(year);
        }
        li.appendChild(hyperlink);
        ul.appendChild(li);
    }
} else {
    toon("storing");
}

function verbergAlleMoviesEnFouten() {
    verberg("year");
    verberg("moviesTable");
    verberg("storing");
}

async function findMoviesFromYear(year) {
    const response = await fetch(`movies?year=${year}`);
    if (response.ok) {
        byId("year").innerText = year;
        toon("year");
        toon("moviesTable");
        const movies = await response.json();
        const moviesBody = byId("moviesBody")
        verwijderChildElementenVan(byId("moviesBody"));
        for (const movie of movies) {
            const tr = moviesBody.insertRow();
            tr.insertCell().innerText = movie.name;
            tr.insertCell().innerText = movie.ranking;
            tr.insertCell().innerText = movie.distributor;
            createHyperlink(tr, "Detail", movie);
            createHyperlink(tr, "Change ranking", movie);
            createHyperlink(tr, "Comments", movie);
        }
    } else {
        toon("storing");
    }
}

function createHyperlink(tr, text, movie) {
    const movieIdName = {
        id : movie.id,
        name : movie.name
    };
    const td = tr.insertCell();
    const hyperlink = document.createElement("a");
    hyperlink.href = text.toLowerCase().split(" ").join("") + ".html";
    hyperlink.innerText = text;
    hyperlink.onclick = function () {
        sessionStorage.setItem("movie", JSON.stringify(movieIdName));
    }
    td.appendChild(hyperlink);
}