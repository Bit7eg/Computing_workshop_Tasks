import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class InterpolationGraphic {
    private JFrame frame;
    private JLabel statusLabel;
    private JTextField minXTextField;
    private JTextField maxXTextField;
    private JTextField nodeNumberTextField;
    private JTextField functionColorTextField;
    private JTextField polyColorTextField;
    private JTextField lineColorTextField;
    private JTextField nodeColorTextField;
    private GraphicPanel graphicPanel;

    public InterpolationGraphic() {
        createWindow();
        initContent();
    }

    public void show() {
        frame.setVisible(true);
    }

    public void hide() {
        frame.setVisible(false);
    }

    private void createWindow() {
        frame = new JFrame("Interpolation");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initContent() {
        Container mainContainer = frame.getContentPane();
        mainContainer.setLayout(new BorderLayout());

        JPanel bottomPanel = createBottomPanel();
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);

        Box rightPanel = createRightPanel();
        mainContainer.add(rightPanel, BorderLayout.EAST);

	    graphicPanel = new GraphicPanel((x) -> {
	        double lx = x;
            return lx * lx * lx;
        });
	    graphicPanel.setBackground(Color.WHITE);
	    mainContainer.add(graphicPanel);
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        statusLabel = new JLabel("Content loading...");
        panel.add(statusLabel);
        return panel;
    }

    private Box createRightPanel() {
        Box panel = Box.createVerticalBox();

        JLabel title = new JLabel("Plotting a function");
        title.setFont(new Font(null, Font.BOLD, 12));
        panel.add(title);

        panel.add(Box.createVerticalStrut(20));

        panel.add(new JLabel("Minimum X value:"));

        minXTextField = new JTextField("-1.0");
        minXTextField.setMaximumSize(new Dimension(300, 30));
        minXTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                minXTextField.setText(graphicPanel.setMinX(minXTextField.getText()).toString());
            }
        });
        panel.add(minXTextField);

        panel.add(new JLabel("Maximum X value:"));

        maxXTextField = new JTextField("1.0");
        maxXTextField.setMaximumSize(new Dimension(300, 30));
        maxXTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                maxXTextField.setText(graphicPanel.setMaxX(maxXTextField.getText()).toString());
            }
        });
        panel.add(maxXTextField);

        panel.add(new JLabel("Nodes number:"));

        nodeNumberTextField = new JTextField("2");
        nodeNumberTextField.setMaximumSize(new Dimension(300, 30));
        nodeNumberTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                nodeNumberTextField.setText(
                        graphicPanel.setArgumentsNumber(nodeNumberTextField.getText()).toString()
                );
            }
        });
        panel.add(nodeNumberTextField);

        panel.add(new JLabel("Function color:"));

        functionColorTextField = new JTextField("#FF0000");
        functionColorTextField.setMaximumSize(new Dimension(300, 30));
        functionColorTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                functionColorTextField.setText(
                        graphicPanel.setFunctionColor(functionColorTextField.getText()).toString()
                );
            }
        });
        panel.add(functionColorTextField);

        panel.add(new JLabel("Polynomial color:"));

        polyColorTextField = new JTextField("#00FF00");
        polyColorTextField.setMaximumSize(new Dimension(300, 30));
        polyColorTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                polyColorTextField.setText(
                        graphicPanel.setPolynomialColor(polyColorTextField.getText()).toString()
                );
            }
        });
        panel.add(polyColorTextField);

        panel.add(new JLabel("Line function color:"));

        lineColorTextField = new JTextField("#0000FF");
        lineColorTextField.setMaximumSize(new Dimension(300, 30));
        lineColorTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                lineColorTextField.setText(
                        graphicPanel.setLineFuncColor(lineColorTextField.getText()).toString()
                );
            }
        });
        panel.add(lineColorTextField);

        panel.add(new JLabel("Nodes color:"));

        nodeColorTextField = new JTextField("#FF00FF");
        nodeColorTextField.setMaximumSize(new Dimension(300, 30));
        nodeColorTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                nodeColorTextField.setText(
                        graphicPanel.setNodeColor(nodeColorTextField.getText()).toString()
                );
            }
        });
        panel.add(nodeColorTextField);

        //panel.add(Box.createVerticalGlue());

        return panel;
    }
}
