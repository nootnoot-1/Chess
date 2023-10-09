package chess;

import org.junit.jupiter.api.Assertions;

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


}
