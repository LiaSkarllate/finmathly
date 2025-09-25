export const parseAndCheckResponse = async (response: Response) => {
    const status = response.status;
    const body = await response.text();

    if (response.ok) {
        if (!body) return null;
        try {
            return JSON.parse(body);
        } catch {
            return body;
        }
    }

    let message: string = response.statusText;

    if (body) {
        try {
            const parsed = JSON.parse(body) as { message?: string };
            message = parsed.message ?? response.statusText;
        } catch {
            message = body;
        }
    }

    const error = new Error(message) as Error & { status: number };
    error.status = status;
    throw error;
};
