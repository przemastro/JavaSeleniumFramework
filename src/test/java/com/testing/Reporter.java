/*
 * Methods
 */
package com.testing;

import com.testing.Pages.MainTest;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Reporter extends TestListenerAdapter {

    double time;
    public String dateValue;
    public String failureMessage;
    public String failedMethod;
    public String testName;
    public Connection connection;
    public PreparedStatement preparedStatement;
    public Statement stmt;
    ResultSet rs;
    String nameBuild;
    String fail;
    BigDecimal timeExecution;
    String testResult;
    ResultSet verifyBuildResult;
    ResultSet verifyTestResult;

    @Override
    public void onTestSuccess(ITestResult tr) {
        failedMethod = "No significant error has been found";
        failureMessage = "";
        time = (tr.getEndMillis() - tr.getStartMillis());
        testName = tr.getName();
        testResult = "p";
        log();

    }

    @Override
    public void onTestFailure(ITestResult tr) {
        if (tr.getTestContext().getAttribute("method") != null) {
            failedMethod = tr.getTestContext().getAttribute("method").toString();
        }
        System.out.println("failure " + failedMethod);
        time = (tr.getEndMillis() - tr.getStartMillis());
        testName = tr.getName();
        failureMessage = tr.getThrowable().toString();
        testResult = "f";
        log();
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        time = (tr.getEndMillis() - tr.getStartMillis());
        testName = tr.getName();
        testResult = "n";
        log();

    }

    public void log() {
        time = (time / 1000);
        timeExecution = new BigDecimal(time);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateValue = dateFormat.format(MainTest.date);
        nameBuild = dateValue;
        fail = failedMethod + "\n" + failureMessage;

        String url = "jdbc:mysql://localhost:3306/testlink";
        try {
            System.out.println("Connecting database...");
            connection = DriverManager.getConnection(url, "admin", "admin");
            System.out.println("Database connected!");

            buildVerificationSQL();
            if (verifyBuildResult.first() == true) { //Build isPresent
                testNameVerificationSQL();
                if (verifyTestResult.first() == false) { //Test Name isNotPresent
                    insertFirstRowToNodeHierarchySQL();
                    insertSecondRowToNodesHierarchySQL();
                    insertRowToTCVersionsSQL();
                }
                insertResultsToExecutionsSQL();
                makeTestActiveForCurrentBuild();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        } finally {
            System.out.println("Closing the connection.");
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

    public void buildVerificationSQL() throws SQLException {
        String verifybuild = "select name from testlink.builds where name = ?;";
        preparedStatement = connection.prepareStatement(verifybuild);
        preparedStatement.setString(1, "Build " + dateValue);
        verifyBuildResult = preparedStatement.executeQuery();
    }

    public void testNameVerificationSQL() throws SQLException {
        String verifyName = "select name from testlink.nodes_hierarchy where name = ?;";
        preparedStatement = connection.prepareStatement(verifyName);
        preparedStatement.setString(1, testName);
        verifyTestResult = preparedStatement.executeQuery();
    }

    public void insertFirstRowToNodeHierarchySQL() throws SQLException {
        stmt = connection.createStatement();
        String maxNodeOrderText = "select max(node_order) as node_order from testlink.nodes_hierarchy";
        rs = stmt.executeQuery(maxNodeOrderText);
        rs.first();
        int maxNodeOrder = rs.getInt("node_order") + 1;
        //System.out.println(maxNodeOrder);

        String insertResultToNodesHierarchy = "insert into testlink.nodes_hierarchy (name, parent_id, node_type_id, node_order)"
                + "values (?, 2, 3, ?);";
        preparedStatement = connection.prepareStatement(insertResultToNodesHierarchy);
        preparedStatement.setString(1, testName);
        preparedStatement.setInt(2, maxNodeOrder);
        preparedStatement.execute();
    }

    public void insertSecondRowToNodesHierarchySQL() throws SQLException {
        stmt = connection.createStatement();
        String maxIdText = "select max(id) as id from testlink.nodes_hierarchy";
        rs = stmt.executeQuery(maxIdText);
        rs.first();
        int maxId = rs.getInt("id");
        //System.out.println(maxId);

        String insertResultToNodesHierarchy2 = "insert into testlink.nodes_hierarchy (name, parent_id, node_type_id, node_order)"
                + "values ('', ?, 4, 0);";
        preparedStatement = connection.prepareStatement(insertResultToNodesHierarchy2);
        preparedStatement.setInt(1, maxId);
        preparedStatement.execute();
    }

    public void insertRowToTCVersionsSQL() throws SQLException {
        stmt = connection.createStatement();
        String maxTcExternalIdText = "select max(tc_external_id) as tc_external_id from testlink.tcversions;";
        rs = stmt.executeQuery(maxTcExternalIdText);
        rs.first();
        int maxTcExternalId = rs.getInt("tc_external_id") + 1;
        //System.out.println(maxTcExternalId);

        String insertTestCaseToTcversions = "insert into testlink.tcversions (id, tc_external_id, version, layout, status, "
                + "summary, preconditions, importance, author_id, creation_ts, updater_id, modification_ts, active, is_open, "
                + "execution_type, estimated_exec_duration)"
                + "values((select max(id) as id from testlink.nodes_hierarchy), ?, 1, 1, 1, '', '', 2, 1, sysdate(),"
                + "null, sysdate(), 1, 1, 2, null)";
        preparedStatement = connection.prepareStatement(insertTestCaseToTcversions);
        preparedStatement.setInt(1, maxTcExternalId);
        preparedStatement.execute();
    }

    public void insertResultsToExecutionsSQL() throws SQLException {
        String insertResultToExecutions = "insert into executions (build_id,tester_id,execution_ts,status,testplan_id,tcversion_id,tcversion_number,platform_id,execution_type,execution_duration,notes)"
                + "values ((select distinct b.id from builds b where b.name=?)"
                + ",1,sysdate(),?,"
                + "(select id from testplans),"
                + "(select tc.id from nodes_hierarchy nh join tcversions tc on nh.id=tc.id where nh.parent_id = (select id from nodes_hierarchy where name=?)),"
                + "1,0,1,?,?);";

        preparedStatement = connection.prepareStatement(insertResultToExecutions);
        preparedStatement.setString(1, "Build " + nameBuild);
        preparedStatement.setString(2, testResult);
        preparedStatement.setString(3, testName);
        preparedStatement.setBigDecimal(4, timeExecution);
        preparedStatement.setString(5, fail);
        preparedStatement.execute();
    }

    public void makeTestActiveForCurrentBuild() throws SQLException {
        String insertTCToTestplanTcversions = "INSERT INTO `testplan_tcversions` (testplan_id,tcversion_id,node_order,urgency,platform_id,author_id,creation_ts)"
                + "VALUES ((select id from testplans),"
                + "(select tc.id from nodes_hierarchy nh join tcversions tc on nh.id=tc.id where nh.parent_id = (select id from nodes_hierarchy where name=?)),"
                + "0,2,0,1,sysdate())";

        preparedStatement = connection.prepareStatement(insertTCToTestplanTcversions);
        preparedStatement.setString(1, testName);
        preparedStatement.execute();
        System.out.println("Result has been inserted to DB");
    }
}
