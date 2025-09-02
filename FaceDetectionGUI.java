package frontend_java;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

	public class FaceDetectionGUI extends JFrame {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JLabel imageLabel;
	    private JFileChooser fileChooser;

	    public FaceDetectionGUI() {
	        // Set up the main window
	        setTitle("Face Detection App");
	        setSize(800, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        
	        // Use a BorderLayout for the main frame
	        setLayout(new BorderLayout());

	        // Create a panel for controls (like the button)
	        JPanel controlPanel = new JPanel();
	        JPanel controlpanel2 = new JPanel();
	        JButton selectImageButton = new JButton("Select Image");
	        JButton exit = new JButton("exit");
	        selectImageButton.setFont(new Font("Arial", Font.BOLD, 16));
	        exit.setFont(new Font("Arial", Font.BOLD, 16));
	        controlPanel.add(selectImageButton);
	        controlPanel.add(exit);
	        add(controlpanel2,BorderLayout.SOUTH);
	        add(controlPanel, BorderLayout.NORTH);

	        // Create a label to display the image
	        imageLabel = new JLabel("Please select an image to detect faces.", SwingConstants.CENTER);
	        imageLabel.setFont(new Font("Arial", Font.ITALIC, 14));
	        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	        imageLabel.setPreferredSize(new Dimension(750, 500));
	        add(new JScrollPane(imageLabel), BorderLayout.CENTER);

	        // Set up the file chooser
	        fileChooser = new JFileChooser();

	        // Add action listener to the button
	        selectImageButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int returnValue = fileChooser.showOpenDialog(null);
	                if (returnValue == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    processImageWithPython(selectedFile.getAbsolutePath());
	                }
	            }
	        });
	    }

	    private void processImageWithPython(String imagePath) {
	        try {
	            // Path to your Python executable and script
	            String pythonExecutable = "python"; // or "python3"
	            String scriptPath = "path/to/your/backend_script.py"; // **Update this path**

	            // Build the command
	            ProcessBuilder pb = new ProcessBuilder(pythonExecutable, scriptPath, imagePath);
	            Process process = pb.start();

	            // Read output from the Python script
	            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String line;
	            StringBuilder output = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                output.append(line);
	            }

	            int exitCode = process.waitFor();
	            if (exitCode == 0) {
	                // The Python script should print the path to the output image
	                String processedImagePath = output.toString().trim();
	                displayImage(processedImagePath);
	            } else {
	                imageLabel.setText("Error: Python script failed. Check your script and paths.");
	            }
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	            imageLabel.setText("Error during script execution: " + e.getMessage());
	        }
	    }

	    private void displayImage(String imagePath) {
	        ImageIcon icon = new ImageIcon(imagePath);
	        if (icon.getIconWidth() == -1) {
	            imageLabel.setText("Could not load processed image. Check the path: " + imagePath);
	        } else {
	            // Scale the image to fit the label
	            Image img = icon.getImage();
	            Image scaledImg = img.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
	            imageLabel.setIcon(new ImageIcon(scaledImg));
	            imageLabel.setText(""); // Remove the placeholder text
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                new FaceDetectionGUI().setVisible(true);
	            }
	        });
	    }
	}


