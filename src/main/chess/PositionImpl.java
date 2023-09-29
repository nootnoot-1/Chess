package chess;

public class PositionImpl implements ChessPosition{
    private final int row;
    private final int column;

    public PositionImpl(int ro, int co) {
        row = ro;
        column = co;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public int getIndex() {
        return ((row-1)*8 + column - 1);
    }
}
