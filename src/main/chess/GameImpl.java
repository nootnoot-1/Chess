package chess;

import java.util.Collection;
import java.util.Map;
//TODO: The issue is that I am testing if I can move a rook while I am in check, which I should not be able to do, but for some reason the test passes, but the rook also moves.
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
        BoardImpl oldBoard = copyBoard(board);
        Collection<ChessMove> moves = board.getPiece(move.getStartPosition()).pieceMoves(board,move.getStartPosition());
        if (!moves.contains(move)) {
            throw new InvalidMoveException();
        } else if (teamturn != board.getPiece(move.getStartPosition()).getTeamColor()) {
            throw new InvalidMoveException();
        }
        if (board.getPiece(move.getEndPosition()) == null) {
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                board.removePiece(move.getStartPosition());
            } else {
                if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                    QueenImpl queen = new QueenImpl(teamturn);
                    board.addPiece(move.getEndPosition(), queen);
                    board.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                    RookImpl rook = new RookImpl(teamturn);
                    board.addPiece(move.getEndPosition(), rook);
                    board.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                    BishopImpl bishop = new BishopImpl(teamturn);
                    board.addPiece(move.getEndPosition(), bishop);
                    board.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                    KnightImpl knight = new KnightImpl(teamturn);
                    board.addPiece(move.getEndPosition(), knight);
                    board.removePiece(move.getStartPosition());
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
                }
            }
            board.removePiece(move.getStartPosition());
        }


        if (isInCheck(teamturn)) {
            board = oldBoard;
            throw new InvalidMoveException();
        }

        if (teamturn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return board.inCheck(teamColor);
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        PositionImpl kingPosition = board.findKing(teamColor);
        return board.getPiece(kingPosition).pieceMoves(board, kingPosition).isEmpty();
    }

    @Override
    public void setBoard(ChessBoard board1) {
         board = (BoardImpl) board1;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }

    private BoardImpl copyBoard(BoardImpl boardtc) {
        BoardImpl copyBoard = new BoardImpl();

        for (int i = 8; i > 0; --i) {
            for (int j = 1; j < 9; ++j) {
                PositionImpl position = new PositionImpl(i,j);
                if (boardtc.getPiece(position) != null) {
                    copyBoard.addPiece(position, boardtc.getPiece(position));
                }
            }
        }

        return copyBoard;
    }
}
