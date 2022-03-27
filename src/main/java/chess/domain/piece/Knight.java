package chess.domain.piece;

import chess.domain.board.Board;
import chess.domain.position.Position;

public class Knight extends Piece {

    private static final String NAME = "n";
    private static final double SCORE = 0;

    public Knight(final Color color) {
        super(color, NAME, SCORE);
    }

    @Override
    public void checkPieceMoveRange(final Board board, final Position from, final Position to) {
        if (isKnightMoving(from, to)) {
            return;
        }
        throw new IllegalArgumentException("나이트는 두 칸 이동 후 90도 방향으로 한 칸 이동할 수 있습니다.");
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
