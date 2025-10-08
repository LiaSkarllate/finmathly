import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import Spinner from '../../components/shared/Spinner';

import { findById, update } from '../../services/modality/requests';

interface Modality {
    id: string;
    name: string;
    yieldType: string;
    capitalizationPeriod: string;
    supportsFlows: boolean;
    createdAt: string;
    updatedAt?: string;
}

const UpdateModalityPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

    const [readModality, setReadModality] = useState<Modality | null>(null);

    const [loading, setLoading] = useState<boolean>(true);
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [name, setName] = useState('');
    const [yieldType, setYieldType] = useState('');
    const [capitalizationPeriod, setCapitalizationPeriod] = useState('');
    const [supportsFlows, setSupportsFlows] = useState(false);

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

                setName(data.name);
                setYieldType(data.yieldType);
                setCapitalizationPeriod(data.capitalizationPeriod);
                setSupportsFlows(Boolean(data.supportsFlows));
            } catch (error) {
                toast.error('Failed to fetch modality.');
                console.error(error);
            } finally {
                setLoading(false);
            }
        };

        fetchModality();
    }, [id]);

    const submitForm = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (!readModality) {
            toast.error('The modality was not loaded.');
            return;
        }

        const updatedModality: Modality = {
            ...readModality,
            name,
            yieldType,
            capitalizationPeriod,
            supportsFlows,
        };

        setIsSubmitting(true);

        try {
            await update(updatedModality);
            toast.success('Modality updated successfully.');
            navigate(`/modalities/${readModality.id}`);
        } catch (error) {
            toast.error('Failed to update modality.');
            console.error(error);
        } finally {
            setIsSubmitting(false);
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
        <section className="bg-indigo-50">
            <div className="container m-auto max-w-2xl py-24">
                <div className="bg-white px-6 py-8 mb-4 shadow-md rounded-md border m-4 md:m-0">
                    <form onSubmit={submitForm}>
                        <h2 className="text-3xl text-center font-semibold mb-6">Update Modality</h2>

                        <div className="mb-4">
                            <label htmlFor="name" className="block text-gray-700 font-bold mb-2">
                                Name
                            </label>
                            <input
                                id="name"
                                name="name"
                                type="text"
                                required
                                className="border rounded w-full py-2 px-3"
                                placeholder="Modality name"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            />
                        </div>

                        <div className="mb-4">
                            <label htmlFor="yieldType" className="block text-gray-700 font-bold mb-2">
                                Yield type
                            </label>
                            <input
                                id="yieldType"
                                name="yieldType"
                                type="text"
                                required
                                className="border rounded w-full py-2 px-3"
                                placeholder="e.g. fixed, variable"
                                value={yieldType}
                                onChange={(e) => setYieldType(e.target.value)}
                            />
                        </div>

                        <div className="mb-4">
                            <label htmlFor="capitalizationPeriod" className="block text-gray-700 font-bold mb-2">
                                Capitalization period
                            </label>
                            <input
                                id="capitalizationPeriod"
                                name="capitalizationPeriod"
                                type="text"
                                required
                                className="border rounded w-full py-2 px-3"
                                placeholder="e.g. monthly, yearly"
                                value={capitalizationPeriod}
                                onChange={(e) => setCapitalizationPeriod(e.target.value)}
                            />
                        </div>

                        <div className="mb-6 flex items-center">
                            <input
                                id="supportsFlows"
                                name="supportsFlows"
                                type="checkbox"
                                className="mr-3"
                                checked={supportsFlows}
                                onChange={(e) => setSupportsFlows(e.target.checked)}
                            />
                            <label htmlFor="supportsFlows" className="text-gray-700">
                                Supports flows
                            </label>
                        </div>

                        <div>
                            <button
                                type="submit"
                                disabled={isSubmitting}
                                className="bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline"
                            >
                                {isSubmitting ? 'Updating...' : 'Update modality'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    );
};

export default UpdateModalityPage;
