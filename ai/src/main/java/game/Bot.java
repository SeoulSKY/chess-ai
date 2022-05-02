package game;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Bot {

    protected Game game;

    protected Map<State, Double> transpositionTable;

    protected int numNodeExpanded;

    public Bot(Game game) {
        this.game = game;
        this.transpositionTable = new HashMap<>();
        this.numNodeExpanded = 0;
    }

    /**
     * Decide the next action from the given state
     * @param state the state
     * @return the decision
     */
    public DecisionRecord decide(State state) {
        Instant startTime = Instant.now();

        this.transpositionTable.clear();
        this.numNodeExpanded = 0;

        double minimaxValue = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        State nextState = null;
        this.numNodeExpanded++;

        double botBest = Double.NEGATIVE_INFINITY;
        double humanBest = Double.POSITIVE_INFINITY;

        for (Action action : this.game.actions(state)) {
            State result = this.game.result(state, action);
            double value = this.minValue(result, botBest, humanBest, 1);

            if (value > minimaxValue) {
                minimaxValue = value;
                bestAction = action;
                nextState = result;
            }

            botBest = Math.max(botBest, minimaxValue);
        }

        Instant endTime = Instant.now();
        Duration timeTaken = Duration.between(startTime, endTime);

        return new DecisionRecord(timeTaken, minimaxValue, bestAction, nextState, this.numNodeExpanded);
    }

    /**
     * Get the max value of the given state
     * @param state the state
     * @param maxBest the best value for the max
     * @param minBest the best value for the min
     * @param depth the current depth
     * @return the value
     */
    protected double maxValue(State state, double maxBest, double minBest, int depth) {
        if (this.transpositionTable.containsKey(state)) {
            return this.transpositionTable.get(state);
        } else if (this.game.isTerminal(state)) {
            return this.game.utility(state);
        } else if (this.game.shouldCutOff(depth)) {
            return this.game.evaluate(state);
        }

        double maxBestHere = Double.NEGATIVE_INFINITY;
        this.numNodeExpanded++;

        for (Action action : this.game.actions(state)) {
            State result = this.game.result(state, action);
            double value = this.minValue(result, maxBest, minBest, depth+1);

            if (value > maxBestHere) {
                maxBestHere = value;
            }

            if (maxBestHere >= minBest) {
                return maxBestHere;
            }

            maxBest = Math.max(maxBest, maxBestHere);
        }

        this.transpositionTable.put(state, maxBestHere);

        return maxBestHere;
    }

    /**
     * Get the min value of the given state
     * @param state the state
     * @param maxBest the best value for the max
     * @param minBest the best value for the min
     * @param depth the current depth
     * @return the value
     */
    protected double minValue(State state, double maxBest, double minBest, int depth) {
        if (this.transpositionTable.containsKey(state)) {
            return this.transpositionTable.get(state);
        } else if (this.game.isTerminal(state)) {
            return this.game.utility(state);
        } else if (this.game.shouldCutOff(depth)) {
            return this.game.evaluate(state);
        }

        double minBestHere = Double.POSITIVE_INFINITY;
        this.numNodeExpanded++;

        for (Action action : this.game.actions(state)) {
            State result = this.game.result(state, action);
            double value = this.maxValue(result, maxBest, minBest, depth+1);

            if (value < minBestHere) {
                minBestHere = value;
            }

            if (minBestHere <= maxBest) {
                return minBestHere;
            }

            minBest = Math.min(minBest, minBestHere);
        }

        this.transpositionTable.put(state, minBestHere);

        return minBestHere;
    }
}
