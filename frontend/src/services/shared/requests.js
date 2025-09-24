export const parseAndCheckResponse = async (response) => {
    const status = response.status;

    const text = await response.text();

    if (response.ok) {
        if (!text) return null;
        try {
            return JSON.parse(text);
        } catch {
            return text;
        }
    }

    let message = response.statusText || 'Request failed';
    if (text) {
        try {
            const json = JSON.parse(text);
            if (json && (json.message || json.error)) {
                message = json.message || json.error;
            } else {
                message = JSON.stringify(json);
            }
        } catch {
            message = text;
        }
    }

    const error = new Error(message);
    error.status = status;
    throw error;
};
