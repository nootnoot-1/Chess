package chess;

import java.util.Collection;
import java.util.HashSet;

public class BishopImpl implements ChessPiece {
    ChessGame.TeamColor teamColor;

    public BishopImpl(ChessGame.TeamColor color) {
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
        return moves;
    }

}

//    @Override
//    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        int row = myPosition.getRow();
//        int column = myPosition.getColumn();
//        Collection<ChessMove> moves = new HashSet<>();
//
//        while (row < 8 && column < 8) {
//            row++;
//            column++;
//            moveHelper(row, column, moves, board, myPosition);
//        }
//
//        row = myPosition.getRow();
//        column = myPosition.getColumn();
//
//        while (row > 1 && column > 1) {
//            row--;
//            column--;
//            moveHelper(row, column, moves, board, myPosition);
//        }
//
//        row = myPosition.getRow();
//        column = myPosition.getColumn();
//
//        while (row < 8 && column > 1) {
//            row++;
//            column--;
//            moveHelper(row, column, moves, board, myPosition);
//        }
//
//        row = myPosition.getRow();
//        column = myPosition.getColumn();
//
//        while (row > 1 && column < 8) {
//            row--;
//            column++;
//            moveHelper(row, column, moves, board, myPosition);
//        }
//        return moves;
//    }
//
//    private void moveHelper(int row, int column, Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
//        PositionImpl position = new PositionImpl(row, column);
//        if (board.getPiece(position).getPieceType() != null) {
//            if (board.getPiece(position).getPieceType() != board.getPiece(myPosition).getPieceType()) {
//                MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
//                moves.add(move);
//            }
//        }
//        if (board.getPiece(position) == null && board.getPiece(position).getPieceType() != board.getPiece(myPosition).getPieceType()) {
//            MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
//            moves.add(move);
//        }
//    }
//}

//    PositionImpl position = new PositionImpl(row, column);
//            if (board.getPiece(position) == null) {
//                    MoveImpl move = new MoveImpl((PositionImpl)myPosition, position, null);
//                    moves.add(move);
//                    } else if (board.getPiece(position).getPieceType() == board.getPiece(myPosition).getPieceType()) {
//                    break;
//                    } else if (board.getPiece(position).getPieceType() != board.getPiece(myPosition).getPieceType()) {
//                    board.addPiece(position,this);
//                    } else {break;}
