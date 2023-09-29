package chess;

public class BoardImpl implements ChessBoard{

    private ChessPiece[] board = new ChessPiece[63];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        PositionImpl position1 = (PositionImpl) position;
        int index = position1.getIndex();
        board[index] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        PositionImpl position1 = (PositionImpl) position;
        return board[position1.getIndex()];
    }

    @Override
    public void resetBoard() {
        board[0] = new RookImpl(ChessGame.TeamColor.WHITE, 1, 1);
        board[1] = new KnightImpl(ChessGame.TeamColor.WHITE, 1, 2);
        board[2] = new BishopImpl(ChessGame.TeamColor.WHITE, 1, 3);
        board[3] = new QueenImpl(ChessGame.TeamColor.WHITE, 1, 4);
        board[4] = new KingImpl(ChessGame.TeamColor.WHITE, 1, 5);
        board[5] = new BishopImpl(ChessGame.TeamColor.WHITE, 1, 6);
        board[6] = new KnightImpl(ChessGame.TeamColor.WHITE, 1, 7);
        board[7] = new RookImpl(ChessGame.TeamColor.WHITE, 1, 8);
        board[8] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 1);
        board[9] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 2);
        board[10] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 3);
        board[11] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 4);
        board[12] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 5);
        board[13] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 6);
        board[14] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 7);
        board[15] = new PawnImpl(ChessGame.TeamColor.WHITE, 2, 8);

        board[56] = new RookImpl(ChessGame.TeamColor.BLACK, 8, 1);
        board[57] = new KnightImpl(ChessGame.TeamColor.BLACK, 8, 2);
        board[58] = new BishopImpl(ChessGame.TeamColor.BLACK, 8, 3);
        board[59] = new QueenImpl(ChessGame.TeamColor.BLACK, 8, 4);
        board[60] = new KingImpl(ChessGame.TeamColor.BLACK, 8, 5);
        board[61] = new BishopImpl(ChessGame.TeamColor.BLACK, 8, 6);
        board[62] = new KnightImpl(ChessGame.TeamColor.BLACK, 8, 7);
        board[63] = new RookImpl(ChessGame.TeamColor.BLACK, 8, 8);
        board[48] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 1);
        board[49] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 2);
        board[50] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 3);
        board[51] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 4);
        board[52] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 5);
        board[53] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 6);
        board[54] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 7);
        board[55] = new PawnImpl(ChessGame.TeamColor.BLACK, 7, 8);
    }
}
