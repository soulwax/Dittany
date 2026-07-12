package de.cirrus.dittany;

import java.awt.*;
import java.awt.image.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.cirrus.dittany.gui.BitmapFont;
import de.cirrus.dittany.gui.GuiLabel;
import de.cirrus.dittany.gui.SettingsMenu;
import de.cirrus.dittany.level.EntityListCache;

public class Dittany extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int DEFAULT_SCALE = 3;

	private boolean keepRunning = true;
	private BufferedImage screenImage;

	private Bitmap screenBitmap;

	private InputHandler inputHandler;
	private Input mouse;
	private Game game;
	private PlayerView playerView;
	private SettingsMenu settingsMenu;
	private GuiLabel fpsLabel;
	private boolean paused;
	private int scale = DEFAULT_SCALE;
	private boolean showFps = true;

	public Dittany() {
		Dimension size = new Dimension(WIDTH * scale, HEIGHT * scale);

		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);

		inputHandler = new InputHandler(this);
	}

	public void start() {
		new Thread(this, "Game Thread").start();
	}

	public void stop() {
		keepRunning = false;
	}

	public void init() {
		Art.init();
		screenImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		screenBitmap = new Bitmap(screenImage);
		mouse = inputHandler.updateMouseStatus(scale);

		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "invisible"));

		game = new Game();
		playerView = new PlayerView(game, game.level.redPlayer, mouse);
		settingsMenu = new SettingsMenu(WIDTH, HEIGHT, scale, showFps);
		fpsLabel = new GuiLabel(0, 2, "0 FPS", 0xffffffff, BitmapFont.SMALL_GLYPH_SIZE, BitmapFont.SMALL_ADVANCE);
		requestFocus();
	}

	public void run() {
		init();

		double nsPerFrame = 1000000000.0 / 60.0;
		double unprocessedTime = 0;
		double maxSkipFrames = 10;

		long lastTime = System.nanoTime();
		long lastFrameTime = System.currentTimeMillis();
		int frames = 0;

		while (keepRunning) {
			long now = System.nanoTime();
			double passedTime = (now - lastTime) / nsPerFrame;
			lastTime = now;

			if (passedTime < -maxSkipFrames) passedTime = -maxSkipFrames;
			if (passedTime > maxSkipFrames) passedTime = maxSkipFrames;

			unprocessedTime += passedTime;

			boolean render = false;
			while (unprocessedTime > 1) {
				unprocessedTime -= 1;
				mouse = inputHandler.updateMouseStatus(scale);
				EntityListCache.reset();
				tick();
				render = true;
			}
			render = true;
			if (render) {
				EntityListCache.reset();
				render(screenBitmap);
				frames++;
			}

			if (System.currentTimeMillis() > lastFrameTime + 1000) {
				fpsLabel.setText(frames + " FPS");
				lastFrameTime += 1000;
				frames = 0;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			swap();
		}
	}

	private void swap() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		int screenW = getWidth();
		int screenH = getHeight();
		int w = WIDTH * scale;
		int h = HEIGHT * scale;

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenW, screenH);
		g.drawImage(screenImage, (screenW - w) / 2, (screenH - h) / 2, w, h, null);
		g.dispose();

		bs.show();
	}

	private void render(Bitmap screen) {
		playerView.render(screen);
		if (showFps) {
			fpsLabel.x = screen.w - fpsLabel.w - 2;
			fpsLabel.render(screen);
		}
		if (paused) settingsMenu.render(screen);
		if (mouse.onScreen) screen.draw(Art.i.cursors[0][0], mouse.x - 1, mouse.y - 1);
	}

	private void tick() {
		if (mouse.escape.typed) paused = !paused;
		if (paused) {
			settingsMenu.tick(mouse);
			if (settingsMenu.scale != scale) setScale(settingsMenu.scale);
			showFps = settingsMenu.showFps;
			if (settingsMenu.resume) paused = false;
			if (settingsMenu.quit) stop();
			return;
		}
		game.tick();
		playerView.tick();
	}

	private void setScale(int scale) {
		this.scale = scale;
		SwingUtilities.invokeLater(() -> {
			Dimension size = new Dimension(WIDTH * scale, HEIGHT * scale);
			setPreferredSize(size);
			setMaximumSize(size);
			setMinimumSize(size);
			Window window = SwingUtilities.getWindowAncestor(this);
			if (window != null) window.pack();
		});
	}

	public static void main(String[] args) {
		Dittany gameComponent = new Dittany();

		JFrame frame = new JFrame("Dittany");
		frame.add(gameComponent);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		gameComponent.start();
	}
}
