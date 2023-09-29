package chess;

import java.util.Collection;

public class BishopImpl implements ChessPiece{
    PositionImpl position;
    ChessGame.TeamColor teamColor;

    public BishopImpl(ChessGame.TeamColor color, int row, int column) {
        position = new PositionImpl(row, column);
        teamColor = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
