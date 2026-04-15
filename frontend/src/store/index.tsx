import React, { useMemo } from "react";
import RootStore from "./RootStore";

const RootStoreContext = React.createContext<RootStore | null>(null);

interface RootStateProviderProps {
	children: React.ReactNode;
}

export const RootStateProvider: React.FC<RootStateProviderProps> = ({ children }) => {
	const rootStore = useMemo(() => new RootStore(), []);

	return (
		<RootStoreContext.Provider value={rootStore}>
			{children}
		</RootStoreContext.Provider>
	);
};

export default function useRootStore(): RootStore {
	const store = React.useContext(RootStoreContext);
	if (!store) throw new Error("The useRootStore must be used within a RootStateProvider. Please, wrap your component tree with <RootStateProvider>.");
	return store;
};