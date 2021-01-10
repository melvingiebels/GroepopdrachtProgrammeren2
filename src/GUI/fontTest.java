package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.stream.Collectors;

public class fontTest extends Application {
    private final static int COLUMNS = 1;
    private final static double FONT_HEIGHT = 30;

    @Override
    public void start(final Stage stage) {
        initUI(stage);
    }

    private void initUI(final Stage stage) {
        final GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        // Get a list of all of the font families and create a label for each.
        // Some of the fonts are badly behaved.
        final var fonts = javafx.scene.text.Font.getFamilies();
        fonts.sort(Comparator.naturalOrder());
        final var upperLabels = fonts.stream().map(ff ->
            new Label(ff.toUpperCase()) {{
                setFont(Font.font(ff, FontWeight.NORMAL, FONT_HEIGHT));
                setPadding(new Insets(5));
            }}).collect(Collectors.toList());
        final var lowerLabels = fonts.stream().map(ff ->
                new Label(ff.toLowerCase()) {{
                    setFont(Font.font(ff, FontWeight.NORMAL, FONT_HEIGHT));
                    setPadding(new Insets(5));
                }}).collect(Collectors.toList());

        //  On my local Java 10 installation, there are 243 = 9 * 27 fonts.
        final var rows = upperLabels.size() / COLUMNS + (upperLabels.size() % COLUMNS != 0 ? 1 : 0);

        // Add the labels from top to bottom, left to right.
        for (var col = 0; col < COLUMNS; ++col)
            for (var row = 0; row < rows; ++row) {
                final var idx = col * rows + row;
                if (idx >= upperLabels.size())
                    break;
                grid.add(upperLabels.get(idx), 2 * col, row);
                grid.add(lowerLabels.get(idx), 2 * col + 1, row);
            }

        final ScrollPane sp = new ScrollPane(grid);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        upperLabels.forEach(l -> {
            l.setMinHeight(50);
            l.setMinHeight(l.getPrefHeight());
        });
        lowerLabels.forEach(l -> {
            l.setMinHeight(50);
            l.setMinHeight(l.getPrefHeight());
        });
        final Scene scene = new Scene(sp, 1000, Screen.getPrimary().getBounds().getHeight() - 100);
        stage.setScene(scene);
        stage.setTitle("FontFamilies");
        stage.show();
    }

    public static void main(String[] args) {
        launch(fontTest.class);
    }    
}
