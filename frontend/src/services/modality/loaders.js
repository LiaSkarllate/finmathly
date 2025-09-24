import { findById } from "./requests.js";

export const modalityLoader = async ({ params }) => {
    return await findById(params.id);
};
