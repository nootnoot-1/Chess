package chess;

import java.util.Collection;

public class PawnImpl implements ChessPiece{
    ChessGame.TeamColor teamColor;

    public PawnImpl(ChessGame.TeamColor color) {
        teamColor = color;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
