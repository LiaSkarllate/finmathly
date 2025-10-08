import React, { useEffect, useState } from 'react';
import { useNavigate, Link, useParams } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';
import { toast } from 'react-toastify';

import Spinner from '../../components/shared/Spinner';
import { findById, deleteById } from '../../services/modality/requests';

interface Modality {
    id: string;
    name: string;
    yieldType: string;
    capitalizationPeriod: string;
    supportsFlows: boolean;
    createdAt: string;
    updatedAt?: string;
}

const ModalityPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

    const [readModality, setReadModality] = useState<Modality | null>(null);

    const [loading, setLoading] = useState<boolean>(true);
    const [isDeleting, setIsDeleting] = useState(false);

    useEffect(() => {
        const fetchModality = async () => {
            setLoading(true);

            if (!id) {
                toast.error('The id is missing.');
                setLoading(false);
                return;
            }

            try {
                const data = await findById(id);
                setReadModality(data);
            } catch (error) {
                toast.error('Failed to fetch modality.');
                console.error(error);
            } finally {
                setLoading(false);
            }
        };

        fetchModality();
    }, [id]);

    const onDeleteClick = async () => {
        if (!readModality) {
            toast.error('The modality was not loaded.');
            return;
        }

        const confirmDelete = window.confirm('Are you sure you want to delete this modality?');
        if (!confirmDelete) return;

        setIsDeleting(true);

        try {
            await deleteById(readModality.id);
            toast.success('Modality deleted successfully.');
            navigate('/modalities');
        } catch (error) {
            toast.error('Failed to delete modality.');
            console.error(error);
        } finally {
            setIsDeleting(false);
        }
    };

    if (loading) {
        return (
            <section className="bg-indigo-50">
                <div className="container m-auto py-24">
                    <div className="bg-white px-6 py-8 mb-4 shadow-md rounded-md border m-4 md:m-0">
                        <Spinner />
                    </div>
                </div>
            </section>
        );
    }

    if (!readModality) {
        return (
            <section className="bg-indigo-50">
                <div className="container m-auto py-24">
                    <div className="bg-white px-6 py-8 mb-4 shadow-md rounded-md border m-4 md:m-0">
                        <div>Nothing here.</div>
                    </div>
                </div>
            </section>
        );
    }

    return (
        <>
            <section>
                <div className="container m-auto py-6 px-6">
                    <Link to="/modalities" className="text-indigo-500 hover:text-indigo-600 flex items-center">
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
                                <div className="text-gray-500 mb-2">ID: {readModality.id}</div>

                                <h1 className="text-3xl font-bold mb-2">{readModality.name}</h1>

                                <div className="text-gray-600 mb-1">Yield type: {readModality.yieldType}</div>

                                <div className="text-gray-600 mb-1">
                                    Capitalization period: {readModality.capitalizationPeriod}
                                </div>

                                <div className="text-gray-600 mb-1">
                                    Supports flows: {readModality.supportsFlows ? 'Yes' : 'No'}
                                </div>
                            </div>

                            <div className="bg-white p-6 rounded-lg shadow-md mt-6">
                                <h3 className="text-indigo-800 text-lg font-bold mb-4">Metadata</h3>

                                <p className="mb-2">
                                    Created at: {new Date(readModality.createdAt).toLocaleString()}
                                </p>

                                <p className="mb-2">
                                    Updated at: {readModality.updatedAt ? new Date(readModality.updatedAt).toLocaleString() : '-'}
                                </p>
                            </div>
                        </main>

                        <aside>
                            <div className="bg-white p-6 rounded-lg shadow-md">
                                <h3 className="text-xl font-bold mb-4">Manage modality</h3>

                                <Link
                                    to={`/modalities/update/${readModality.id}`}
                                    className="bg-indigo-500 hover:bg-indigo-600 text-white text-center font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline block"
                                >
                                    Edit modality
                                </Link>

                                <button
                                    onClick={() => onDeleteClick()}
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

export default ModalityPage;
