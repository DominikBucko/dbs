package backend;

import javax.swing.undo.AbstractUndoableEdit;
import java.sql.Connection;
import java.util.Scanner;

public class Main_Backend {
    public static void main(String[] args){
        Connection conn = Mapping.getConnection("postgres", "BoboJeSmutny");
        Scanner scan = new Scanner(System.in);
        Mapping.addNew(conn, scan);
        }
}
