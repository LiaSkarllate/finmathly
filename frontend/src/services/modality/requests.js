export const create = async (modality) => {
    const response = await fetch('/modalities', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(modality),
    });

    return response;
};

export const deleteById = async (id) => {
    const response = await fetch(`/modalities/${id}`, {
        method: 'DELETE',
    });

    return response;
};

export const update = async (modality) => {
    const response = await fetch(`/modalities/${modality.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(modality),
    });

    return response;
};