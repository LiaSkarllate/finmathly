import { action, computed, makeAutoObservable, observable, runInAction } from 'mobx';

import { ModalityDTO, ModalitySearchFilter } from "../../models/Modality.types";
import { FrontendError } from "../../models/shared/types";

import { create, deleteById, findByFilter, update } from "../../services/api";

import { normalizeToArray } from '../../utils/http';

export default class ModalitiesStore {
    private _modalities: ModalityDTO[] = [];
    private _selectedModality?: ModalityDTO = undefined;

    loading: boolean = false;
    error?: FrontendError = undefined;

    constructor() {
        makeAutoObservable(this, {
            error: observable,
            loading: observable,

            getModalities: action,
            postModality: action,
            putModality: action,
            deleteModality: action,

            clean: action,

            modalities: computed,
        });
    }

    get modalities(): ModalityDTO[] {
        return this._modalities.slice();
    }

    get selectedModality(): ModalityDTO | undefined {
        return this._selectedModality;
    }

    clean() {
        this._modalities = [];
        this._selectedModality = undefined;

        this.loading = false;
        this.error = undefined;
    }

    async getModalities(filter?: ModalitySearchFilter) {
        this.loading = true;
        this.error = undefined;
        this._modalities = [];

        try {
            const response = await findByFilter<ModalitySearchFilter>('/modalities', filter);
            const array = normalizeToArray<ModalityDTO>(response);

            runInAction(() => {
                this._modalities = array;
                this._selectedModality = array.length > 0 ? array[0] : undefined; 
                this.loading = false;
            });
        } catch (error) {
            runInAction(() => {
                this.error = error as FrontendError;
                this.loading = false;
            });

            throw error;
        }
    }

    async postModality(modality: ModalityDTO) {
        this.loading = true;
        this.error = undefined;

        try {
            await create<ModalityDTO>('/modalities', modality);

            runInAction(() => {
                this.loading = false;
            });
        } catch (error) {
            runInAction(() => {
                this.error = error as FrontendError;
                this.loading = false;
            });

            throw error;
        }
    };

    async putModality(modality: ModalityDTO) {
        this.loading = true;
        this.error = undefined;

        try {
            if (!modality?.id) {
                throw new FrontendError("The id is required to update a modality. Please, provide one.");
            }

            await update<ModalityDTO>('/modalities', modality.id, modality);

            runInAction(() => {
                this.loading = false;
            });
        } catch (error) {
            runInAction(() => {
                this.error = error as FrontendError;
                this.loading = false;
            });

            throw error;
        }
    };

    deleteModality = async () => {
        this.loading = true;
        this.error = undefined;

        try {
            if (!this._selectedModality?.id) {
                throw new FrontendError("The id is required for delete the modality.");
            }

            await deleteById('/modalities', `${this._selectedModality?.id}`);

            runInAction(() => {
                this.loading = false;
            });
        } catch (error) {
            runInAction(() => {
                this.error = error as FrontendError;
                this.loading = false;
            });

            throw error;
        }
    };
}