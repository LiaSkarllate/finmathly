import React, { useState } from 'react';
import { useParams, useLoaderData, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const UpdateModalityPage = ({ onUpdateModality }) => {
    const modality = useLoaderData();
    const { id } = useParams();
    const navigate = useNavigate();

    const [name, setName] = useState(modality.name);
    const [yieldType, setYieldType] = useState(modality.yieldType);
    const [capitalizationPeriod, setCapitalizationPeriod] = useState(modality.capitalizationPeriod);
    const [supportsFlows, setSupportsFlows] = useState(Boolean(modality.supportsFlows));

    const [isSubmitting, setIsSubmitting] = useState(false);

    const submitForm = async (e) => {
        e.preventDefault();

        const updatedModality = {
            id,
            name,
            yieldType,
            capitalizationPeriod,
            supportsFlows,
        };

        setIsSubmitting(true);

        try {
            await onUpdateModality(updatedModality);
            toast.success('Modality updated successfully.');
            navigate(`/modalities/${id}`);
        } catch (error) {
            toast.error('Failed to update modality.');
            console.log(error);
        } finally {
            setIsSubmitting(false);
        }
    };

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
