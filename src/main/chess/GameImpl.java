package chess;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GameImpl implements ChessGame{
    TeamColor teamturn = TeamColor.WHITE;
    BoardImpl gameBoard = new BoardImpl();

    public GameImpl() {}

    public String serialize() {
        StringBuilder gamestring = new StringBuilder();
        Map<PositionImpl,ChessPiece> board = gameBoard.getBoard();

        if (teamturn == TeamColor.WHITE) {
            gamestring.append("W");
        } else {gamestring.append("B");}

        for (Map.Entry<PositionImpl, ChessPiece> it : board.entrySet()) {
            gamestring.append(it.getKey().getRow());
            gamestring.append(it.getKey().getColumn());
            if (it.getValue().getTeamColor() == TeamColor.WHITE) {
                if (it.getValue().getPieceType() == ChessPiece.PieceType.KING) {
                    gamestring.append("K");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.QUEEN) {
                    gamestring.append("Q");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.BISHOP) {
                    gamestring.append("B");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    gamestring.append("N");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.ROOK) {
                    gamestring.append("R");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.PAWN) {
                    gamestring.append("P");
                }
            } else if (it.getValue().getTeamColor() == TeamColor.BLACK) {
                if (it.getValue().getPieceType() == ChessPiece.PieceType.KING) {
                    gamestring.append("k");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.QUEEN) {
                    gamestring.append("q");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.BISHOP) {
                    gamestring.append("b");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    gamestring.append("n");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.ROOK) {
                    gamestring.append("r");
                } else if (it.getValue().getPieceType() == ChessPiece.PieceType.PAWN) {
                    gamestring.append("p");
                }
            }
        }
        return gamestring.toString();
    }



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
        if (gameBoard.getPiece(startPosition) == null) {
            return null;
        }
        Collection<ChessMove> moves = gameBoard.getPiece(startPosition).pieceMoves(gameBoard,startPosition);
        Collection<ChessMove> safeMoves = new HashSet<>();

        for (ChessMove move : moves) {
            BoardImpl board = copyBoard(gameBoard);
            secureMove(move, board, gameBoard.getPiece(startPosition).getTeamColor());
            if (!board.inCheck(gameBoard.getPiece(startPosition).getTeamColor())) {
                safeMoves.add(move);
            }
        }

        return safeMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        BoardImpl oldBoard = copyBoard(gameBoard);
        Collection<ChessMove> moves = gameBoard.getPiece(move.getStartPosition()).pieceMoves(gameBoard,move.getStartPosition());
        if (!moves.contains(move)) {
            throw new InvalidMoveException();
        } else if (teamturn != gameBoard.getPiece(move.getStartPosition()).getTeamColor()) {
            throw new InvalidMoveException();
        }
        if (gameBoard.getPiece(move.getEndPosition()) == null) {
            if (move.getPromotionPiece() == null) {
                gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
                gameBoard.removePiece(move.getStartPosition());
            } else {
                if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                    QueenImpl queen = new QueenImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), queen);
                    gameBoard.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                    RookImpl rook = new RookImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), rook);
                    gameBoard.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                    BishopImpl bishop = new BishopImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), bishop);
                    gameBoard.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                    KnightImpl knight = new KnightImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), knight);
                    gameBoard.removePiece(move.getStartPosition());
                }
            }
        } else {
            gameBoard.removePiece(move.getEndPosition());
            if (move.getPromotionPiece() == null) {
                gameBoard.addPiece(move.getEndPosition(), gameBoard.getPiece(move.getStartPosition()));
            } else {
                if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                    QueenImpl queen = new QueenImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), queen);
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                    RookImpl rook = new RookImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), rook);
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                    BishopImpl bishop = new BishopImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), bishop);
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                    KnightImpl knight = new KnightImpl(teamturn);
                    gameBoard.addPiece(move.getEndPosition(), knight);
                }
            }
            gameBoard.removePiece(move.getStartPosition());
        }

        if (isInCheck(teamturn)) {
            gameBoard = oldBoard;
            throw new InvalidMoveException();
        }

        if (teamturn == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    private void secureMove(ChessMove move, BoardImpl board, TeamColor color) {
        if (board.getPiece(move.getEndPosition()) == null) {
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
                board.removePiece(move.getStartPosition());
            } else {
                if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                    QueenImpl queen = new QueenImpl(color);
                    board.addPiece(move.getEndPosition(), queen);
                    board.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                    RookImpl rook = new RookImpl(color);
                    board.addPiece(move.getEndPosition(), rook);
                    board.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                    BishopImpl bishop = new BishopImpl(color);
                    board.addPiece(move.getEndPosition(), bishop);
                    board.removePiece(move.getStartPosition());
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                    KnightImpl knight = new KnightImpl(color);
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
                    QueenImpl queen = new QueenImpl(color);
                    board.addPiece(move.getEndPosition(), queen);
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                    RookImpl rook = new RookImpl(color);
                    board.addPiece(move.getEndPosition(), rook);
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                    BishopImpl bishop = new BishopImpl(color);
                    board.addPiece(move.getEndPosition(), bishop);
                }
                if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                    KnightImpl knight = new KnightImpl(color);
                    board.addPiece(move.getEndPosition(), knight);
                }
            }
            board.removePiece(move.getStartPosition());
        }
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        return gameBoard.inCheck(teamColor);
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> moves = gameBoard.getAllMoves(teamColor);
        for (ChessMove move : moves) {
            BoardImpl board = copyBoard(gameBoard);
            secureMove(move, board, teamColor);
            if (!board.inCheck(teamColor)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return isInCheckmate(teamColor);
    }

    @Override
    public void setBoard(ChessBoard board1) {
         gameBoard = (BoardImpl) board1;
    }

    @Override
    public ChessBoard getBoard() {
        return gameBoard;
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
