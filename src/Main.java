import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	private static final String PATH_IMAGE_A = "image_a.png";
	private static final String PATH_IMAGE_B = "image_b.png";
	private static final String PATH_OUTPUT = "output_image.png";

	public static void main(String[] args) {

		BufferedImage bufferedImageA = null;
		BufferedImage bufferedImageB = null;
		BufferedImage bufferedImageOutput = null;

		float diffProportion = 0;

		boolean diff = false;

		// Reads the input images
		try {
			bufferedImageA = ImageIO.read(new File(PATH_IMAGE_A));
			bufferedImageB = ImageIO.read(new File(PATH_IMAGE_B));
			bufferedImageOutput = new BufferedImage(bufferedImageA.getWidth(), bufferedImageA.getHeight(),
					BufferedImage.TYPE_INT_ARGB);

		} catch (IOException e) {
			System.err.println("Error while loading images: " + e.getMessage());
		}

		// Verify if both images have the same dimension
		if (bufferedImageA.getWidth() != bufferedImageB.getWidth()
				|| bufferedImageA.getHeight() != bufferedImageB.getHeight()) {
			System.out.println("ERROR: The images must have the same dimensions");
			System.exit(0);
		}

		// Iterate through every pixel in both images
		for (int x = 0; x < bufferedImageA.getWidth(); x++) {
			for (int y = 0; y < bufferedImageA.getHeight(); y++) {

				Color colorPixelA = new Color(bufferedImageA.getRGB(x, y));
				Color colorPixelB = new Color(bufferedImageB.getRGB(x, y));

				/*
				 * For each pixel in the source images, verify if both of them
				 * have the same RGB values. If not, then calculate the average
				 * value of those pixels. 
				 * 
				 * On the other hand, if both of them are equal, then just
				 * define it as a blank pixel.
				 * 
				 * Finally, draw that value at the
				 * (x,y) coordinates on the result image
				 */
				if (colorPixelA.getRed() != colorPixelB.getRed() || colorPixelA.getGreen() != colorPixelB.getGreen()
						|| colorPixelA.getBlue() != colorPixelB.getBlue()
						|| colorPixelA.getAlpha() != colorPixelB.getAlpha()) {
					bufferedImageOutput.setRGB(x, y,
							new Color(((colorPixelA.getRed() + colorPixelB.getRed()) / 2),
									((colorPixelA.getGreen() + colorPixelB.getGreen()) / 2),
									((colorPixelA.getBlue() + colorPixelB.getBlue()) / 2),
									((colorPixelA.getAlpha() + colorPixelB.getAlpha()) / 2)).getRGB());
					diff = true;
					diffProportion += 1;
				} else {
					bufferedImageOutput.setRGB(x, y, Color.WHITE.getRGB());
				}

			}
		}

		try {
			File outputFile = new File(PATH_OUTPUT);
			ImageIO.write(bufferedImageOutput, "png", outputFile);
		} catch (IOException e) {
			System.out.println("ERROR: The output image could not be saved");
		}

		System.out.println("Output\n------");

		if (diff) {
			System.out.println("The images are different");
			System.out.println("Different pixels proportion: "
					+ (diffProportion / (bufferedImageA.getWidth() * bufferedImageA.getHeight()) * 100) + "%");
			System.out.println("Output image: " + PATH_OUTPUT);

		} else {
			System.out.println("The images are equal");
		}

	}

}
