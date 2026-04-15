import React, { useMemo } from "react";
import { Table, Button } from "antd";
import type { ColumnsType } from "antd/es/table";
import { SearchOutlined, FormOutlined, CloseOutlined } from "@ant-design/icons";

import { CAPITALIZATION_PERIOD_LABELS_MAP, CapitalizationPeriod, YIELD_TYPE_LABELS_MAP, YieldType, ModalityDTO } from "../../../models/Modality.types";

import { OperationType } from "../../../models/shared/types";
import { DATE_FORMATTER } from "../../../utils/constants";

interface GridModalitiesProps {
    data: ModalityDTO[];
    onSelect?: (operation: OperationType, id?: string) => void;
}

const GridModalities: React.FC<GridModalitiesProps> = ({ data, onSelect }) => {
    const columns: ColumnsType<ModalityDTO> = useMemo(() => {
        const baseColumns: ColumnsType<ModalityDTO> = [
            { title: "ID", dataIndex: "id", key: "id", width: 100 },
            { title: "Name", dataIndex: "name", key: "name", width: 200 },
            {
                title: "Yield Type",
                dataIndex: "yieldType",
                key: "yieldType",
                width: 150,
                render: (value: YieldType) => YIELD_TYPE_LABELS_MAP.get(value) ?? 'Not available.'
            },
            {
                title: "Capitalization Period",
                dataIndex: "capitalizationPeriod",
                key: "capitalizationPeriod",
                width: 180,
                render: (value: CapitalizationPeriod) => CAPITALIZATION_PERIOD_LABELS_MAP.get(value) ?? 'Not available.'
            },
            {
                title: "Supports Flows",
                dataIndex: "supportsFlows",
                key: "supportsFlows",
                width: 130,
                render: (value: boolean) => (value ? "Yes" : "No")
            },
            {
                title: "Created At",
                dataIndex: "createdAt",
                key: "createdAt",
                width: 180,
                render: (value: string) => DATE_FORMATTER.format(new Date(value))
            },
            {
                title: "Updated At",
                dataIndex: "updatedAt",
                key: "updatedAt",
                width: 180,
                render: (value: string) => DATE_FORMATTER.format(new Date(value))
            },
        ];

        const actionColumns: ColumnsType<ModalityDTO> = [
            {
                key: "action-read",
                width: 60,
                align: "right",
                render: (_value: any, record: ModalityDTO) => (
                    <Button
                        onClick={() => onSelect?.(OperationType.READ, record.id ?? '')}
                        size="small"
                        aria-label={`View modality ${record.name}`}
                        icon={<SearchOutlined />}
                    />
                ),
            },
            {
                key: "action-update",
                width: 60,
                align: "right",
                render: (_value: any, record: ModalityDTO) => (
                    <Button
                        onClick={() => onSelect?.(OperationType.UPDATE, record.id ?? '')}
                        size="small"
                        aria-label={`Edit modality ${record.name}`}
                        icon={<FormOutlined />}
                    />
                ),
            },
            {
                key: "action-delete",
                width: 60,
                align: "right",
                render: (_value: any, record: ModalityDTO) => (
                    <Button
                        onClick={() => onSelect?.(OperationType.DELETE, record.id ?? '')}
                        size="small"
                        aria-label={`Delete modality ${record.name}`}
                        icon={<CloseOutlined />}
                    />
                ),
            },
        ];

        return [...baseColumns, ...actionColumns];
    }, [onSelect]);

    return (
        <Table<ModalityDTO>
            columns={columns}
            dataSource={data}
            rowKey={(row: ModalityDTO) => row.id ?? 'Not available.'}
            pagination={{ pageSize: 20, showSizeChanger: true }}
        />
    );
};

export default GridModalities;