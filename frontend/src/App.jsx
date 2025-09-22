import {
    Route,
    createBrowserRouter,
    createRoutesFromElements,
    RouterProvider,
} from 'react-router-dom';

import {
    create as createModality,
    deleteById as deleteModalityById,
    update as updateModality,
} from './services/modality/requests.js';

import MainLayout from './layouts/MainLayout';
import HomePage from './pages/shared/HomePage';
import NotFoundPage from './pages/shared/NotFoundPage';

import ModalitiesPage from './pages/modality/ModalitiesPage';
import ModalityPage, { modalityLoader } from './pages/modality/ModalityPage';
import CreateModalityPage from './pages/modality/CreateModalityPage';
import UpdateModalityPage from './pages/modality/UpdateModalityPage';

const App = () => {
    const router = createBrowserRouter(
        createRoutesFromElements(
            <Route path='/' element={<MainLayout />}>
                <Route index element={<HomePage />} />
                <Route path='/modalities' element={<ModalitiesPage />} />
                <Route path='/modalities/create' element={<CreateModalityPage onCreateModality={createModality} />} />
                <Route
                    path='/modalities/update/:id'
                    element={<UpdateModalityPage onUpdateModality={updateModality} />}
                    loader={modalityLoader}
                />
                <Route
                    path='/modalities/:id'
                    element={<ModalityPage onDeleteModality={deleteModalityById} />}
                    loader={modalityLoader}
                />
                <Route path='*' element={<NotFoundPage />} />
            </Route>
        )
    );

    return <RouterProvider router={router} />;
};

export default App;
