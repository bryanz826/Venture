package com.example.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.example.refs.ReferenceResource;
import com.example.state.StateManager;
import com.example.state.list.Intro;
import com.example.state.list.Menu;
import com.example.state.list.Play;
import com.example.utils.resource.Resource;

public class Splash
{
	/**
	 * A Screen instance with the appropriate layout
	 */
	private Screen	screen;

	/**
	 * Counting variable for setting the bar's progress
	 */
	private int		count;

	public Splash()
	{
		screen = new Screen(ReferenceResource.IMAGE_LOC + "flying-bonger.png");
		screen.setLocationRelativeTo(null);
		screen.setMaxProgress(50);
		screen.setVisible(true);

		add("cursor.png");

		add(ReferenceResource.METEOR_LOC + "meteor-brown-1.png");
		add(ReferenceResource.METEOR_LOC + "meteor-brown-2.png");
		add(ReferenceResource.METEOR_LOC + "meteor-brown-3.png");
		add(ReferenceResource.METEOR_LOC + "meteor-brown-4.png");
		add(ReferenceResource.METEOR_LOC + "meteor-gray-1.png");
		add(ReferenceResource.METEOR_LOC + "meteor-gray-2.png");
		add(ReferenceResource.METEOR_LOC + "meteor-gray-3.png");
		add(ReferenceResource.METEOR_LOC + "meteor-gray-4.png");

		add(ReferenceResource.PLAYER_LOC + "player-damaged-1.png");
		add(ReferenceResource.PLAYER_LOC + "player-damaged-2.png");
		add(ReferenceResource.PLAYER_LOC + "player-damaged-3.png");
		add(ReferenceResource.PLAYER_LOC + "player-orange.png");
		// add("icon.png");

		StateManager.addState(new Intro());
		StateManager.addState(new Menu());
		StateManager.addState(new Play());

		screen.setVisible(false);
	}

	private void add(String fileName)
	{
		new Resource(fileName);
		screen.setProgress(count++);
	}
}

/**
 * 
 * @author poroia
 */
@SuppressWarnings("serial")
class Screen extends JWindow
{
	/**
	 * Main layout for the JWindow
	 */
	private BorderLayout	borderLayout;

	/**
	 * Label for the splash image
	 */
	private ImageIcon		imageIcon;
	private JLabel			imageLabel;
	private JPanel			southPanel;
	private FlowLayout		southFlow;
	private JProgressBar	progressBar;

	public Screen(String image)
	{
		imageIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource(image)));
		borderLayout = new BorderLayout();
		imageLabel = new JLabel();
		southPanel = new JPanel();
		southFlow = new FlowLayout();
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);

		init();
	}

	private void init()
	{
		imageLabel.setIcon(imageIcon);
		imageLabel.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().setLayout(borderLayout);
		southPanel.setLayout(southFlow);
		southPanel.setBackground(Color.BLACK);
		getContentPane().add(imageLabel, BorderLayout.CENTER);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.add(progressBar, null);
		pack();
	}

	public void setMaxProgress(int maxProgress)
	{
		progressBar.setMaximum(maxProgress);
	}

	public void setProgress(int progress)
	{
		float percentage = ((float) progress / (float) progressBar.getMaximum()) * 100;

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				progressBar.setValue(progress);
				progressBar.setString("Loading: " + (int) percentage + "%");
			}
		});
	}
}
