import React from "react";
import { useFormContext } from "react-hook-form";
import { Form, Input } from "antd";

const AuditInfo: React.FC = () => {
    const { watch } = useFormContext();
    const createdAt = watch("createdAt");
    const updatedAt = watch("updatedAt");

    return (
        <div className="p-4 border rounded bg-gray-50">
            <h2 className="text-lg font-semibold mb-4">Audit Info</h2>

            <Form layout="vertical" component="div">
                <Form.Item label="Created at">
                    <Input
                        value={createdAt ? new Date(createdAt).toLocaleString() : 'Not available.'}
                        id="createdAt"
                        disabled />
                </Form.Item >

                <Form.Item label="Updated at">
                    <Input
                        value={updatedAt ? new Date(updatedAt).toLocaleString() : 'Not available.'}
                        id="updatedAt"
                        disabled />
                </Form.Item>
            </Form>
        </div>
    );
};

export default AuditInfo;
