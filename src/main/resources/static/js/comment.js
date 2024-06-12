"use strict"

import {byId, setText, toon, verberg} from "./util.js";

const movie = JSON.parse(sessionStorage.getItem("movie"));
setText("movieName", `${movie.name}`)
const response = await fetch(`movies/${movie.id}/comments`);
if (response.ok) {
    toon("commentsTable");
    const comments = await response.json();
    const commentsBody = byId("commentsBody");
    for (const comment of comments) {
        const tr = commentsBody.insertRow();
        tr.insertCell().innerText = new Date(comment.moment).toLocaleString("nl-BE");
        tr.insertCell().innerText = comment.emailAddress;
        tr.insertCell().innerText = comment.comment;
    }
} else {
    toon("storing");
}
