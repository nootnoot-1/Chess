// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import chess.*;


public class Main {
    public static void main(String[] args) {

        System.out.println("SRC MAIN FILE?????");

    }

    public static GameImpl deserialize(String gamestring) {
        GameImpl game = new GameImpl();
        BoardImpl board = new BoardImpl();

        if (gamestring.charAt(0) == 'W') {
            game.setTeamTurn(ChessGame.TeamColor.WHITE);
        } else {game.setTeamTurn(ChessGame.TeamColor.BLACK);}

        for (int i=1; i<gamestring.length(); ++i) {
            PositionImpl position = new PositionImpl();
            position.setRow(gamestring.charAt(i)-48); ++i;
            position.setColumn(gamestring.charAt(i)-48); ++i;
            ChessPiece piece = null;
            if (gamestring.charAt(i) == 'K') {
                piece = new KingImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'Q') {
                piece = new QueenImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'B') {
                piece = new BishopImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'N') {
                piece = new KnightImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'R') {
                piece = new RookImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'P') {
                piece = new PawnImpl(ChessGame.TeamColor.WHITE);
            } else if (gamestring.charAt(i) == 'k') {
                piece = new PawnImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'q') {
                piece = new QueenImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'b') {
                piece = new BishopImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'n') {
                piece = new KnightImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'r') {
                piece = new RookImpl(ChessGame.TeamColor.BLACK);
            } else if (gamestring.charAt(i) == 'p') {
                piece = new PawnImpl(ChessGame.TeamColor.BLACK);
            }
            board.addPiece(position,piece);
        }
        game.setBoard(board);
        return game;
    }
}