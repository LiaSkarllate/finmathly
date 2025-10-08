import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

import { create } from '../../services/modality/requests';

interface NewModality {
    name: string;
    yieldType: string;
    capitalizationPeriod: string;
    supportsFlows: boolean;
}
const CreateModalityPage: React.FC = () => {
    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [yieldType, setYieldType] = useState('');
    const [capitalizationPeriod, setCapitalizationPeriod] = useState('');
    const [supportsFlows, setSupportsFlows] = useState(false);

    const [isSubmitting, setIsSubmitting] = useState(false);

    const submitForm = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const newModality: NewModality = {
            name,
            yieldType,
            capitalizationPeriod,
            supportsFlows,
        };

        setIsSubmitting(true);

        try {
            await create(newModality);
            toast.success('Modality created successfully.');
            navigate('/modalities');
        } catch (error) {
            toast.error('Failed to create modality.');
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
                        <h2 className="text-3xl text-center font-semibold mb-6">Create Modality</h2>

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
                                className="bg-indigo-500 hover:bg-indigo-600 text-white font-bold py-2 px-4 rounded-full w-full focus:outline-none focus:shadow-outline disabled:opacity-50"
                            >
                                {isSubmitting ? 'Creating...' : 'Create modality'}
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    );
};

export default CreateModalityPage;
