package dev.liaskarllate.finmathly.shared.enums;

import dev.liaskarllate.finmathly.asset.entity.Asset;
import dev.liaskarllate.finmathly.shared.exception.ForbiddenArgumentException;
import dev.liaskarllate.finmathly.shared.exception.MissingArgumentException;

public enum YieldType {
    FIXED {
        @Override
        public void validate(Asset asset) {
            this.require(asset.hasInterestRate(),
                    "The asset interest rate is missing, which is required for fixed modalities. Please, provide an interest rate.");
            this.forbid(asset.hasMarketIndex(),
                    "The asset market index must not be set for fixed modalities. Please, remove the asset market index or choose a modality with a different yield type.");
            this.forbid(asset.hasIndexPercentage(),
                    "The asset index percentage must not be set for fixed modalities. Please, remove the asset index percentage or choose a modality with a different yield type.");
        }
    },
    FLOATING {
        @Override
        public void validate(Asset asset) {
            this.forbid(asset.hasInterestRate(),
                    "The asset interest rate must not be set for floating modalities. Please, remove the asset interest rate or choose a modality with a different yield type.");
            this.require(asset.hasMarketIndex(),
                    "The asset market index is missing, which is required for floating modalities. Please, provide the asset market index.");
            this.require(asset.hasIndexPercentage(),
                    "The asset index percentage rate is missing, which is required for floating modalities. Please, provide the asset index percentage rate.");
        }
    },
    HYBRID {
        @Override
        public void validate(Asset asset) {
            this.require(asset.hasInterestRate(),
                    "The asset interest rate is missing, which is required for hybrid modalities. Please, provide an interest rate.");
            this.require(asset.hasMarketIndex(),
                    "The asset market index is missing, which is required for hybrid modalities. Please, provide the asset market index.");
            this.require(asset.hasIndexPercentage(),
                    "The asset index percentage rate is missing, which is required for hybrid modalities. Please, provide the asset index percentage rate.");
        }
    };

    public abstract void validate(Asset asset);

    protected void require(boolean condition, String message) {
        if (!condition)
            throw new MissingArgumentException(message);
    }

    protected void forbid(boolean condition, String message) {
        if (condition)
            throw new ForbiddenArgumentException(message);
    }
}