import { API_BASE_URL } from "../config/app-config";

const ACCESS_TOKEN = "ACCESS_TOKEN";

export function call(api, method, request) {
    let headers = new Headers({ "Content-Type": "application/json" });

    //로컬스토리지에서 ACCESS_TOKEN 가져오기
    const accessToken = localStorage.getItem(ACCESS_TOKEN);
    if(accessToken && accessToken != null) {
        headers.append("Authorization", "Bearer " + accessToken);
    }

    let options = {
        headers: headers,
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
    )
    .catch((error) => {
        if(error.status === 403) {
            window.location.href = "/login";
        }
        return Promise.reject(error);
    });
}

export function signin(userDTO) {
    return call("/auth/signin", "POST", userDTO) 
        .then((response) => {
            if(response.token) {
                //로컬스토리지에 토큰저장
                localStorage.setItem("ACCESS_TOKEN", response.token);
                //token이 존재할 경우 todo 목록 화면으로 이동
                window.location.href = "/";
            }
        });
}