/// <reference types="vite/client" />

interface ImportMetaEnv {
    readonly VITE_FINMATHLY_APP_MONOLITH_BACKEND_URL: string;
    readonly VITE_FINMATHLY_APP_USER_INACTIVITY_TIME: string;
}

interface ImportMeta {
    readonly env: ImportMetaEnv;
}

interface Window {
    config?: {
        VITE_FINMATHLY_APP_MONOLITH_BACKEND_URL?: string,
        VITE_FINMATHLY_APP_USER_INACTIVITY_TIME?: string
    };
}