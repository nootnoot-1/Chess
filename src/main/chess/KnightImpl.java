package chess;

import java.util.Collection;

public class KnightImpl implements ChessPiece{
    PositionImpl position;
    ChessGame.TeamColor teamColor;

    public KnightImpl(ChessGame.TeamColor color, int row, int column) {
        position = new PositionImpl(row, column);
        teamColor = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
