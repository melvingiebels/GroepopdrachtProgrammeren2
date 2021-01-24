package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import Domain.Webcast;
import GUI.Course.OverviewCourse;
import GUI.Student.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainMenu extends GenericGUI {

    public Scene getScene(Stage mainStage) {

        mainStage.setTitle("Qiang Loozen(2168151), Melvin Giebels(2173543), Pieter Bakker(2160778)");

        // Create views
        OverviewStudent studentScene = new OverviewStudent();
        OverviewCourse courseScene = new OverviewCourse();

        BorderPane layout = new BorderPane();
        // Create menu for main layout
        VBox menu = new VBox();
        menu.setPadding(new Insets(100, 0, 0, 225));
        menu.setSpacing(20);

        // Create buttons for menu
        Button studentsBtn = new Button("Students");
        studentsBtn.setMinSize(200, 50);

        Button coursesBtn = new Button("Courses");
        coursesBtn.setMinSize(200, 50);

        Button top3Btn = new Button("Top 3 Webcasts");
        top3Btn.setMinSize(200, 50);

        // Add button actions
        studentsBtn.setOnAction((event) -> {
            mainStage.setScene(studentScene.getScene(mainStage));
        });

        coursesBtn.setOnAction((Event) -> {
            mainStage.setScene(courseScene.getScene(mainStage));
        });
        top3Btn.setOnAction((event) -> {
            this.getTop3Modal();
        });

        // Add buttons to menu
        menu.getChildren().addAll(studentsBtn, coursesBtn, top3Btn);

        layout.setPrefSize(650, 400);
        layout.setCenter(menu);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    public void getTop3Modal() {
        ArrayList<HashMap<Webcast, Integer>> webcasts = contentItemDAO.getTop3ViewedWebcasts();
        // Modal elements
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Top 3 viewed webcasts");

        VBox list = new VBox();
        HBox header = new HBox();
        Label rankLabel = new Label("Rank");
        rankLabel.setMinWidth(50);
        rankLabel.setMaxWidth(50);
        Label titleLabel = new Label("Title");
        titleLabel.setMinWidth(300);
        titleLabel.setMaxWidth(300);
        Label viewsLabel = new Label("Views");
        viewsLabel.setMinWidth(50);
        viewsLabel.setMaxWidth(50);
        header.getChildren().addAll(rankLabel, titleLabel, viewsLabel);
        header.setSpacing(20);
        header.setStyle("-fx-font-weight: bold");
        list.getChildren().add(header);

        for (int i = 0; i < webcasts.size(); i++) {
            HashMap<Webcast, Integer> map = webcasts.get(i);
            for (Webcast webcast : map.keySet()) {
                HBox row = new HBox();
                Label rank = new Label(String.valueOf(i + 1));
                rank.setMinWidth(50);
                rank.setMaxWidth(50);
                Label title = new Label(webcast.getTitle());
                title.setMinWidth(300);
                title.setMaxWidth(300);
                Label views = new Label(String.valueOf(map.get(webcast)));
                views.setMinWidth(50);
                views.setMaxWidth(50);
                row.getChildren().addAll(rank, title, views);
                row.setSpacing(20);
                list.getChildren().add(row);
            }
        }
        list.setPadding(new Insets(10, 10, 10, 10));
        // Adding table to the scene
        Scene scene = new Scene(list);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }
}