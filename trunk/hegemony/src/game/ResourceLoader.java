package game;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public enum ResourceLoader implements ImageObserver {

	INSTANCE;
	private Map<String,BufferedImage> images = new HashMap<String,BufferedImage>();
	private Map<String,AudioClip> sounds = new HashMap<String,AudioClip>();
	private Map<String,List<BufferedImage>> animations = new HashMap<String, List<BufferedImage>>();
	
	public void cleanup() {
		for (AudioClip sound : sounds.values()) {
			sound.stop();
		}
		
	}
	public AudioClip getSound(String name) {
		AudioClip sound = sounds.get(name);
		if (null != sound)
			return sound;
		
		URL url = null;
		try {			
			url = getClass().getClassLoader().getResource("res/" + name);
			sound = Applet.newAudioClip(url);
			sounds.put(name,sound);			
		} catch (Exception e) {
			System.err.println("Cound not locate sound " + name + ": " + e.getMessage());			
		}		
		
		return sound;
	}
	
	/**
	 * creates an animation given an image and number of animation frames
	 * @param animationFile file containing the animation
	 * @param frames number of frames in the animation
	 * @return list of images composing the animation
	 */
	public void createAnimation(String animationFile, int frames) {
		BufferedImage sprites = getSprite(animationFile);
		List<BufferedImage> animation = new ArrayList<BufferedImage>();
		int xOffset = sprites.getWidth()/frames;
		int dx = 0;
		while(dx < sprites.getWidth()) {
			BufferedImage sprite = createCompatible(xOffset, sprites.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = sprite.getGraphics();
			g.drawImage(sprites, 0, 0, xOffset, sprites.getHeight(), dx, 0, (dx += xOffset), sprites.getHeight(), this);
			animation.add(sprite);
		}
		animations.put(animationFile, animation);		
	}
	
	/**
	 * creates a compatible image in memory, faster than using the original image format
	 * @param width image width
	 * @param height image height
	 * @param transparency type of transparency
	 * @return a compatible BufferedImage
	 */
	public static BufferedImage createCompatible(int width, int height,
			int transparency) {
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		BufferedImage compatible = gc.createCompatibleImage(width, height,
				transparency);
		return compatible;		
	}

	public static BufferedImage getImageWithOpacity(BufferedImage src, float alpha)
    {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                                               BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = dest.createGraphics();
        int rule = AlphaComposite.SRC_OVER;
        AlphaComposite ac = AlphaComposite.getInstance(rule, alpha);
        g2.setComposite(ac);
        g2.drawImage(src, null, 0, 0);
        g2.dispose();
        return dest;
    }
	
	public BufferedImage getSprite(String name, float alpha) {
		return getImageWithOpacity(getSprite(name), alpha);
	}
	
	/**
	 * check if image is cached, if not, load it
	 * @param name
	 * @return
	 */
	public BufferedImage getSprite(String name) {
		BufferedImage image = images.get(name);
		if (null != image)
			return image;

		URL url = null;
		try {
			url = getClass().getClassLoader().getResource("res/" + name);
			image = ImageIO.read(url);
			//store a compatible image instead of the original format
			BufferedImage compatible = createCompatible(image.getWidth(), image.getHeight(), Transparency.BITMASK);
			compatible.getGraphics().drawImage(image, 0,0,this);

			images.put(name,compatible);			
		} catch (Exception e) {
			System.err.println("Cound not locate image " + name + ": " + e.getMessage());			
		}		
		
		return image;
	}
	
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return (infoflags & (ALLBITS|ABORT)) == 0;
	}
	public BufferedImage getAnimationFrame(String animationName, int frame) {
		List<BufferedImage> animation = animations.get(animationName);
		return (null == animation ? null : animation.get(frame));
	}

}
