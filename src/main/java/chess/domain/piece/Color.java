package chess.domain.piece;

public enum Color {
    BLACK, WHITE;

    public Color reversed() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
