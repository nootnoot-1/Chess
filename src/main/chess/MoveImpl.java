package chess;

public class MoveImpl implements ChessMove{

    private PositionImpl startposition;
    private PositionImpl endposition;

    public MoveImpl(PositionImpl start, PositionImpl end) {
        startposition = new PositionImpl(start.getRow(), start.getColumn());
        endposition = new PositionImpl(end.getRow(), end.getColumn());
    }

    @Override
    public ChessPosition getStartPosition() {
        return startposition;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endposition;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return null;
    }
}
