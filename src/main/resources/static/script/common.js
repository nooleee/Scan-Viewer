// src/main/resources/static/script/authFetch.js
function authFetch(url, options = {}) {
    const token = localStorage.getItem('jwt');
    if (token) {
        if (!options.headers) {
            options.headers = {};
        }
        options.headers['Authorization'] = 'Bearer ' + token;
    }
    return fetch(url, options);
}
