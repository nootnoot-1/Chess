package chess;

import java.util.Collection;

public class QueenImpl implements ChessPiece{
    PositionImpl position;
    ChessGame.TeamColor teamColor;

    public QueenImpl(ChessGame.TeamColor color, int row, int column) {
        position = new PositionImpl(row, column);
        teamColor = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
