package main;

import javax.validation.constraints.Null;
import java.sql.*;

/**
 * Created by Akmaral on 22.01.2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public  class App extends main{

    //private static final String CREATE_QUERY = "CREATE TABLE EXAMPLE (RESULTS CLOB), TARGET NUMBER (24))";

    public App() {
    }


//    //  NO
//    public void setResult(String dates, Integer counts) throws SQLException {
//        Connection db = DriverManager.getConnection("jdbc:h2:mem:");
//           Statement  dataQuery = db.createStatement();
//       //     try (Statement dataQuery = db.createStatement()) {
//        //        dataQuery.execute("CREATE TABLE EXAMPLE (GREETING VARCHAR(24), TARGET NUMBER (24))");
//               dataQuery.execute("INSERT INTO EXAMPLE VALUES('1', '2' )");
//       //     }
//
//    }


   static void setResult(String s){
       try (Connection db = DriverManager.getConnection("jdbc:h2:mem:")) {
           try (Statement dataQuery = db.createStatement()) {
               //   dataQuery.execute("INSERT INTO EXAMPLE VALUES(CURRENT_TIMESTAMP(), "+counts+")");
               dataQuery.execute("INSERT INTO EXAMPLE VALUES(" + s + ")");
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }catch (SQLException ex) {
                System.out.println("Database select failure: "
                        + ex.getMessage());
            }
    }


//    static void createBase(Connection db){
//            try (Statement dataQuery = db.createStatement()) {
//                dataQuery.execute(CREATE_QUERY);
//                // dataQuery.execute("INSERT INTO EXAMPLE VALUES(CURRENT_TIMESTAMP(), "+counts+")");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//    }
    public static String getResults(){
        String s="pp";
        try (Connection db = DriverManager.getConnection("jdbc:h2:mem:")) {

            try (PreparedStatement query =
                         db.prepareStatement("SELECT * FROM EXAMPLE")) {
                ResultSet result = query.executeQuery();

                while (result.next()) {
                   s = s +
                            result.getString(1)+
                            result.getString("TARGET")+"\n";
                }
                result.close();
            }

        } catch (SQLException ex) {
            System.out.println("Database select failure: "
                    + ex.getMessage());
        }
        return s;
    }

}
