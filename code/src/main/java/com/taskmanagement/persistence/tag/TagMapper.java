package com.taskmanagement.persistence.tag;

import com.taskmanagement.domain.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps Tag domain objects to/from database rows
 */
public class TagMapper {

    /**
     * Maps a database ResultSet row to a Tag object
     * @param rs the ResultSet containing a tag row
     * @return a Tag object populated with data from the ResultSet
     * @throws SQLException if database access fails
     */
    public Tag mapRowToTag(ResultSet rs) throws SQLException {
        Tag tag = new Tag();
        tag.setName(rs.getString("name"));
        return tag;
    }

    /**
     * Maps a Tag object to database column values
     * @param tag the Tag to map
     * @return a map of column names to values
     */
    public Map<String, Object> mapTagToValues(Tag tag) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", tag.getName());
        return values;
    }

    /**
     * Extracts column values from a Tag for an INSERT statement
     * @param tag the Tag to extract values from
     * @return array of values: [name]
     */
    public Object[] getInsertValues(Tag tag) {
        return new Object[]{
            tag.getName()
        };
    }

    /**
     * Extracts column values from a Tag for an UPDATE statement
     * @param tag the Tag to extract values from
     * @return array of values: [name, id]
     */
    public Object[] getUpdateValues(Tag tag) {
        return new Object[]{
            tag.getName()
        };
    }
}
