interface RequestState<T> {
    status: 'idle' | 'loading' | 'success' | 'error';
    data: T | null;
    errorMessage: string | null;
}