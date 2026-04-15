export type YieldType = 'FIXED' | 'FLOATING' | 'HYBRID';

export const YIELD_TYPE_LABELS: { label: string; value: YieldType }[] = [
    { label: 'Fixed', value: 'FIXED' },
    { label: 'Floating', value: 'FLOATING' },
    { label: 'Hybrid', value: 'HYBRID' },
];

export const YIELD_TYPE_LABELS_MAP = new Map(YIELD_TYPE_LABELS.map(item => [item.value, item.label]));

export type CapitalizationPeriod =
    | 'MONTHLY'
    | 'YEARLY'
    | 'QUARTERLY'
    | 'FOUR_MONTHLY'
    | 'HALF_YEARLY'
    | 'DAILY_360'
    | 'DAILY_252';

export const CAPITALIZATION_PERIOD_LABELS: { label: string; value: CapitalizationPeriod }[] = [
    { label: 'Monthly', value: 'MONTHLY' },
    { label: 'Yearly', value: 'YEARLY' },
    { label: 'Quarterly', value: 'QUARTERLY' },
    { label: 'Four monthly', value: 'FOUR_MONTHLY' },
    { label: 'Half yearly', value: 'HALF_YEARLY' },
    { label: 'Daily (360)', value: 'DAILY_360' },
    { label: 'Daily (252)', value: 'DAILY_252' },
];

export const CAPITALIZATION_PERIOD_LABELS_MAP = new Map(CAPITALIZATION_PERIOD_LABELS.map(item => [item.value, item.label]));

export interface ModalityDTO {
    id?: string;
    name: string;
    yieldType: YieldType;
    capitalizationPeriod: CapitalizationPeriod;
    supportsFlows?: boolean;
    createdAt?: string;
    updatedAt?: string;
}

export interface ModalitySearchFilter {
    id?: string;
    name?: string;
    yieldType?: YieldType;
}

