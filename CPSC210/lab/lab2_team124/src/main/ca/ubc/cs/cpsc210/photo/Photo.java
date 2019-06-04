package ca.ubc.cs.cpsc210.photo;

import ca.ubc.cs.cpsc210.utility.Thumbnail;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


// Represents a photo having a name, a creation date, an album and a set of tags.
public class Photo {
    public static final int MAX_WIDTH = 600;
    public static final int MAX_HEIGHT = 400;

	private Album album;
	private Set<Tag> tags;
	private String name;
	private Date dateCreated;
	private String description;
	
	private BufferedImage image;
	private Thumbnail thumbnail;

	// EFFECTS: constructs a photo with the given name, having no tags; creation date, description and album are null.
	public Photo(String name) {
		this.name = name;
        tags = new HashSet<Tag>();
        dateCreated = null;
        description = null;
        album = null;
	}

	public String getName() {
		return name;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album newAlbum) {
		album = newAlbum;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public BufferedImage getImage()  {
        return image;
    }

    // EFFECTS: returns thumbnail's image
    public Image getThumbnailImage()  {
        return thumbnail.getThumbnailImage();
    }

    // EFFECTS: returns an unmodifiable set of this photo's tags
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    // MODIFIES: this
    // EFFECTS: loads photo from file, scaling it to fit within a rectangle of size MAX_WIDTH X MAX_HEIGHT;
    //          returns true if operation is successful, false otherwise.
    public boolean loadPhoto(File file) {
        try {
            image = ImageIO.read(file);

            if (image == null)
                return false;

            scaleImage();

            thumbnail = new Thumbnail(image);
        } catch (IOException ioe) {
            return false;
        }

        return true;
    }

    // MODIFIES: this
    // EFFECTS: scales image so that it fits within rectangle of size MAX_WIDTH x MAX_HEIGHT
    private void scaleImage() {
        Image scaled = null;
        double imageRatio = (double) image.getWidth() / image.getHeight();
        double windowRatio = (double) MAX_WIDTH / MAX_HEIGHT;

        if (image.getWidth() > MAX_WIDTH && image.getHeight() > MAX_HEIGHT
                && imageRatio > windowRatio)
            scaled = image.getScaledInstance(MAX_WIDTH, -1, Image.SCALE_DEFAULT);
        else if (image.getWidth() > MAX_WIDTH && image.getHeight() > MAX_HEIGHT)
            scaled = image.getScaledInstance(-1, MAX_HEIGHT, Image.SCALE_DEFAULT);
        else if (image.getWidth() > MAX_WIDTH)
            scaled = image.getScaledInstance(MAX_WIDTH, -1, Image.SCALE_DEFAULT);
        else if (image.getHeight() > MAX_HEIGHT)
            scaled = image.getScaledInstance(-1, MAX_HEIGHT, Image.SCALE_DEFAULT);

        if (scaled != null) {
            image = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null),
                    BufferedImage.TYPE_INT_RGB);

            Graphics g = image.createGraphics();
            g.drawImage(scaled, 0, 0, null);
            g.dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds tag to this photo, if photo does not already have it
	public void addTag(Tag tag) {
		if(!tags.contains(tag)){
			tags.add(tag);
			tag.addToPhoto(this);
		}		
	}
	
	// MODIFIES: this
    // EFFECTS: removes tag from this photo
	public void removeTag(Tag tag) {
		if(tags.contains(tag)){
			tags.remove(tag);
			tag.removeFromPhoto(this);
		}		
	}

    // MODIFIES: this
    // EFFECTS: converts image to black and white
    public void toBlackAndWhite() {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                image.setRGB(x, y, rgbToGray(rgb));
            }
        }
    }

    // EFFECTS: returns gray scale equivalent of given RGB colour
    private int rgbToGray(int rgb) {
        Color c = new Color(rgb);
        int red = c.getRed();
        int blue = c.getBlue();
        int green = c.getGreen();
        int average = (red + blue + green) / 3;
        Color greyColor = new Color(average, average, average);
        return greyColor.getRGB();
    }

    // MODIFIES: this
    // EFFECTS: flips the image horizontally
    public void flipHorizontal() {
	    // clone the original image to use as reference, from stack overflow question 3514158
	    BufferedImage originalImg = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
	    Graphics g = originalImg.getGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();

        int width = image.getWidth();
        int height = image.getHeight();

        for (int x = 0; x < width / 2 ; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(width - 1 - x, y, image.getRGB(x, y));
                image.setRGB(x, y, originalImg.getRGB(width - 1 - x, y));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: blurs the image
    public void blur() {
        BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = getBlurredColourAt(x, y);
                temp.setRGB(x, y, rgb);
            }
        }

        Graphics g = image.createGraphics();
        g.drawImage(temp, 0, 0, null);
        g.dispose();
    }

    // EFFECTS: gets colour of blurred pixel at (x, y) in image
    private int getBlurredColourAt(int x, int y) {
        final int SPREAD = 1;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int count = 0;

        for (int dx = -SPREAD; dx <= SPREAD; dx++) {
            for (int dy = -SPREAD; dy <= SPREAD; dy++) {
                if (isInBounds(x + dx, y + dy)) {
                    Color rgb = new Color(image.getRGB(x + dx, y + dy));
                    redSum += rgb.getRed();
                    blueSum += rgb.getBlue();
                    greenSum += rgb.getGreen();
                    count++;
                }
            }
        }

        return new Color(redSum / count, greenSum / count, blueSum / count).getRGB();
    }

    // EFFECTS: returns true if (x, y) is within the bounds of the image
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight();
    }

    // MODIFIES: this
    // EFFECTS:  darken and alternate hue of the photo
    public void darkenAndAlternateHue() {
        BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = getDarkenColourAt(x, y);
                temp.setRGB(x, y, rgb);
            }
        }

        Graphics g = image.createGraphics();
        g.drawImage(temp, 0, 0, null);
        g.dispose();
    }

    private int getDarkenColourAt(int x, int y) {
        Color rgb = new Color(image.getRGB(x, y ));

        int reductionStep = 10;

        int r = rgb.getRed() - reductionStep;
        r = keepInRGBBound(r);
        int b = rgb.getBlue() - reductionStep;
        b = keepInRGBBound(b);
        int g = rgb.getGreen() - reductionStep;
        g = keepInRGBBound(g);
        return new Color(r, b, g).getRGB();
    }

    private int keepInRGBBound(int r) {
        if (r > 255) {
            return 255;
        } else if (r < 0) {
            return 0;
        } else {
            return r;
        }
    }

	@Override
	public String toString(){
		return "Photo(" + name + ")";
	}
}
