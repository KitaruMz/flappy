import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.sub.ImageObject;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.resource.image.FileImageResource;
import org.frice.utils.FileUtils;
import org.frice.utils.shape.FCircle;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FClock;
import org.frice.utils.time.FTimer;
import org.frice.utils.time.FpsCounter;
import org.frice.utils.media.AudioManager;
import org.frice.utils.media.AudioPlayer;
import org.frice.obj.button.FText;
import org.frice.obj.button.SimpleButton;
import org.frice.obj.button.SimpleText;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import static org.frice.Initializer.launch;

public class flappy extends Game {
	private FText text1;
	private FText text2;
	private SimpleButton but;
	// private ShapeObject object;
	private static int ttt = 0;
	private double a = 0;
	private double b = 0;
	private FTimer timer1 = new FTimer(20);
	private FTimer timer = new FTimer(4000);
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		// System.out.print("Please input start" + "\n");
		// String w = TextIO.getlnWord();
		// if (w.equals("start")) {
		// System.out.println("The game will start in 5 seconds");
		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }

		launch(flappy.class);

		// } else {
		// return;
		// }

	}

	private ImageObject bird;
	private List<ShapeObject> objects = new ArrayList<>();

	@Override
	public void onInit() {
		setAlwaysTop(true);
		setShowFPS(true);
		setFullScreen(false);
		setSize(500, 800);
		setTitle("Flappy Challen");
		AudioPlayer audioPlayer = AudioManager.getPlayer("a.mp3");
		audioPlayer.start();
		but = new SimpleButton(ColorResource.宝强绿, "aaa", 50, 50, 50, 50);
		// addObject(but);

		bird = new ImageObject(new FileImageResource("2.png"), 20, 555) {

			{
				addKeyListener(null, e -> {

					addAnim(new AccelerateMove(0, 10));

				}, null);

			}
		};
		addObject(bird);

		addKeyListener(null, e -> {
			bird.stopAnims();
			bird.addAnim(new AccelerateMove(0, 10));
			bird.addAnim(new SimpleMove(0, -400));
		}, null);
		// object = new ShapeObject(ColorResource.GREEN, new FCircle(20.0), 50.0, 200.0);
		// object.addAnim(new AccelerateMove(0, 10));
		text1 = new SimpleText("Press any key to jump", 20, 40);
		text2 = new SimpleText("Presented by yijiez3 & yuefeng3", 20, 20);

		
		addObject(text1);
		addObject(text2);

		// addObject(but);
	}

	@Override
	public void onRefresh() {
		if (timer1.ended()) {
			a += 0.0009;
			b += a;
			addObject(new ImageObject(new FileImageResource("3.png"), bird.getX(), bird.getY()) {
				{

					addAnim(new SimpleMove((int) (Math.sin(b) * 256), (int) (Math.cos(b) * 256)));

					// addAnim(new SimpleMove((int) (500), (int) (0)));
				}
			});
			System.out.println(getLayers()[0].getObjects().size());
		}

		if (timer.ended())
			addKeyListener(null, e -> {
				addObject(getObj((int) (Math.random() * 300)));

			}, null);

		boolean die = false;
		if (bird.getY() > getHeight() + 20)
			die = true;

		// objects.removeIf(ShapeObject::getDied);

		if (objects.stream().anyMatch(o -> o.collides(bird)))
			die = true;
		if (die) {
			new Thread(() -> FileUtils.image2File(getScreenCut().getImage(), "截屏.png")).start();

			dialogShow("Game Over" + "\n" + "You lived " + FClock.getCurrent() / 1000 + " seconds" + "\n"
					+ "press ok twice to restart");

			System.out.println(ttt);

			launch(flappy.class);

			// System.exit(0);

		}
	}

	private ShapeObject[] getObj(int height) {
		ttt++;

		return new ShapeObject[] { new ShapeObject(ColorResource.GREEN, new FRectangle(50, height), 550, 0) {
			{
				addAnim(new SimpleMove(-150, 0));
				objects.add(this);
			}
		}, new ShapeObject(ColorResource.GREEN, new FRectangle(50, getHeight() - height - 400), 550, height + 400) {
			{
				addAnim(new SimpleMove(-150, 0));
				objects.add(this);
			}
		} };
	}

}
