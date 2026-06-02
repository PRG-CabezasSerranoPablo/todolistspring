package es.progcipfpbatoi.todolistspring.model.daos;

import es.progcipfpbatoi.todolistspring.exceptions.NotFoundException;
import es.progcipfpbatoi.todolistspring.model.entities.Prioridad;
import es.progcipfpbatoi.todolistspring.model.entities.Tarea;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class SQLTareaDAO implements TareaDAO {

    @Value("${app.datasource.url}")
    private String url;

    @Value("${app.datasource.user}")
    private String user;

    @Value("${app.datasource.password}")
    private String password;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void add(Tarea tarea) throws SQLException {
        String sql = "INSERT INTO tareas(usuario, descripcion, fecha_vencimiento, prioridad, realizada, categoria_id) "
                + "VALUES (?, ?, ?, ?, ?, NULL)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tarea.getUsuario());
            statement.setString(2, tarea.getDescripcion());
            statement.setTimestamp(3, Timestamp.valueOf(tarea.getFechaVencimiento()));
            statement.setString(4, prioridadToSql(tarea.getPrioridad()));
            statement.setBoolean(5, tarea.isRealizada());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tarea.setCodigo(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public Tarea get(int codTarea) throws SQLException, NotFoundException {
        String sql = "SELECT t.*, c.nombre AS categoria_nombre "
                + "FROM tareas t LEFT JOIN categorias c ON t.categoria_id = c.id "
                + "WHERE t.codigo = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codTarea);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return toTarea(resultSet);
            }
        }

        throw new NotFoundException("La tasca amb codi " + codTarea + " no existeix");
    }

    @Override
    public ArrayList<Tarea> findAll() throws SQLException {
        String sql = "SELECT t.*, c.nombre AS categoria_nombre "
                + "FROM tareas t LEFT JOIN categorias c ON t.categoria_id = c.id "
                + "ORDER BY t.codigo";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            return toTareas(resultSet);
        }
    }

    @Override
    public ArrayList<Tarea> findAll(String usuario, LocalDate fecha, Boolean realizada) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT t.*, c.nombre AS categoria_nombre "
                + "FROM tareas t LEFT JOIN categorias c ON t.categoria_id = c.id WHERE 1 = 1");
        ArrayList<Object> parametros = new ArrayList<>();

        if (usuario != null && !usuario.isBlank()) {
            sql.append(" AND t.usuario = ?");
            parametros.add(usuario);
        }

        if (fecha != null) {
            sql.append(" AND DATE(t.fecha_vencimiento) = ?");
            parametros.add(Date.valueOf(fecha));
        }

        if (realizada != null) {
            sql.append(" AND t.realizada = ?");
            parametros.add(realizada);
        }

        sql.append(" ORDER BY t.codigo");

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                statement.setObject(i + 1, parametros.get(i));
            }

            ResultSet resultSet = statement.executeQuery();
            return toTareas(resultSet);
        }
    }

    @Override
    public Tarea delete(int codTarea) throws SQLException, NotFoundException {
        Tarea tarea = get(codTarea);
        String sql = "DELETE FROM tareas WHERE codigo = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codTarea);
            statement.executeUpdate();
        }

        return tarea;
    }

    private ArrayList<Tarea> toTareas(ResultSet resultSet) throws SQLException {
        ArrayList<Tarea> tareas = new ArrayList<>();

        while (resultSet.next()) {
            tareas.add(toTarea(resultSet));
        }

        return tareas;
    }

    private Tarea toTarea(ResultSet resultSet) throws SQLException {
        return new Tarea(
                resultSet.getInt("codigo"),
                resultSet.getString("descripcion"),
                resultSet.getString("usuario"),
                resultSet.getTimestamp("fecha_vencimiento").toLocalDateTime(),
                prioridadFromSql(resultSet.getString("prioridad")),
                resultSet.getString("categoria_nombre"),
                resultSet.getBoolean("realizada")
        );
    }

    private String prioridadToSql(Prioridad prioridad) {
        if (prioridad == Prioridad.MITJANA) {
            return "MEDIA";
        }

        if (prioridad == Prioridad.BAIXA) {
            return "BAJA";
        }

        return "ALTA";
    }

    private Prioridad prioridadFromSql(String prioridad) {
        if ("MEDIA".equals(prioridad)) {
            return Prioridad.MITJANA;
        }

        if ("BAJA".equals(prioridad)) {
            return Prioridad.BAIXA;
        }

        return Prioridad.ALTA;
    }
}
