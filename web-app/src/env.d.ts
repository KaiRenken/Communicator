/// <reference types="vite/client" />

// Activate IntelliSence for TypeScript
// see .env and .env.production
interface ImportMetaEnv {
    readonly VITE_OVERRIDE_SERVER_URL: string
}

interface ImportMeta {
    readonly env: ImportMetaEnv
}