export class FrontendError {
    public static readonly DEFAULT_ERROR_MESSAGE =
        'An unexpected error occurred. Please, try again later.';

    constructor(public readonly message: string = FrontendError.DEFAULT_ERROR_MESSAGE) {}
}

export enum OperationType {
    READ = 'read',
    CREATE = 'create',
    UPDATE = 'update',
    DELETE = 'delete'
}

