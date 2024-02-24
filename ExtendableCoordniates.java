/**
    Allows you to get the coorninates of the mouse cursor within a
    JFrame when clicked on it. The coordinates are then shown on the title
    of the application window.

	@author Enrique Gabriel Badiola
	@since 23 February 2024

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.
**/

import java.io.*;
import java.net.URI;
import java.net.http.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JFrame;

/**
 * This Java class extends JFrame and allows for displaying the coordinates of mouse clicks in the
 * JFrame title.
 */
public class ExtendableCoordniates extends JFrame {
    
    private JFrame f;

    /**
     * The `public ExtendableCoordniates(JFrame f)` constructor in the `ExtendableCoordniates` class is
     * initializing an instance variable `f` with the value passed as a parameter to the constructor.
     * This allows the class to work with the `JFrame` object that is passed to it, enabling the
     * functionality of displaying the coordinates of mouse clicks in the JFrame title.
     * @param f a reference to the JFrame that the program will get the coordinates from
     */
    public ExtendableCoordniates(JFrame f) {
        this.f = f;
        checkForupdates();
    }

    private void checkForupdates() {
        String githubRawURL = "https://raw.githubusercontent.com/skyant83/testing/main/ExtendableCoordniates.java";
        String localFilePath = "ExtendableCoordniates.java";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(githubRawURL))
            .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(githubContent -> {
                try {
                    String localContent = readFile(localFilePath);
                    if (!githubContent.equals(localContent)) {
                        
                        System.out.println("Update Found. Downloading...");
                        writeFile(localFilePath, githubContent);
                        System.out.println("File updated successfully.");
                    } else {
                        System.out.println("File is already up to date.");
                    }
                } catch (IOException e) {
                    System.err.println("Error updating file: " + e.getMessage());
                }
            })
            .join();
    }

    private void writeFile(String filePath, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        }
    }
    
    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    /**
     * The `coordsToTitle` function sets the title of a frame to display the x and y coordinates of the
     * mouse click event adjusted by -7 and -30 respectively.
     */
    public void coordsToTitle() {
        f.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                f.setTitle(String.format("x: %s, y: %s", x-7, y-30));
            }
        });
    }
}
