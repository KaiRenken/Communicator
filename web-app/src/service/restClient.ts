export const httpGet = (url: string, accessToken?: string): Promise<Response> => {
    const requestHeaders: HeadersInit = new Headers();
    requestHeaders.set('Content-Type', 'text/html');
    requestHeaders.set('Access-Control-Allow-Origin', '*');
    requestHeaders.set('Access-Control-Allow-Methods', '*');

    if (accessToken) {
        requestHeaders.set('Authorization', `Bearer ${accessToken}`)
    }

    return fetch(url, {
        method: 'GET',
        cache: 'no-cache',
        headers: requestHeaders,
    })
        .then(handleErrors)
}

export const httpPost = (url: string, body: object, accessToken?: string): Promise<Response> => {
    const requestHeaders: HeadersInit = new Headers();
    requestHeaders.set('Content-Type', 'application/json');
    requestHeaders.set('Access-Control-Allow-Origin', '*');
    requestHeaders.set('Access-Control-Allow-Methods', '*');

    if (accessToken) {
        requestHeaders.set('Authorization', `Bearer ${accessToken}`)
    }

    return fetch(url, {
        method: 'POST',
        body: JSON.stringify(body),
        cache: 'no-cache',
        headers: requestHeaders,
    })
        .then(handleErrors)
}

const handleErrors = (response: Response) => {
    if (!response.ok) {
        console.error("Response not ok:", response.status, response.statusText);

        response.json()
            .then(json => alert(json.message));

        return Promise.reject(response)
    }

    return Promise.resolve(response);
}