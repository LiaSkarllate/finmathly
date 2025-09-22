import React, { useState } from 'react';
import { Link } from 'react-router-dom';

function ModalityListing({ modality }) {
    const [showDetails, setShowDetails] = useState(false);
    const detailsId = `modality-details-${modality.id}`;

    return (
        <article className="bg-white rounded-xl shadow-md relative">
            <div className="p-4">
                <header className="mb-6">
                    <div className="text-gray-600 my-2">{modality.yieldType}</div>
                    <h3 className="text-xl font-bold">{modality.name}</h3>
                </header>

                <div className="mb-4 text-sm text-gray-700">
                    <div>
                        <strong>Capitalization period:</strong> {modality.capitalizationPeriod}
                    </div>
                    <div>
                        <strong>Supports flows:</strong> {modality.supportsFlows ? 'Yes' : 'No'}
                    </div>
                </div>

                <button
                    type="button"
                    onClick={() => setShowDetails(s => !s)}
                    className="text-indigo-500 mb-4 hover:text-indigo-600"
                    aria-expanded={showDetails}
                    aria-controls={detailsId}
                >
                    {showDetails ? 'Hide details' : 'Show details'}
                </button>

                {showDetails && (
                    <div id={detailsId} className="mb-4 text-sm text-gray-800" role="region" aria-live="polite">
                        <div>
                            <strong>Created at:</strong>{' '}
                            {new Date(modality.createdAt).toLocaleString()}
                        </div>
                        <div>
                            <strong>Last updated:</strong>{' '}
                            {new Date(modality.updatedAt).toLocaleString()}
                        </div>
                    </div>
                )}

                <div className="border border-gray-100 mb-5" />

                <footer className="flex justify-end items-center mb-4">
                    <Link
                        to={`/modalities/${modality.id}`}
                        className="h-[36px] bg-indigo-500 hover:bg-indigo-600 text-white px-4 py-2 rounded-lg text-center text-sm"
                        aria-label={`View details for ${modality.name}`}
                    >
                        View details
                    </Link>
                </footer>
            </div>
        </article>
    );
}

export default ModalityListing;
