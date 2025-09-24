import { parseAndCheckResponse } from '../../services/shared/requests';

const MODALITIES_PATH = '/api/modalities';

export const create = async (modality) => {
    const response = await fetch(MODALITIES_PATH, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(modality),
    });

    return await parseAndCheckResponse(response);
};

export const deleteById = async (id) => {
    const response = await fetch(`${MODALITIES_PATH}/${id}`, {
        method: 'DELETE',
    });

    return await parseAndCheckResponse(response);
};

export const update = async (modality) => {
    const response = await fetch(`${MODALITIES_PATH}/${modality.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(modality),
    });

    return await parseAndCheckResponse(response);
};

export const findById = async (id) => {
    const response = await fetch(`${MODALITIES_PATH}/${id}`);

    return await parseAndCheckResponse(response);
};

export const findByFilter = async () => {
    const response = await fetch(MODALITIES_PATH);

    return await parseAndCheckResponse(response);
};
