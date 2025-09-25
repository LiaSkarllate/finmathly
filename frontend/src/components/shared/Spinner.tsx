import ClipLoader from 'react-spinners/ClipLoader';
import { CSSProperties } from 'react';

const override: CSSProperties = {
    display: 'block',
    margin: '100px auto',
};

interface SpinnerProps {
    loading?: boolean;
}

const Spinner: React.FC<SpinnerProps> = ({ loading = true }) => {
    return (
        <ClipLoader
            color='#4338ca'
            loading={loading}
            cssOverride={override}
            size={150}
        />
    );
};

export default Spinner;
