package com.github.enesusta.belge.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DefaultBelgeRepository implements BelgeRepository {

    private final DataSource dataSource;

    @Override
    public final byte[] getSource(final int owner,
                                  final String fileName) {

        byte[] source = null;

        try (Connection connection =
                     dataSource.getConnection()) {

            final String query = "select pdf_source from pdf where pdf_id = 1";
            final int insensitive = ResultSet.TYPE_SCROLL_INSENSITIVE;
            final int readOnly = ResultSet.CONCUR_READ_ONLY;
            final int closeCursorsAtCommit = ResultSet.CLOSE_CURSORS_AT_COMMIT;

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query, insensitive, readOnly, closeCursorsAtCommit)) {

                final ResultSet rs = preparedStatement.executeQuery();
                rs.setFetchSize(1); // it will return only 1 row

                while (rs.next()) source = rs.getBytes(1);

                rs.close();

            } catch (SQLException e) {
                log.error(e.getMessage());
            }

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return source;
    }

    @Override
    public void save(byte[] arr) {

        final String query = "insert into pdf(pdf_file_name, pdf_source) values (?,?)";
        final int insensitive = ResultSet.TYPE_SCROLL_INSENSITIVE;
        final int readOnly = ResultSet.CONCUR_READ_ONLY;
        final int closeCursorsAtCommit = ResultSet.CLOSE_CURSORS_AT_COMMIT;

        try (Connection connection =
                     dataSource.getConnection()) {

            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(query, insensitive, readOnly, closeCursorsAtCommit)) {

                preparedStatement.setString(1, "test");
                preparedStatement.setBytes(2, arr);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                log.error(e.getMessage());
                connection.rollback();
            }

            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }


    }

}
