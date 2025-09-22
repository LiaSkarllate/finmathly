import React, { useState } from 'react';
import { useParams, useLoaderData, useNavigate, Link } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';
import { toast } from 'react-toastify';

const ModalityPage = ({ onDeleteModality }) => {
    const modality = useLoaderData();
    const { id } = useParams();
    const navigate = useNavigate();

    const [isDeleting, setIsDeleting] = useState(false);

    const onDeleteClick = (modalityId) => {
        const confirmDelete = window.confirm(
            'Are you sure you want to delete this modality?'
        );

        if (!confirmDelete) return;

        setIsDeleting(true);

        try {
            onDeleteModality(modalityId);
            toast.success('Modality deleted successfully.');
            navigate('/modalities');
        } catch (err) {
            toast.error(err?.message || 'Failed to delete modality.');
        } finally {
            setIsDeleting(false);
        }
    };

    return (
        <>
            <section>
                <div className="container m-auto py-6 px-6">
                    <Link
                        to="/modalities"
                        className="text-indigo-500 hover:text-indigo-600 flex items-center"
                    >
                        <FaArrowLeft className="mr-2" />
                        Back to Modalities
                    </Link>
                </div>
            </section>

            <section className="bg-indigo-50">
                <div className="container m-auto py-10 px-6">
                    <div className="grid grid-cols-1 md:grid-cols-70/30 w-full gap-6">
                        <main>
                            <div className="bg-white p-6 rounded-lg shadow-md text-center md:text-left">
                                <div className="text-gray-500 mb-2">ID: {modality.id}</div>

                                <h1 className="text-3xl font-bold mb-2">{modality.name}</h1>

                                <div className="text-gray-600 mb-1">
                                    Yield type: {modality.yieldType}
                                </div>

                                <div className="text-gray-600 mb-1">
                                    Capitalization period: {modality.capitalizationPeriod}
                                </div>

                                <div className="text-gray-600 mb-1">
                                    Supports flows: {modality.supportsFlows ? 'Yes' : 'No'}
                                </div>
                            </div>

                            <div className="bg-white p-6 rounded-lg shadow-md mt-6">
                                <h3 className="text-indigo-800 text-lg font-bold mb-4">Metadata</h3>

                                <p className="mb-2">
                                    Created at: {new Date(modality.createdAt).toLocaleString()}
                                </p>

                                <p className="mb-2">
                                    Updated at: {new Date(modality.updatedAt).toLocaleString()}
                                </p>
                            </div>
                        </main>

                        <aside>
                            <div className="bg-white p-6 rounded-lg shadow-md">
                                <h3 className="text-xl font-bold mb-4">Manage modality</h3>

                                <Link
                                    to={`/modalities/update/${modality.id}`}
                                    className="bg-indigo-500 hover:bg-indigo-600 text-white text-center font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline block"
                                >
                                    Edit modality
                                </Link>

                                <button
                                    onClick={() => onDeleteClick(modality.id)}
                                    disabled={isDeleting}
                                    className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline mt-4 block"
                                >
                                    {isDeleting ? 'Deleting...' : 'Delete modality'}
                                </button>
                            </div>
                        </aside>
                    </div>
                </div>
            </section>
        </>
    );
};

const modalityLoader = async ({ params, request }) => {
    const endpoint = `/modalities/${params.id}`;

    const response = await fetch(endpoint, { signal: request.signal });

    if (!response.ok) {
        throw new Response('Fetch failed', { status: response.status });
    }

    const data = await response.json();
    return data;
};

export { ModalityPage as default, modalityLoader };
