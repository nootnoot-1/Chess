package chess;

import java.util.Collection;
import java.util.HashSet;

public class QueenImpl implements ChessPiece{
    ChessGame.TeamColor teamColor;

    public QueenImpl(ChessGame.TeamColor color) {
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
        int row = myPosition.getRow();
        int column = myPosition.getColumn();
        Collection<ChessMove> moves = new HashSet<>();

        while (row < 8 && column < 8) {
            ++row;
            ++column;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }

        row = myPosition.getRow();
        column = myPosition.getColumn();
        while (row > 1 && column < 8) {
            --row;
            ++column;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }

        row = myPosition.getRow();
        column = myPosition.getColumn();
        while (row < 8 && column > 1) {
            ++row;
            --column;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }

        row = myPosition.getRow();
        column = myPosition.getColumn();
        while (row > 1 && column > 1) {
            --row;
            --column;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }
        row = myPosition.getRow();
        column = myPosition.getColumn();
        while (row < 8) {
            ++row;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }

        row = myPosition.getRow();
        while (row > 1) {
            --row;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }

        row = myPosition.getRow();
        while (column > 1) {
            --column;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }

        column = myPosition.getColumn();
        while (column < 8) {
            ++column;
            PositionImpl position = new PositionImpl(row,column);
            if (board.getPiece(position) == null) {
                ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                moves.add(move);
            } else {
                if (board.getPiece(position).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                }
                if (board.getPiece(position).getTeamColor() != board.getPiece(myPosition).getTeamColor() && board.getPiece(position).getTeamColor() != null) {
                    //TODO: CAPTURE
                    ChessMove move = new MoveImpl((PositionImpl)myPosition, position, null);
                    moves.add(move);
                    break;
                }
            }
        }
        return moves;
    }
}
