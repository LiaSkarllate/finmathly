import React, { useCallback, useEffect } from "react";
import { observer } from "mobx-react";
import { useNavigate, useParams } from "react-router-dom";
import { FormProvider, useForm, FieldErrors } from "react-hook-form";

import Spinner from "../../../../fragments/shared/Spinner";
import useRootStore from "../../../../store";
import { ModalityDTO } from "../../../../models/Modality.types";
import { OperationType, FrontendError } from "../../../../models/shared/types";

import ModalityDetails from "./fragments/ModalityDetails";
import AuditInfo from "./fragments/AuditInfo";
import { OperationsLabel } from "../../../../utils/constants";

function useModalityForm() {
    const navigate = useNavigate();
    const { operation, id } = useParams<{ operation: OperationType; id?: string }>();
    const { modalitiesStore, messageStore } = useRootStore();

    const formMethods = useForm<ModalityDTO>({
        mode: 'onSubmit',
    });

    const { handleSubmit, reset } = formMethods;

    const handleBack = () => navigate('/definitions/modalities');

    const fetchModality = async () => {
        if (!id || operation === OperationType.CREATE) return;

        modalitiesStore.clean();
        await modalitiesStore.getModalities({ id });

        const modality = modalitiesStore.selectedModality;
        if (modality) {
            reset(modality);
        } else {
            messageStore.addMessage('The modality not was found. Please, try a different ID.', 'error');
        }
    };

    useEffect(() => {
        fetchModality();
    }, [id, operation]);

    const onValidForm = useCallback(async (modality: ModalityDTO) => {
        if (!operation || operation === OperationType.READ) return;

        try {
            if (operation === OperationType.CREATE) {
                await modalitiesStore.postModality(modality);
            } else if (operation === OperationType.UPDATE) {
                await modalitiesStore.putModality({
                    ...modalitiesStore.selectedModality,
                    ...modality
                });
            } else if (operation === OperationType.DELETE) {
                await modalitiesStore.deleteModality();
            }

            messageStore.addMessage('The operation completed successfully.', 'success');
            handleBack();
        } catch {
            messageStore.addMessageFromError(modalitiesStore.error);
        }
    }, [modalitiesStore]);

    const onInvalidForm = (errors: FieldErrors<ModalityDTO>) => {
        for (const error of Object.values(errors)) {
            const message = (error as any)?.message ?? FrontendError.DEFAULT_ERROR_MESSAGE;
            messageStore.addMessage(message, 'error');
        }
    };

    const onSubmit = handleSubmit(onValidForm, onInvalidForm);

    const isEditable =
        operation !== undefined && [OperationType.CREATE, OperationType.UPDATE].includes(operation);

    return {
        formMethods,
        onSubmit,
        handleBack,
        operation,
        loading: modalitiesStore.loading,
        isEditable,
    };
}

export const ModalityOperations: React.FC = observer(() => {
    const { formMethods, onSubmit, handleBack, operation, loading, isEditable } = useModalityForm();

    return (
        <div className="p-6">
            <h1 className="text-2xl font-semibold mb-4">Modality ({operation})</h1>

            {loading ? (
                <div className="flex justify-center py-6">
                    <Spinner />
                </div>
            ) : (
                <FormProvider {...formMethods}>
                    <form onSubmit={onSubmit} className="space-y-6">
                        <ModalityDetails isEditable={isEditable} />
                        {operation && (operation == OperationType.READ || operation == OperationType.DELETE) && (
                            <AuditInfo />
                        )}

                        <div className="flex gap-3">
                            <button type="button" onClick={handleBack} className="px-4 py-2 border rounded bg-gray-100">
                                Back
                            </button>

                            {operation && operation !== OperationType.READ && (
                                <button type="submit" disabled={loading} className="px-4 py-2 bg-blue-600 text-white rounded">
                                   {OperationsLabel[operation]}
                                </button>
                            )}
                        </div>
                    </form>
                </FormProvider>
            )}
        </div>
    );
});

export default ModalityOperations;
