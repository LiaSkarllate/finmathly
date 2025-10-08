import { Route, Routes } from "react-router-dom"

import MainLayout from "../layouts/MainLayout"

import CreateModalityPage from "../pages/modality/CreateModalityPage"
import ModalitiesPage from "../pages/modality/ModalitiesPage"
import ModalityPage from "../pages/modality/ModalityPage"
import UpdateModalityPage from "../pages/modality/UpdateModalityPage"

import HomePage from "../pages/shared/HomePage"
import NotFoundPage from "../pages/shared/NotFoundPage"

export const RouteComponent: React.FC = () => {
    return (
        <Routes>
            <Route path="/" element={<MainLayout />}>
                <Route index element={<HomePage />} />

                <Route path="modalities" element={<ModalitiesPage />} />
                <Route
                    path="modalities/create"
                    element={<CreateModalityPage />}
                />
                <Route
                    path="modalities/update/:id"
                    element={<UpdateModalityPage />}
                />
                <Route
                    path="modalities/:id"
                    element={<ModalityPage />}
                />

                <Route path="*" element={<NotFoundPage />} />
            </Route>
        </Routes>
    )
}