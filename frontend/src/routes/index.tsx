import { Route, Routes } from "react-router-dom"

import MainLayout from "../layouts/MainLayout"

import ModalityListings from '../pages/Definitions/Modalities';

import HomePage from "../pages/HomePage"
import NotFoundPage from "../pages/NotFoundPage"
import ModalityOperations from "../pages/Definitions/Modalities/Operations";

const RouteComponent: React.FC = () => {
    return (
        <Routes>
            <Route path="/" element={<MainLayout />}>
                <Route index element={<HomePage />} />

                <Route path="definitions/modalities" element={<ModalityListings />} />
                <Route path="definitions/modalities/:operation/:id?" element={<ModalityOperations />} />

                <Route path="*" element={<NotFoundPage />} />
            </Route>
        </Routes>
    )
}

export default RouteComponent;