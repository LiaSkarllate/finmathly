import React, { useCallback, useEffect } from "react";
import { Controller, FieldErrors, useForm } from "react-hook-form";
import { observer } from "mobx-react";
import { useNavigate, useResolvedPath } from "react-router-dom";
import { isEmpty } from 'lodash';
import { Input, Select } from "antd";

import useRootStore from "../../../store";

import Spinner from "../../../fragments/shared/Spinner";

import { OperationType, FrontendError } from "../../../models/shared/types";
import { ModalitySearchFilter, YIELD_TYPE_LABELS } from "../../../models/Modality.types";

import GridModalities from "./GridModalities";

const ModalityListings: React.FC = observer(() => {
    const { modalitiesStore, messageStore } = useRootStore();
    const navigate = useNavigate();
    const path = useResolvedPath('').pathname;

    const { handleSubmit, control } = useForm<ModalitySearchFilter>();

    useEffect(() => {
        modalitiesStore.clean();
        onValidForm();
    }, [modalitiesStore]);

    const navigateToOperation = (operation: OperationType, id?: string) => {
        navigate(`${path}/${operation}/${id ?? ""}`);
    };

    const onValidForm = useCallback(async (filter?: ModalitySearchFilter) => {
        try {
            await modalitiesStore.getModalities(filter);

            if (!modalitiesStore.loading &&
                !modalitiesStore.error &&
                modalitiesStore.modalities.length === 0) {

                messageStore.addMessage('No records were found. Please, try a different filter if used one.', 'info');
            }
        } catch {
            messageStore.addMessageFromError(modalitiesStore.error);
        }
    }, [modalitiesStore]);

    const onInvalidForm = (errors: FieldErrors<ModalitySearchFilter>) => {
        for (const error of Object.values(errors)) {
            const message = (error as any)?.message ?? FrontendError.DEFAULT_ERROR_MESSAGE;
            messageStore.addMessage(message, 'error');
        }
    };

    const onSubmit = handleSubmit(onValidForm, onInvalidForm);

    return (
        <section className="bg-blue-50 px-4 py-10">
            <div className="container-xl lg:container m-auto">
                <h2 className="text-3xl font-bold text-indigo-500 mb-6 text-center">Modalities</h2>

                <div className="max-w-6xl mx-auto mb-6">
                    <form onSubmit={onSubmit} className="space-y-4">
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                            <Controller
                                name="name"
                                control={control}
                                defaultValue=''
                                rules={{
                                    maxLength: { value: 25, message: "The modality name cannot exceed 25 characters. Please, provide a shorter name." },
                                }}
                                render={({ field }) => (
                                    <Input
                                        {...field}
                                        id="name"
                                        placeholder="Name"
                                        className="w-full"
                                        maxLength={25} />
                                )}
                            />

                            <Controller
                                name="yieldType"
                                control={control}
                                defaultValue={undefined}
                                render={({ field }) => (
                                    <Select
                                        {...field}
                                        id="yieldType"
                                        placeholder="Yield Type"
                                        options={YIELD_TYPE_LABELS}
                                        allowClear
                                    />
                                )}
                            />
                        </div>


                        <div className="flex items-center justify-center gap-4">
                            <button
                                type="submit"
                                className="px-4 py-2 bg-indigo-600 text-white rounded hover:bg-indigo-700"
                            >
                                Search
                            </button>

                            <button
                                type="button"
                                onClick={() => navigateToOperation(OperationType.CREATE)}
                                className="px-4 py-2 bg-gray-200 rounded hover:bg-gray-300"
                            >
                                New
                            </button>
                        </div>
                    </form>
                </div>

                <div className="max-w-6xl mx-auto">
                    {modalitiesStore.loading ? (
                        <div className="flex justify-center py-6">
                            <Spinner />
                        </div>
                    ) : (
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                            {!isEmpty(modalitiesStore.modalities) && (
                                <GridModalities
                                    data={modalitiesStore.modalities}
                                    onSelect={navigateToOperation} />
                            )}
                        </div>
                    )}
                </div>
            </div>
        </section >
    );
});

export default ModalityListings;
