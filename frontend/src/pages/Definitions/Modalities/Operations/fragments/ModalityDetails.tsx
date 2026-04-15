import React from 'react';
import { useFormContext, Controller } from 'react-hook-form';
import { Input, Checkbox, Form, Select } from 'antd';
import { YIELD_TYPE_LABELS, CAPITALIZATION_PERIOD_LABELS } from '../../../../../models/Modality.types';

interface ModalityDetailsProps {
    isEditable: boolean;
}

const ModalityDetails: React.FC<ModalityDetailsProps> = ({ isEditable }) => {
    const { control } = useFormContext();

    return (
        <div className="p-4 border rounded bg-white">
            <h2 className="text-lg font-semibold mb-4">Modality Details</h2>

            <Form layout="vertical" component="div">
                <Form.Item label="Name">
                    <Controller
                        name="name"
                        control={control}
                        defaultValue=''
                        rules={
                            isEditable ? {
                                required: 'The name is required. Please, provide one.',
                                maxLength: { value: 25, message: "The modality name cannot exceed 25 characters. Please, provide a shorter name." },
                            } : undefined}
                        render={({ field }) => (
                            <Input {...field}
                                id="name"
                                placeholder="Enter a name."
                                disabled={!isEditable}
                                maxLength={25} />
                        )}
                    />
                </Form.Item>

                <Form.Item label="Yield Type">
                    <Controller
                        name="yieldType"
                        control={control}
                        defaultValue={undefined}
                        rules={
                            isEditable ? {
                                required: 'The yield type is required. Please, select one.'
                            } : undefined}
                        render={({ field }) => (
                            <Select
                                {...field}
                                id="yieldType"
                                placeholder="Select a yield type."
                                disabled={!isEditable}
                                options={YIELD_TYPE_LABELS}
                                allowClear
                            />
                        )}
                    />
                </Form.Item>

                <Form.Item label="Capitalization Period">
                    <Controller
                        name="capitalizationPeriod"
                        control={control}
                        defaultValue={undefined}
                        rules={
                            isEditable ? {
                                required: 'The capitalization period is required. Please, select one.',
                            } : undefined}
                        render={({ field }) => (
                            <Select
                                {...field}
                                id="capitalizationPeriod"
                                placeholder="Select an capitalization period."
                                disabled={!isEditable}
                                options={CAPITALIZATION_PERIOD_LABELS}
                                allowClear
                            />
                        )}
                    />
                </Form.Item>

                <Form.Item>
                    <Controller
                        name="supportsFlows"
                        control={control}
                        defaultValue={false}
                        render={({ field: { value, onChange, ...rest } }) => (
                            <Checkbox
                                {...rest}
                                id="supportsFlows"
                                checked={!!value}
                                onChange={(e) => onChange(e.target.checked)}
                                disabled={!isEditable}
                            >
                                Supports Flows
                            </Checkbox>
                        )}
                    />
                </Form.Item>
            </Form>
        </div>
    );
};

export default ModalityDetails;
