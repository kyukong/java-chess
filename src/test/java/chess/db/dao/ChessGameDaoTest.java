package chess.db.dao;

import chess.domain.ChessGame;
import chess.db.entity.ChessGameEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ChessGameDaoTest {

    private final DBConnector dbConnector = new DBConnector();

    @Test
    @DisplayName("게임 개수 호출")
    void count() {
        ChessGameDao chessGameDao = new ChessGameDao();

        assertThatCode(chessGameDao::count)
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("게임 시작")
    void saveWithRollback() throws SQLException {
        ChessGameDao chessGameDao = new ChessGameDao();
        Connection connectionRollback = dbConnector.getConnection();

        connectionRollback.setAutoCommit(false);
        int chessGameId = chessGameDao.save(new ChessGame());
        connectionRollback.rollback();
    }

    @Test
    @DisplayName("게임 호출")
    void findWithRollback() throws SQLException {
        ChessGameDao chessGameDao = new ChessGameDao();
        Connection connectionRollback = dbConnector.getConnection();

        connectionRollback.setAutoCommit(false);
        int chessGameId = chessGameDao.save(new ChessGame());

        ChessGameEntity chessGameEntity = chessGameDao.find(1);
        assertThat(chessGameEntity.getId()).isEqualTo(1);

        connectionRollback.rollback();
    }

    @Test
    @DisplayName("피스 이동")
    void moveWithRollback() throws SQLException {
        ChessGameDao chessGameDao = new ChessGameDao();
        Connection connectionRollback = dbConnector.getConnection();

        connectionRollback.setAutoCommit(false);
        int chessGameId = chessGameDao.save(new ChessGame());

        assertThatCode(() -> chessGameDao.move(chessGameId, "nextState"))
                .doesNotThrowAnyException();

        connectionRollback.rollback();
    }
}
