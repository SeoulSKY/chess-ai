import React, {useContext} from "react";

interface GameState {
    isThinking: boolean,
    setIsThinking: React.Dispatch<React.SetStateAction<boolean>>
    intelligenceLevel: React.MutableRefObject<number>
    promotingIcon: React.MutableRefObject<string>
    minimaxValue: React.MutableRefObject<number>
}

export const GameContext = React.createContext<GameState | undefined>(undefined);

export function useGameContext(): GameState {
    let context = useContext(GameContext);
    if (context === undefined) {
        throw Error("useGameContext must be used within GameContextProvider");
    }

    return context;
}