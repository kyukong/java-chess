package chess.view;

import chess.domain.piece.Piece;
import chess.domain.position.CoordinateX;
import chess.domain.position.Position;
import chess.domain.position.CoordinateY;

import java.util.Map;

public class Output {

    private static final String EXCEPTION_PREFIX = "[ERROR] ";

    private Output() {
    }

    public static void printExceptionMessage(final String exceptionMessage) {
        System.out.println(EXCEPTION_PREFIX + exceptionMessage);
    }

    public static void printChessGameStart() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public static void printBoard(final Map<Position, Piece> board) {
        for (final CoordinateY coordinateY : CoordinateY.reversed()) {
            printBoard(coordinateY, board);
        }
    }

    public static void printScore(final String colorName, final double score) {
        System.out.printf("%s의 점수는 %.1f점 입니다.", colorName, score);
        System.out.println();
    }

    private static void printBoard(final CoordinateY coordinateY, final Map<Position, Piece> board) {
        for (final CoordinateX coordinateX : CoordinateX.sorted()) {
            printBoard(coordinateX, coordinateY, board);
        }
        System.out.println();
    }

    private static void printBoard(final CoordinateX coordinateX, final CoordinateY coordinateY, final Map<Position, Piece> board) {
        final Piece target = board.get(Position.of(coordinateX, coordinateY));
        if (target != null) {
            System.out.print(target.getName());
            return;
        }
        System.out.print(".");
    }
}
