import { API_BASE_URL } from "../config/app-config";

export function call(api, method, request) {
    let options = {
        headers: new Headers({
            "Content-Type": "application/json",
        }),
        url: API_BASE_URL + api,
        method: method,
    };

    if(request) {
        //GET methoe
        options.body = JSON.stringify(request);
    }

    return fetch(options.url, options).then((response) => 
        response.json().then((json) => {
            if(!response.ok){
                return Promise.reject(json);
            }
            return json;
        })
    ); 
}

export function signin(userDTO) {
    return call("/auth/signin", "POST", userDTO) 
        .then((response) => {
            if(response.token) {
                window.location.href = "/";
            }
        });
}