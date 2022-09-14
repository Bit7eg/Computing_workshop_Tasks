import javax.swing.*;
import java.awt.*;

public class InterpolationGraphic {
    private JFrame frame;
    private JLabel statusLabel;
    private JTextField colorTextField;
    private JTextField nameTextField;
    private GraphicPanel graphicPanel;
    private final Integer width = 600;
    private final Integer height = 300;

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
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initContent() {
        Container mainContainer = frame.getContentPane();
        mainContainer.setLayout(new BorderLayout());

        JPanel bottomPanel = createBottomPanel();
        mainContainer.add(bottomPanel, BorderLayout.SOUTH);

        Box leftPanel = createLeftPanel();
        mainContainer.add(leftPanel, BorderLayout.WEST);

	graphicPanel = new GraphicPanel();
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

    private Box createLeftPanel() {
        Box panel = Box.createVerticalBox();

        JLabel title = new JLabel("Plotting a function");
        title.setFont(new Font(null, Font.BOLD, 12));
        panel.add(title);

        panel.add(Box.createVerticalStrut(20));

        panel.add(new JLabel("Name:"));

        nameTextField = new JTextField();
        nameTextField.setMaximumSize(new Dimension(300, 30));
        panel.add(nameTextField);

        panel.add(new JLabel("Color:"));

        colorTextField = new JTextField("#FF0000");
        colorTextField.setMaximumSize(new Dimension(300, 30));
        panel.add(colorTextField);

        panel.add(Box.createVerticalGlue());

        JButton button = new JButton("Draw");
        panel.add(button);

	button.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			changeGraphicColor();
		}
	});
        return panel;
    }

    private void changeGraphicColor() {
	    String name = nameTextField.getText();
	    String color = colorTextField.getText();
	    graphicPanel.setNameAndColor(name, color);
    }
}
