import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AI {
    public int[] getComputerAction(char[][] board) {
        char[][] state = Board.copy(board);
        Object[] abmax = abmax(state, -100, +100);
        printBoard(state);
        System.out.println("AI has chosen: " + Arrays.toString((int[]) abmax[1]) + " with value " + abmax[0]);
        return (int[]) abmax[1];
    }

    public List<int[]> getAllNextMoves(char[][] board) {
        List<int[]> list = new LinkedList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                char cell = board[x][y];
                if (cell == '\u0000') {
                    list.add(new int[]{x, y});
                }
            }
        }
        Collections.shuffle(list);
        return list;
    }

    public Object[] abmin(char[][] state, int alpha, int beta) {
        if (isTerminalState(state)) return new Object[]{evaluation(state), null};
        List<int[]> moves = getAllNextMoves(state);
        int minValue = Integer.MAX_VALUE;
        int[] bestMove = null;
        for (int[] move : moves) {
            char[][] new_state = Board.copy(state);
            new_state[move[0]][move[1]] = 'X';
            Object[] abmax = abmax(new_state, alpha, beta);
            int result = (int) abmax[0];
            int[] m = (int[]) abmax[1];

            if (result < minValue) {
                minValue = result;
                bestMove = move;
            }
            if (minValue <= alpha) return new Object[]{minValue, bestMove};
            beta = Math.min(minValue, beta);
        }
        return new Object[]{minValue, bestMove};
    }

    public Object[] abmax(char[][] state, int alpha, int beta) {
        if (isTerminalState(state)) return new Object[]{evaluation(state), null};
        List<int[]> moves = getAllNextMoves(state);
        int maxValue = Integer.MIN_VALUE;
        int[] bestMove = null;
        for (int[] move : moves) {
            char[][] new_state = Board.copy(state);
            new_state[move[0]][move[1]] = 'O';
            Object[] abmin = abmin(new_state, alpha, beta);
            int result = (int) abmin[0];
            int[] m = (int[]) abmin[1];

            if (result > maxValue) {
                maxValue = result;
                bestMove = move;
            }
            if (maxValue >= beta) {
                return new Object[]{maxValue, bestMove};
            }
            alpha = Math.max(maxValue, alpha);
        }
        return new Object[]{maxValue, bestMove};
    }

    /**
     * @param state
     * @return
     * @requires state is a terminal state
     */
    public static int evaluation(char[][] state) {
        if (Board.checkWin('X', state)) return -1;
        if (Board.checkWin('O', state)) return 1;
        return 0;
    }

    public static boolean isTerminalState(char[][] state) {
        return Board.checkWin('X', state) || Board.checkWin('O', state) || Board.checkDraw(state);
    }

    public static void printBoard(char[][] board) {
        for (int x = 0; x < 3; x++) {
            System.out.print('[');
            for (int y = 0; y < 3; y++) {
                System.out.print(board[y][x] + ", ");
            }
            System.out.print("\b\b]\n");
        }
    }
}
