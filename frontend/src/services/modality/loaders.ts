import { LoaderFunction } from "react-router-dom";
import { findById } from "./requests";

export const modalityLoader: LoaderFunction = async ({ params }) => {
    const id = params.id;

    if (!id) {
        throw new Response("Bad Request", { status: 400 });
    }

    return findById(id);
};
