import { useState, useEffect } from 'react';
import { toast } from 'react-toastify';

import ModalityListing from './ModalityListing';
import Spinner from '../shared/Spinner';

import { findByFilter } from '../../services/modality/requests';

const ModalityListings = () => {
    const [modalities, setModalities] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchModalities = async () => {
            setLoading(true);

            try {
                await findByFilter().then((data) => setModalities(data.content));
            } catch (error) {
                toast.error('Failed to fetch modalities.');
                console.log(error);
            } finally {
                setLoading(false);
            }
        };

        fetchModalities();
    }, []);

    return (
        <section className='bg-blue-50 px-4 py-10'>
            <div className='container-xl lg:container m-auto'>
                <h2 className='text-3xl font-bold text-indigo-500 mb-6 text-center'>
                    {'Browse Modalities'}
                </h2>

                {loading ? (
                    <Spinner />
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                        {modalities.map((modality) => (
                            <ModalityListing key={modality.id} modality={modality} />
                        ))}
                    </div>
                )}
            </div>
        </section>
    );
};

export default ModalityListings;
