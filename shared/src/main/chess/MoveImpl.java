package chess;

import java.util.Objects;

public class MoveImpl implements ChessMove{

    private final PositionImpl startposition;
    private final PositionImpl endposition;
    private final ChessPiece.PieceType promotionpiece;

    public MoveImpl(PositionImpl start, PositionImpl end, ChessPiece.PieceType piece) {
        startposition = new PositionImpl(start.getRow(), start.getColumn());
        endposition = new PositionImpl(end.getRow(), end.getColumn());
        promotionpiece = piece;
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
        return promotionpiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveImpl move)) return false;
        return Objects.equals(startposition, move.startposition) && Objects.equals(endposition, move.endposition) && promotionpiece == move.promotionpiece;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startposition, endposition, promotionpiece);
    }

    @Override
    public String toString() {
        return "MoveImpl{" +
                "startposition=" + startposition +
                ", endposition=" + endposition +
                ", promotionpiece=" + promotionpiece +
                '}';
    }
}
