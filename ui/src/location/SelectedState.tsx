import {createContext, FC, PropsWithChildren, useContext, useState} from "react";
import {StateCode} from "../nav/State";

type SetSelectedState = (selectedState: StateCode) => void;
type UseSelectedState = {
    selectedState: StateCode;
    setSelectedState: SetSelectedState;
}

const SelectedStateContext = createContext<UseSelectedState | undefined>(undefined);

export const SelectedStateProvider: FC<PropsWithChildren> = ({children}) => {
    const [selectedState, setSelectedState] = useState<StateCode>("US");
    return (
        <SelectedStateContext.Provider value={{selectedState, setSelectedState}}>
            {children}
        </SelectedStateContext.Provider>
    );
}

export function useSelectedState(): UseSelectedState {
    const useSelectedStateContext = useContext(SelectedStateContext);
    if (useSelectedStateContext === undefined) {
        throw new Error('useSelectedState must be used within a SelectedStateProvider');
    }
    return useSelectedStateContext;
}