import { FrontendError } from "../models/shared/types";
import { TypeOptions } from "react-toastify/dist/types";
import { toast } from "react-toastify";

export class MessageStore {
    addMessage = (
        text: string,
        type?: TypeOptions,
        key?: string | number,
    ) => {
        toast(text, { type, toastId: key ?? Date.now() })
    };

    addMessageFromError = (error?: FrontendError) => {
        let text: string = FrontendError.DEFAULT_ERROR_MESSAGE;

        if (error) {
            const message: string | undefined = error.message;

            if (message && message.length > 0) text = message;
        }

        this.addMessage(text, 'error');
    };
}
