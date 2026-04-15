import { FrontendError } from "../models/shared/types";

export const parseResponse = async (response: Response) => {
    const contentType = response.headers.get("content-type") ?? '';
    const bodyText = await response.text();
    const trimmed = bodyText?.trim();

    if (!response.ok) {
        let message = FrontendError.DEFAULT_ERROR_MESSAGE || `HTTP ${response.status}`;

        if (trimmed) {
            if (contentType.includes("application/json") || trimmed.startsWith("{") || trimmed.startsWith("[")) {
                try {
                    const parsed = JSON.parse(trimmed);
                    message = parsed?.message ?? FrontendError.DEFAULT_ERROR_MESSAGE;
                } catch {
                    message = trimmed;
                }
            } else {
                message = trimmed;
            }
        }

        throw new FrontendError(message);
    }

    if (!trimmed) {
        return null;
    }

    if (contentType.includes("application/json") || trimmed.startsWith("{") || trimmed.startsWith("[")) {
        return JSON.parse(trimmed);
    }

    throw new FrontendError();
};

export function normalizeToArray<T = any>(resp: any): T[] {
    if (resp == null) return [];

    if (Array.isArray(resp)) return resp as T[];
    if (Array.isArray(resp.content)) return resp.content as T[];
    if (Array.isArray(resp.data)) return resp.data as T[];
    if (Array.isArray(resp.items)) return resp.items as T[];

    throw new FrontendError();
}
