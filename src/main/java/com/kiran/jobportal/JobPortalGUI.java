package com.kiran.jobportal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JobPortalGUI extends Application {

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new VBox(), 800, 600);

        VBox registrationLayout = new VBox();
        VBox loginLayout = new VBox();
        VBox dashboardLayout = new VBox(20);

        registrationLayout.setAlignment(Pos.CENTER);
        loginLayout.setAlignment(Pos.CENTER);
        dashboardLayout.setAlignment(Pos.CENTER);

        registrationLayout.setStyle("-fx-background-color:#f4f6f8;");
        loginLayout.setStyle("-fx-background-color:#f4f6f8;");
        dashboardLayout.setStyle("-fx-background-color:#f4f6f8;");

        /* ---------------- REGISTER ---------------- */

        VBox regForm = new VBox(10);
        regForm.setMaxWidth(320);
        regForm.setPadding(new Insets(25));
        regForm.setAlignment(Pos.CENTER_LEFT);
        regForm.setStyle("-fx-background-color:white; -fx-background-radius:10;");

        Label regTitle = new Label("User Registration");
        regTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();

        Label passLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();

        Label roleLabel = new Label("Role");
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("JOB_SEEKER","RECRUITER");

        Button registerBtn = new Button("Register");
        Button goLoginBtn = new Button("Already Registered? Login");

        registerBtn.setOnAction(e -> {

            try {

                URL url = new URL("http://localhost:8080/users/register");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json");
                conn.setDoOutput(true);

                String json = "{"
                        + "\"name\":\""+nameField.getText()+"\","
                        + "\"email\":\""+emailField.getText()+"\","
                        + "\"password\":\""+passwordField.getText()+"\","
                        + "\"role\":\""+roleBox.getValue()+"\""
                        + "}";

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                os.close();

                BufferedReader br =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String response = br.readLine();
                br.close();

                new Alert(Alert.AlertType.INFORMATION,response).showAndWait();

                nameField.clear();
                emailField.clear();
                passwordField.clear();
                roleBox.setValue(null);

            } catch (Exception ex) {

                new Alert(Alert.AlertType.ERROR,
                        "Registration failed").showAndWait();
            }

        });

        goLoginBtn.setOnAction(e -> scene.setRoot(loginLayout));

        regForm.getChildren().addAll(
                regTitle,
                nameLabel,nameField,
                emailLabel,emailField,
                passLabel,passwordField,
                roleLabel,roleBox,
                registerBtn,
                goLoginBtn
        );

        registrationLayout.getChildren().add(regForm);

        /* ---------------- LOGIN ---------------- */

        VBox loginForm = new VBox(10);
        loginForm.setMaxWidth(320);
        loginForm.setPadding(new Insets(25));
        loginForm.setAlignment(Pos.CENTER_LEFT);
        loginForm.setStyle("-fx-background-color:white; -fx-background-radius:10;");

        Label loginTitle = new Label("User Login");
        loginTitle.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        Label loginEmailLabel = new Label("Email");
        TextField loginEmailField = new TextField();

        Label loginPassLabel = new Label("Password");
        PasswordField loginPassField = new PasswordField();

        Button loginBtn = new Button("Login");
        Button goRegisterBtn = new Button("New User? Register");

        loginBtn.setOnAction(e -> {

            try {

                URL url = new URL("http://localhost:8080/users/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json");
                conn.setDoOutput(true);

                String json = "{"
                        + "\"email\":\""+loginEmailField.getText()+"\","
                        + "\"password\":\""+loginPassField.getText()+"\""
                        + "}";

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();
                os.close();

                BufferedReader br =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String response = br.readLine();
                br.close();

                if(response != null && !response.equals("null")){

                    scene.setRoot(dashboardLayout);

                }else{

                    new Alert(Alert.AlertType.ERROR,
                            "Invalid email or password").showAndWait();
                }

            } catch (Exception ex) {

                new Alert(Alert.AlertType.ERROR,
                        "Cannot connect to server").showAndWait();
            }

        });

        goRegisterBtn.setOnAction(e -> scene.setRoot(registrationLayout));

        loginForm.getChildren().addAll(
                loginTitle,
                loginEmailLabel,loginEmailField,
                loginPassLabel,loginPassField,
                loginBtn,
                goRegisterBtn
        );

        loginLayout.getChildren().add(loginForm);

        /* ---------------- DASHBOARD ---------------- */

        Label dashTitle = new Label("Job Portal Dashboard");
        dashTitle.setStyle("-fx-font-size:24px; -fx-font-weight:bold;");

        Button postJobBtn = new Button("Post Job");
        Button logoutBtn = new Button("Logout");

        postJobBtn.setPrefWidth(200);
        logoutBtn.setPrefWidth(200);

        /* ---------------- POST JOB ---------------- */

        postJobBtn.setOnAction(e -> {

            Stage postStage = new Stage();

            VBox layout = new VBox(10);
            layout.setPadding(new Insets(20));
            layout.setAlignment(Pos.CENTER_LEFT);

            Label titleLabel = new Label("Job Title");
            TextField titleField = new TextField();

            Label companyLabel = new Label("Company");
            TextField companyField = new TextField();

            Label locationLabel = new Label("Location");
            TextField locationField = new TextField();

            Label descLabel = new Label("Description");
            TextArea descField = new TextArea();

            Button submitBtn = new Button("Post Job");

            submitBtn.setOnAction(ev -> {

                try {

                    URL url = new URL("http://localhost:8080/jobs/post");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type","application/json");
                    conn.setDoOutput(true);

                    String json = "{"
                            + "\"title\":\""+titleField.getText()+"\","
                            + "\"company\":\""+companyField.getText()+"\","
                            + "\"location\":\""+locationField.getText()+"\","
                            + "\"description\":\""+descField.getText()+"\""
                            + "}";

                    OutputStream os = conn.getOutputStream();
                    os.write(json.getBytes());
                    os.flush();
                    os.close();

                    new Alert(Alert.AlertType.INFORMATION,
                            "Job Posted Successfully").showAndWait();

                    postStage.close();

                } catch (Exception ex) {

                    new Alert(Alert.AlertType.ERROR,
                            "Failed to post job").showAndWait();
                }

            });

            layout.getChildren().addAll(
                    new Label("Post Job"),
                    titleLabel,titleField,
                    companyLabel,companyField,
                    locationLabel,locationField,
                    descLabel,descField,
                    submitBtn
            );

            Scene postScene = new Scene(layout,400,400);

            postStage.setTitle("Post Job");
            postStage.setScene(postScene);
            postStage.show();
        });

        logoutBtn.setOnAction(e -> {

            loginEmailField.clear();
            loginPassField.clear();

            scene.setRoot(loginLayout);
        });

        dashboardLayout.getChildren().addAll(
                dashTitle,
                postJobBtn,
                logoutBtn
        );

        /* ---------------- START APP ---------------- */

        scene.setRoot(loginLayout);

        stage.setTitle("Job Portal System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
