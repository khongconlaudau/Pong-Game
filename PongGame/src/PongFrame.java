import javax.swing.JFrame;

public class PongFrame extends JFrame{
	public PongFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(new PongPanel());
		this.setTitle("Pong game");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
