package ui;

import chess.BoardImpl;
import chess.ChessGame;
import chess.ChessPiece;
import chess.PositionImpl;
import com.sun.tools.jconsole.JConsoleContext;
import models.Game;

import java.util.Map;
import java.util.Objects;

public class Printer {

    public void printGame(Game game, ChessGame.TeamColor teamcolor) {
        BoardImpl boardImpl = (BoardImpl) game.getGame().getBoard();
        if (teamcolor == ChessGame.TeamColor.BLACK) {
            makeReverseString(boardImpl.getBoard());
        } else {
            makeString(boardImpl.getBoard());
        }
        //System.out.print(EscapeSequences.SET_BG_COLOR_MAGENTA + "                              ");
        //System.out.println("\033[0m");
        System.out.print("\033[0m");
    }

    public void makeString(Map<PositionImpl,ChessPiece> board){
        System.out.print("\u001b[35;100m");
        System.out.print("    a  b  c  d  e  f  g  h    ");
        System.out.println("\033[0m");
        for (int i = 8; i > 0; --i) {
            System.out.print("\u001b[35;100m");
            System.out.print(" "+i+" ");
            for (int j = 1; j < 9; ++j) {
                System.out.print("\u001b[107m");
                PositionImpl position = new PositionImpl(i,j);
                if (board.containsKey(position)) {
                    if ((position.getColumn() + position.getRow())%2 == 0) {
                        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
                        toStringHelper(board.get(position).getPieceType(), board.get(position).getTeamColor());
                    } else {
                        System.out.print(EscapeSequences.SET_BG_COLOR_WHITE);
                        toStringHelper(board.get(position).getPieceType(), board.get(position).getTeamColor());
                    }
                } else {
                    if ((position.getColumn() + position.getRow())%2 == 0) {
                        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + EscapeSequences.SET_TEXT_COLOR_GREEN);
                        System.out.print("   ");
                    } else {
                        System.out.print(EscapeSequences.SET_BG_COLOR_WHITE + EscapeSequences.SET_TEXT_COLOR_GREEN);
                        System.out.print("   ");
                    }
                }
            }
            System.out.print("\u001b[35;100m");
            System.out.print(" "+i+" ");
            System.out.println("\033[0m");
        }
        System.out.print("\u001b[35;100m");
        System.out.print("    a  b  c  d  e  f  g  h    ");
        System.out.println("\033[0m");
    }
    public void makeReverseString(Map<PositionImpl,ChessPiece> board){
        System.out.print("\u001b[35;100m");
        System.out.print("    h  g  f  e  d  c  b  a    ");
        System.out.println("\033[0m");
        for (int i = 1; i < 9; ++i) {
            System.out.print("\u001b[35;100m");
            System.out.print(" "+(i)+" ");
            for (int j = 1; j < 9; ++j) {
                System.out.print("\u001b[107m");
                PositionImpl position = new PositionImpl(i,j);
                if (board.containsKey(position)) {
                    if ((position.getColumn() + position.getRow())%2 == 0) {
                        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK);
                        toStringHelper(board.get(position).getPieceType(), board.get(position).getTeamColor());
                    } else {
                        System.out.print(EscapeSequences.SET_BG_COLOR_WHITE);
                        toStringHelper(board.get(position).getPieceType(), board.get(position).getTeamColor());
                    }
                } else {
                    if ((position.getColumn() + position.getRow())%2 == 0) {
                        System.out.print(EscapeSequences.SET_BG_COLOR_BLACK + EscapeSequences.SET_TEXT_COLOR_GREEN);
                        System.out.print("   ");
                    } else {
                        System.out.print(EscapeSequences.SET_BG_COLOR_WHITE + EscapeSequences.SET_TEXT_COLOR_GREEN);
                        System.out.print("   ");
                    }
                }
            }
            System.out.print("\u001b[35;100m");
            System.out.print(" "+(i)+" ");
            System.out.println("\033[0m");
        }
        System.out.print("\u001b[35;100m");
        System.out.print("    h  g  f  e  d  c  b  a    ");
        System.out.println("\033[0m");
    }

    private void toStringHelper(ChessPiece.PieceType type, ChessGame.TeamColor color) {
        if (type == ChessPiece.PieceType.KING) {
            if (color == ChessGame.TeamColor.WHITE) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN);
                System.out.print(" K ");
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                System.out.print(" K ");
            }
        }
        if (type == ChessPiece.PieceType.QUEEN) {
            if (color == ChessGame.TeamColor.WHITE) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN);
                System.out.print(" Q ");
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                System.out.print(" Q ");
            }
        }
        if (type == ChessPiece.PieceType.BISHOP) {
            if (color == ChessGame.TeamColor.WHITE) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN);
                System.out.print(" B ");
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                System.out.print(" B ");
            }
        }
        if (type == ChessPiece.PieceType.KNIGHT) {
            if (color == ChessGame.TeamColor.WHITE) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN);
                System.out.print(" N ");
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                System.out.print(" N ");
            }
        }
        if (type == ChessPiece.PieceType.ROOK) {
            if (color == ChessGame.TeamColor.WHITE) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN);
                System.out.print(" R ");
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                System.out.print(" R ");
            }
        }
        if (type == ChessPiece.PieceType.PAWN) {
            if (color == ChessGame.TeamColor.WHITE) {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN);
                System.out.print(" P ");
            } else {
                System.out.print(EscapeSequences.SET_TEXT_COLOR_RED);
                System.out.print(" P ");
            }
        }
    }

}
