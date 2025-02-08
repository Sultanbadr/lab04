import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class gitapp extends Application {
    @Override
    public void start(Stage primaryStage) {
        TextField commandInput = new TextField();
        Button searchButton = new Button("Search Command");
        Label resultLabel = new Label();
        Button backButton = new Button("Back");

        VBox mainLayout = new VBox(15, commandInput, searchButton, resultLabel, backButton);
        mainLayout.setSpacing(10);

        Scene mainScene = new Scene(mainLayout, 400, 300);

        searchButton.setOnAction(e -> {
            String commandID = commandInput.getText();
            String commandDetails = getCommandDetails(commandID);
            resultLabel.setText(commandDetails);
        });

        backButton.setOnAction(e -> {
            commandInput.clear();
            resultLabel.setText("");
        });

        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Git Command Search");
        primaryStage.show();
    }

    private String getCommandDetails(String id) {
        if (!id.matches("\\d+")) {  // Ensure input is only numbers
            return "Invalid input! Enter a number.";
        }

        String query = "SELECT command, description FROM git_commands WHERE id = ?";
        try (Connection conn = DBUtils.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("command") + " - " +
                        (rs.getString("description") == null ? "(No description available)" : rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Command not found!";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
