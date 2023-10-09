package chess;

import java.util.Collection;

public class GameImpl implements ChessGame{
    TeamColor teamturn = TeamColor.WHITE;
    BoardImpl board = new BoardImpl();
    @Override
    public TeamColor getTeamTurn() {
        return teamturn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        teamturn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (board.getPiece(startPosition) == null) {
            return null;
        } else {
            return board.getPiece(startPosition).pieceMoves(board,startPosition);
        }
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (teamturn != board.getPiece(move.getStartPosition()).getTeamColor()) {
            throw new InvalidMoveException("not your turn");
        }
        Collection<ChessMove> moves = board.getPiece(move.getStartPosition()).pieceMoves(board,move.getStartPosition());
        if (!moves.contains(move)) {
            throw new InvalidMoveException();
        } else {
            if (board.getPiece(move.getEndPosition()) == null) {
                if (move.getPromotionPiece() == null) {
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                    board.removePiece(move.getStartPosition());
                } else {
                    if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                        QueenImpl queen = new QueenImpl(teamturn);
                        board.addPiece(move.getEndPosition(), queen);
                    }
                    if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                        RookImpl rook = new RookImpl(teamturn);
                        board.addPiece(move.getEndPosition(), rook);
                    }
                    if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                        BishopImpl bishop = new BishopImpl(teamturn);
                        board.addPiece(move.getEndPosition(), bishop);
                    }
                    if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                        KnightImpl knight = new KnightImpl(teamturn);
                        board.addPiece(move.getEndPosition(), knight);
                    } else {
                        throw new InvalidMoveException();
                    }
                }
            } else {
                board.removePiece(move.getEndPosition());
                if (move.getPromotionPiece() == null) {
                    board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                } else {
                    if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                        QueenImpl queen = new QueenImpl(teamturn);
                        board.addPiece(move.getEndPosition(), queen);
                    }
                    if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                        RookImpl rook = new RookImpl(teamturn);
                        board.addPiece(move.getEndPosition(), rook);
                    }
                    if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                        BishopImpl bishop = new BishopImpl(teamturn);
                        board.addPiece(move.getEndPosition(), bishop);
                    }
                    if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                        KnightImpl knight = new KnightImpl(teamturn);
                        board.addPiece(move.getEndPosition(), knight);
                    } else {
                        throw new InvalidMoveException();
                    }
                }
                board.removePiece(move.getStartPosition());
            }
        }
        if (teamturn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board1) {
         board = (BoardImpl) board1;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
