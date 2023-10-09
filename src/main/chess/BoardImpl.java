package chess;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BoardImpl implements ChessBoard{
    private Map<PositionImpl,ChessPiece> board = new HashMap<>();

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board.put((PositionImpl)position, piece);
    }

    public void removePiece(ChessPosition position) {board.remove(position);}

    public PositionImpl findKing(ChessGame.TeamColor teamColor) {
        for (Map.Entry<PositionImpl, ChessPiece> it : board.entrySet()) {
            if (it.getValue().getPieceType() == ChessPiece.PieceType.KING && it.getValue().getTeamColor() == teamColor) {
                return it.getKey();
            }
        }
        return null;
    }

    public boolean inCheck(ChessGame.TeamColor teamColor) {
        PositionImpl kingPosition = findKing(teamColor);
        if (teamColor == ChessGame.TeamColor.WHITE) {
            for (Map.Entry<PositionImpl, ChessPiece> piece : board.entrySet()) {
                if (piece.getValue().getTeamColor() == ChessGame.TeamColor.BLACK) {
                    Collection<ChessMove> moves = piece.getValue().pieceMoves(this, piece.getKey());
                    for (ChessMove move : moves) {
                        if (kingPosition != null) {
                            if (move.getEndPosition().getRow() == kingPosition.getRow() && move.getEndPosition().getColumn() == kingPosition.getColumn()) {
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            for (Map.Entry<PositionImpl, ChessPiece> piece : board.entrySet()) {
                if (piece.getValue().getTeamColor() == ChessGame.TeamColor.WHITE) {
                    Collection<ChessMove> moves = piece.getValue().pieceMoves(this, piece.getKey());
                    for (ChessMove move : moves) {
                        if (kingPosition != null) {
                            if (move.getEndPosition().getRow() == kingPosition.getRow() && move.getEndPosition().getColumn() == kingPosition.getColumn()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board.get((PositionImpl)position);
    }

    @Override
    public void resetBoard() {
        board.clear();
        ChessPiece pawnWhite = new PawnImpl(ChessGame.TeamColor.WHITE);
        for (int column = 1; column <= 8; ++column) {
            PositionImpl position = new PositionImpl(2,column);
            addPiece(position, pawnWhite);
        }
        ChessPiece rookWhite = new RookImpl(ChessGame.TeamColor.WHITE);
        PositionImpl rookwp1 = new PositionImpl(1,1);
        addPiece(rookwp1, rookWhite);
        PositionImpl rookwp2 = new PositionImpl(1,8);
        addPiece(rookwp2, rookWhite);

        ChessPiece knightWhite = new KnightImpl(ChessGame.TeamColor.WHITE);
        PositionImpl knightwp1 = new PositionImpl(1,2);
        addPiece(knightwp1, knightWhite);
        PositionImpl knightwp2 = new PositionImpl(1,7);
        addPiece(knightwp2, knightWhite);

        ChessPiece bishopWhite = new BishopImpl(ChessGame.TeamColor.WHITE);
        PositionImpl bishopwp1 = new PositionImpl(1,3);
        addPiece(bishopwp1, bishopWhite);
        PositionImpl bishopwp2 = new PositionImpl(1,6);
        addPiece(bishopwp2, bishopWhite);

        ChessPiece queenWhite = new QueenImpl(ChessGame.TeamColor.WHITE);
        PositionImpl queenwp = new PositionImpl(1,4);
        addPiece(queenwp, queenWhite);

        ChessPiece kingWhite = new KingImpl(ChessGame.TeamColor.WHITE);
        PositionImpl kingwp = new PositionImpl(1,5);
        addPiece(kingwp, kingWhite);

        resetBoardHelper();
    }
    private void resetBoardHelper() {
        ChessPiece pawnBlack = new PawnImpl(ChessGame.TeamColor.BLACK);
        for (int column = 1; column <= 8; ++column) {
            PositionImpl position = new PositionImpl(7,column);
            addPiece(position,pawnBlack);
        }
        ChessPiece rookWhite = new RookImpl(ChessGame.TeamColor.BLACK);
        PositionImpl rookwp1 = new PositionImpl(8,1);
        addPiece(rookwp1, rookWhite);
        PositionImpl rookwp2 = new PositionImpl(8,8);
        addPiece(rookwp2, rookWhite);

        ChessPiece knightWhite = new KnightImpl(ChessGame.TeamColor.BLACK);
        PositionImpl knightwp1 = new PositionImpl(8,2);
        addPiece(knightwp1, knightWhite);
        PositionImpl knightwp2 = new PositionImpl(8,7);
        addPiece(knightwp2, knightWhite);

        ChessPiece bishopWhite = new BishopImpl(ChessGame.TeamColor.BLACK);
        PositionImpl bishopwp1 = new PositionImpl(8,3);
        addPiece(bishopwp1, bishopWhite);
        PositionImpl bishopwp2 = new PositionImpl(8,6);
        addPiece(bishopwp2, bishopWhite);

        ChessPiece queenWhite = new QueenImpl(ChessGame.TeamColor.BLACK);
        PositionImpl queenwp = new PositionImpl(8,4);
        addPiece(queenwp, queenWhite);

        ChessPiece kingWhite = new KingImpl(ChessGame.TeamColor.BLACK);
        PositionImpl kingwp = new PositionImpl(8,5);
        addPiece(kingwp, kingWhite);
    }
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();
        for (int i = 8; i > 0; --i) {
            for (int j = 1; j < 9; ++j) {
                PositionImpl position = new PositionImpl(i,j);
                if (board.containsKey(position)) {
                    string.append("|");
                    string.append(toStringHelper(board.get(position).getPieceType(), board.get(position).getTeamColor()));
                    string.append("|");
                } else {
                    string.append("| |");
                }
            }
            string.append("\n");
        }
        return string.toString();
    }

    private String toStringHelper(ChessPiece.PieceType type, ChessGame.TeamColor color) {
        if (type == ChessPiece.PieceType.KING) {
            if (color == ChessGame.TeamColor.WHITE) {
                return "K";
            } else {
                return "k";
            }
        }
        if (type == ChessPiece.PieceType.QUEEN) {
            if (color == ChessGame.TeamColor.WHITE) {
                return "Q";
            } else {
                return "q";
            }
        }
        if (type == ChessPiece.PieceType.BISHOP) {
            if (color == ChessGame.TeamColor.WHITE) {
                return "B";
            } else {
                return "b";
            }
        }
        if (type == ChessPiece.PieceType.KNIGHT) {
            if (color == ChessGame.TeamColor.WHITE) {
                return "N";
            } else {
                return "n";
            }
        }
        if (type == ChessPiece.PieceType.ROOK) {
            if (color == ChessGame.TeamColor.WHITE) {
                return "R";
            } else {
                return "r";
            }
        }
        if (type == ChessPiece.PieceType.PAWN) {
            if (color == ChessGame.TeamColor.WHITE) {
                return "P";
            } else {
                return "p";
            }
        }
        return "x";
    }
}
