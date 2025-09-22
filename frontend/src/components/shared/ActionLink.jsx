import { Link } from 'react-router-dom';

const ActionLink = ({
    to,
    children
}) => (
    <Link
        to={to}
        className={"inline-block rounded-lg px-4 py-2 bg-indigo-600 text-white hover:bg-indigo-700 focus:outline-none focus-visible:ring-2 focus-visible:ring-indigo-500"}
    >
        {children}
    </Link>
);

export default ActionLink;
