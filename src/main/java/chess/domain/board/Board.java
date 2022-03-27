package chess.domain.board;

import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.position.File;
import chess.domain.position.Position;
import chess.domain.position.Rank;

import java.util.Collections;
import java.util.Map;

public class Board {

    private static final int NEXT = 1;
    private final Map<Position, Piece> value;

    public Board(final Initializable initializable) {
        value = initializable.init();
    }

    public boolean isInitialized(final Initializable initializable) {
        return value.equals(initializable.init());
    }

    public void move(final Position from, final Position to) {
        final Piece piece = getPiece(from);
        piece.checkPieceMoveRange(this, from, to);
        value.put(to, value.remove(from));
    }

    public boolean isMatchedColor(final Position target, final Color color) {
        Piece piece = getPiece(target);
        return piece.isSameColor(color);
    }

    public boolean hasPieceInFile(final Position from, final Position to) {
        int minRank = Math.min(from.getRank(), to.getRank());
        int maxRank = Math.max(from.getRank(), to.getRank());

        return value.keySet().stream()
                .filter(position -> position.getFile().equals(from.getFile()))
                .filter(position -> position.getRank() > minRank)
                .anyMatch(position -> position.getRank() < maxRank);
    }

    public boolean hasPieceInRank(final Position from, final Position to) {
        int minFile = File.min(from.getFile(), to.getFile());
        int maxFile = File.max(from.getFile(), to.getFile());

        return value.keySet().stream()
                .filter(position -> position.getRank() == from.getRank())
                .filter(position -> position.getFileOrder() > minFile)
                .anyMatch(position -> position.getFileOrder() < maxFile);
    }

    public void checkPieceInDiagonal(final Position from, final Position to) {
        checkRisingDiagonal(from, to);
        checkDescendingDiagonal(from, to);
    }

    public void checkHasPiece(final Position to) {
        if (hasPiece(to)) {
            throw new IllegalArgumentException("이동 경로에 기물이 존재합니다.");
        }
    }

    private void checkRisingDiagonal(final Position from, final Position to) {
        int minFile = File.min(from.getFile(), to.getFile());
        int maxFile = File.max(from.getFile(), to.getFile());
        int minRank = Math.min(from.getRank(), to.getRank());

        int rank = minRank + NEXT;
        for (int file = minFile + NEXT; file < maxFile; file++, rank++) {
            checkHasPiece(Position.of(File.from(file), Rank.from(rank)));
        }
    }

    private void checkDescendingDiagonal(final Position from, final Position to) {
        int nextRank = Math.min(from.getRank(), to.getRank()) + NEXT;
        int maxRank = Math.max(from.getRank(), to.getRank());
        int file = File.max(from.getFile(), to.getFile()) - NEXT;

        for (int rank = nextRank; rank < maxRank; rank++, file--) {
            checkHasPiece(Position.of(File.from(file), Rank.from(rank)));
        }
    }

    public boolean hasKing(final Color color) {
        return value.values().stream()
                .filter(piece -> piece.isSameColor(color))
                .anyMatch(Piece::isKing);
    }

    public boolean hasPiece(final Position position) {
        return value.get(position) != null;
    }

    public Piece getPiece(final Position position) {
        final Piece piece = value.get(position);
        if (piece != null) {
            return piece;
        }
        throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
    }

    public Map<Position, Piece> toMap() {
        return Collections.unmodifiableMap(value);
    }
}
