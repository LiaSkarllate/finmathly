import { parseResponse } from '../utils/http';

const BASE_API = window.config?.VITE_FINMATHLY_APP_MONOLITH_BACKEND_URL || import.meta.env.VITE_FINMATHLY_APP_MONOLITH_BACKEND_URL;

export const create = async <T = any>(path: string, dto: T) =>
    parseResponse(await fetch(BASE_API + path, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    }));

export const update = async <T = any>(path: string, id: string, dto: T) =>
    parseResponse(await fetch(`${BASE_API + path}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    }));

export const deleteById = async (path: string, id: string) =>
    parseResponse(await fetch(`${BASE_API + path}/${id}`, { method: 'DELETE' }));

export const findById = async (path: string, id: string) => {
    return parseResponse(await fetch(`${BASE_API + path}/${id}`));
};

export const findByFilter = async <F = any>(path: string, filter?: F) => {
    let url = BASE_API + path;

    if (filter && Object.keys(filter).length > 0) {
        const searchParams = new URLSearchParams();

        for (const [field, value] of Object.entries(filter)) {
            if (value !== undefined && value !== null && value !== '') {
                searchParams.append(field, value as string);
            }
        }

        const queryString = searchParams.toString();
        if (queryString) {
            url = `${url}?${queryString}`;
        }
    }

    return parseResponse(await fetch(url));
};

