import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CartesianGraph extends JFrame {
    private JTextField x1Field, y1Field, x2Field, y2Field;// These are variables that stores the coordinates of the two points (x1,y1) & (x2,y2)
    private JButton drawButton; // This is a variable that stores a button object which when clicked will draw a line on the graph
    private JPanel drawingPanel; // This defines the section of our window that will be used for drawing lines and for the display of the Cartesian Graph.

    public CartesianGraph() {
        setTitle("Cartesian Graph"); // Gives a name to the window of our program
        setSize(500, 500); // sets the height and width of our window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // This command specifies that if we close the window, the program will terminate automatically

        // Create input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 4)); // Creates an invisible grid format on the window which has 2 rows and 4 coloumns
        
        inputPanel.add(new JLabel("x1:")); // This adds a label that says "x1: "
        x1Field = new JTextField(5); // This creates and input space where the user can enter the x1 value
        inputPanel.add(x1Field); // The above x1Field variable is added on to the window panel

        inputPanel.add(new JLabel("y1:"));// This adds a label that says "y1: "
        y1Field = new JTextField(5);// This creates and input space where the user can enter the y1 value
        inputPanel.add(y1Field);// The above y1Field variable is added on to the window panel

        inputPanel.add(new JLabel("x2:"));// This adds a label that says "x2: "
        x2Field = new JTextField(5);// This creates and input space where the user can enter the x2 value
        inputPanel.add(x2Field);// The above x2Field variable is added on to the window panel

        inputPanel.add(new JLabel("y2:"));// This adds a label that says "y2: "
        y2Field = new JTextField(5);// This creates and input space where the user can enter the y2 value
        inputPanel.add(y2Field);// The above y2Field variable is added on to the window panel

        // Sets the default values for x1, y1, x2, y2 as 0
        x1Field.setText("0");
        y1Field.setText("0");
        x2Field.setText("0");
        y2Field.setText("0");

        // Create drawing panel which consists of the Cartesian Graph and the line that is drawon on to it 
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawCartesianGraph(g); // First draws the cartesian graph
                drawLine(g); // then draws the line
            }
        };

        // Create draw button that when clicked will draw a line on to the graph
        drawButton = new JButton("Draw Line"); // The label of the button says "Draw Line:"
        drawButton.addActionListener(new ActionListener() { // This method makes the button function the way it should
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.repaint();// This means that everytime the button is clicked, a new line is drawn on the graph
            }
        });

        // Add the different components to the program window such as the coordinate inputs, The Graph & the button
        Container contentPane = getContentPane();
        contentPane.add(inputPanel, BorderLayout.NORTH); // input components at the top
        contentPane.add(drawingPanel, BorderLayout.CENTER); // cartesian graph at the center
        contentPane.add(drawButton, BorderLayout.SOUTH); // 'Draw Line' button at the bottom
    }

    // This method will draw and design the outline of the graph (or the drawing area)
    private void drawCartesianGraph(Graphics g) {
        int width = drawingPanel.getWidth(); // It stores the width of the drawing area
        int height = drawingPanel.getHeight(); // It stores the height of the drawing area
        g.setColor(Color.WHITE); // It sets the color WHITE
        g.fillRect(0, 0, width, height); // Fills the background with the white color
        g.setColor(Color.BLACK); // It sets the color BLACK for the x & y axis lines drawn
        g.drawLine(width / 2, 0, width / 2, height); // y-axis is drawn
        g.drawLine(0, height / 2, width, height / 2); // x-axis is drawn
    }

    // This method is used to draw the line as per the coordinates given by the user
    private void drawLine(Graphics g) {
        try {
            // Converts String inputs into integer values
            int x1 = Integer.parseInt(x1Field.getText());
            int y1 = Integer.parseInt(y1Field.getText());
            int x2 = Integer.parseInt(x2Field.getText());
            int y2 = Integer.parseInt(y2Field.getText());
            g.setColor(Color.RED); // sets the color of the line drawn as red
            g.drawLine(convertX(x1), convertY(y1), convertX(x2), convertY(y2)); // Draws a line from the coordinates 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers for coordinates."); // This will show a pop-up error message if the inputs of the coordinates aren't Integer values
        }
    }

    // This will make sure that the coordinates of x entered by the user will be calculated based on the origin (0,0) of the Cartesian Graph
    private int convertX(int x) {
        return drawingPanel.getWidth() / 2 + x;
    }

    // This will make sure that the coordinates of y entered by the user will be calculated based on the origin (0,0) of the Cartesian Graph
    private int convertY(int y) {
        return drawingPanel.getHeight() / 2 - y;
    }

    // This method will help run our code
    public static void main(String[] args) {
        CartesianGraph graph = new CartesianGraph(); // Displays the window
        graph.setVisible(true); // Makes the window visible
    }
}
