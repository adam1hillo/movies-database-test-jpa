"use strict"

import {byId, setText, toon, verberg} from "./util.js";

const movie = JSON.parse(sessionStorage.getItem("movie"));
setText("movieName", movie.name);
byId("save").onclick = async function() {
    const nieuweRankingInput = byId("nieuweRanking");
    if (nieuweRankingInput.checkValidity()) {
        verberg("nieuweRankingFout");
        updateRanking(Number(nieuweRankingInput.value));
    } else {
        toon("nieuweRankingFout");
        nieuweRankingInput.focus();
    }
}

async function updateRanking(nieuweRanking) {
    const response = await fetch(`movies/${movie.id}/ranking`,
        {
            method : "PATCH",
            headers : {'Content-Type' : "application/json"},
            body: JSON.stringify(nieuweRanking)
        });
    if (response.ok) {
        window.location = "detail.html"
    } else {
        toon("storing");
    }
}