/* 
 * This Program Lets's The user draw lines on a graph by entering coordinates
 * 
 */

 /*
    Added Features:
    - Added a clear button to clear the entire graph and start fresh.
    - Added different colors to different lines drawn.
    - Added a seperate panel to display the distance values based on the colors of the lines and coordinates.


    Bugs to Fix: (Fixed!!)
    - Application window scalability issue.
  */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class CartesianGraphv2 extends JFrame {

    private Color[] colors = {Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW};

    class LineSegment {
        private int x1, y1, x2, y2; // These variable stores the values of x & y coordinates in units
        private int X1, Y1, X2, Y2; // These stores the values pf x & y coordinates in pixels
        private double dist;
        
        
        public LineSegment(int x1, int y1, int x2, int y2, double dist) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.dist = dist;
    
            X1 = 0;
            Y1 = 0;
            X2 = 0;
            Y2 = 0;
        }
    
    
        public void draw(Graphics g, int color_index) {
            color_index = color_index%colors.length;
            X1 = convertX(x1);
            Y1 = convertY(y1);
            X2 = convertX(x2);
            Y2 = convertY(y2);

            g.setColor(colors[color_index]);
            g.drawLine(X1, Y1, X2, Y2);

            g.setColor(Color.BLACK);
            // Draw filled circles at each end of the line
            int circleRadius = 5; // Set the radius of the circle
            g.fillOval(X1 - circleRadius / 2, Y1 - circleRadius / 2, circleRadius, circleRadius); // Draw circle at (x1, y1)
            g.fillOval(X2 - circleRadius / 2, Y2 - circleRadius / 2, circleRadius, circleRadius); // Draw circle at (x2, y2)
            
            
            //g.setFont(new Font("Arial", Font.PLAIN, 15)); // Set font size to 12
            
            //g.drawString("(" + x1 + "," + y1+ ")", X1, Y1- 5); // Labels vertex (x1, y1)
            //g.drawString("(" + x2 + "," + y2 + ")", X2, Y2 - 5); // Labels vertex (x2, y2)
            //g.drawString("dist("+String.format("%.2f", dist)+")", ((X1+X2)/2)-5, ((Y1+Y2)/2)-5); // This line will display the distance measure of the line
        }
    }

    private JTextField x1Field, y1Field, x2Field, y2Field;// These are variables that stores the coordinates of the two points (x1,y1) & (x2,y2)
    private JButton drawButton; // This is a variable that stores a button object which when clicked will draw a line on the graph
    private JButton clearButton;
    private JButton lineDataButton;
    private JPanel linesPanel;
    private JPanel drawingPanel; // This defines the section of our window that will be used for drawing lines and for the display of the Cartesian Graph.
    private List<LineSegment> lines = new ArrayList<>();
    private JFrame linesFrame;

    public CartesianGraphv2() {
        setTitle("Cartesian Graph v2"); // Gives a name to the window of our program
        setSize(515, 605); // sets the height and width of our window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // This command specifies that if we close the window, the program will terminate automatically
        //distance = 0.0;
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
                int i=0;
                for (LineSegment line : lines) {
                    line.draw(g, i); // Draws lines every time  value is enterd by the user
                    i++;
                }
            }
        };

        lineDataButton= new JButton("show data");
        lineDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (!lines.isEmpty()) {
                    showLinesFrame();
                } else {
                    JOptionPane.showMessageDialog(CartesianGraphv2.this, "No lines to show.");
                }
            }
        });

        clearButton = new JButton("clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                lines.clear();
                drawingPanel.repaint();
            }
        });

        // Create draw button that when clicked will draw a line on to the graph
        drawButton = new JButton("Draw Line"); // The label of the button says "Draw Line:"
        drawButton.addActionListener(new ActionListener() { // This method makes the button function the way it should
            @Override
            public void actionPerformed(ActionEvent e) {
                drawLine();
                //updateLinesPanel();
                //linesFrame.repaint();
                drawingPanel.repaint();// This means that everytime the button is clicked, a new line is drawn on the graph
                showLinesFrame();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                drawingPanel.repaint();
            }
        });
        
        JPanel lowerPanel = new JPanel(new GridLayout(1,3));
        lowerPanel.add(drawButton);
        lowerPanel.add(clearButton);
        lowerPanel.add(lineDataButton);

        // Add the different components to the program window such as the coordinate inputs, The Graph & the button
        Container contentPane = getContentPane();
        contentPane.add(inputPanel, BorderLayout.NORTH); // input components at the top
        contentPane.add(drawingPanel, BorderLayout.CENTER); // cartesian graph at the center
        contentPane.add(lowerPanel, BorderLayout.SOUTH); // 'Draw Line' button at the bottom
    }


    private void updateLinesPanel(){
        linesPanel = new JPanel(new GridLayout(lines.size(), 1));

            // Iterate through each line and add label with coordinates and distance
            int i =0;
            for (LineSegment line: lines) {

                i++;
                JLabel label = new JLabel("L" + i + "--> (" + line.x1 + "," + line.y1 + ")   (" + line.x2 + "," + line.y2 + ")  ||  Distance: " + String.format("%.2f",line.dist)+" units");
                label.setForeground(colors[i-1]); // Set the color of the label
                linesPanel.add(label);
            }
    }


    private void showLinesFrame() {

        if (linesFrame != null) {
            linesFrame.dispose();
        }

            linesFrame = new JFrame("Lines Data");
            linesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            linesFrame.setSize(300, 200);

            // Create a panel to display line coordinates and distances
            updateLinesPanel();

            linesFrame.add(linesPanel);

        linesFrame.setVisible(true);
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

        g.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font size to 12
        g.drawString("X",width -10, (height/2) -10); // prints X label in the positive side of X-axis
        g.drawString("-X",10, (height/2) -10); // prints -X label on the negetive side " " "
        g.drawString("-Y",(width/2) +10, height-10); // prints -Y label on the negative side of Y -axis
        g.drawString("Y",(width/2) +10, 10); // prints Y label on the positive side " " "

        g.setFont(new Font("Arial", Font.PLAIN, 10)); // Set font size to 12
        g.drawString("O",(width/2) +5, (height/2) +15); // labels the Origin point of graph (0,0)

        // Adds number lines on to the X-axis and Y-axis
        printAxisPoints(g);

    }


    // A method to mark all the points from X & Y axis
    private void printAxisPoints(Graphics g){
        int width = drawingPanel.getWidth();
        int height = drawingPanel.getHeight();
        int originX = (int) width/2;
        int originY = (int) height/2;

        int i= originX,j= originX;
        while(true){
            i = i+25; j = j-25;
            if(i >= width || j <= 0){
                break;
            }
            g.drawLine(i, originY-5 ,i, originY+5); // marks the X-axis points from top-down
            g.drawLine(j, originY-5 ,j, originY+5); // " " " " from bottom-up
        }
        i = originY; j = originY;
        while(true){
            i = i+25; j = j-25;
            if(i >= height || j <= 0){
                break;
            }
            g.drawLine(originX-5, i, originX+5, i); // marks the Y-axis points from top-down
            g.drawLine(originX-5, j, originX+5, j); //  ' ' ' ' from bottom-up
            j++;
        }
    }



    // This method is used to draw the line as per the coordinates given by the user
    private void drawLine() {
        try {
            // Converts String inputs into integer values
            int x1 = Integer.parseInt(x1Field.getText());
            int y1 = Integer.parseInt(y1Field.getText());
            int x2 = Integer.parseInt(x2Field.getText());
            int y2 = Integer.parseInt(y2Field.getText());
            // Addes the values of x & y coordinates of a line to a list of Line Segments
            lines.add(new LineSegment(x1, y1, x2, y2, CalDistance())); 
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid integers for coordinates."); // This will show a pop-up error message if the inputs of the coordinates aren't Integer values
        }
    }

    // This will make sure that the coordinates of x entered by the user will be calculated based on the origin (0,0) of the Cartesian Graph
    private int convertX(int x) {
        return drawingPanel.getWidth() / 2 + (x*25);
    }

    // This will make sure that the coordinates of y entered by the user will be calculated based on the origin (0,0) of the Cartesian Graph
    private int convertY(int y) {
        return drawingPanel.getHeight() / 2 - (y*25);
    }

    // This meathod caluclates the distance measure of the line
    private double CalDistance(){
        try{
            // Converts String inputs into integer values
            int x1 = Integer.parseInt(x1Field.getText());
            int y1 = Integer.parseInt(y1Field.getText());
            int x2 = Integer.parseInt(x2Field.getText());
            int y2 = Integer.parseInt(y2Field.getText());
            return Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2)); // applies the distance formula : √((x2 – x1)² + (y2 – y1)²)
        }catch (NumberFormatException e) {
            return 0.0; // if the user input is anything but a number, it will keep a default value value of 0.0
        }
        
    }

    // This method will help run our code
    public static void main(String[] args) {
        CartesianGraphv2 graph = new CartesianGraphv2(); // Displays the window
        graph.setVisible(true); // Makes the window visible
    }
}
