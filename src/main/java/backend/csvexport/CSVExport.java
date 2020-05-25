package backend.csvexport;

import backend.service.ConnectionService;
import com.opencsv.CSVWriter;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVExport {
    private static String exportToCsv(String column) {
        Connection conn = ConnectionService.getConnectionService().getConnection();
        String filename = "/tmp/" + new Date().getTime() + "_" + column + ".csv";
        File file = new File(filename);
        ResultSet rs;
        try {
            PreparedStatement sql = conn.prepareStatement("SELECT * FROM "+ column);
            rs = sql.executeQuery();
            FileWriter out = new FileWriter(file);
            CSVWriter writer = new CSVWriter(out);
            List<String> header = new ArrayList<>();
            int colCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= colCount; i++) {
                header.add(rs.getMetaData().getColumnName(i));
            }
            writer.writeNext(header.toArray(new String[0]));
            while (rs.next()) {
                List<String> line = new ArrayList<>();
                for (String col : header) {
                    line.add(rs.getString(col));
                }
                writer.writeNext(line.toArray(new String[0]));
            }
            writer.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    /**
     * Returns input stream of a CSV file - export of a given table.
     * @param table - name of a table to export
     * @return InputStream of file where data is exported.
     */
    public static InputStream createResource(String table) {
        String filename = exportToCsv(table);
        try {
            File fileToDl = new File(filename);
            return new FileInputStream(fileToDl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
