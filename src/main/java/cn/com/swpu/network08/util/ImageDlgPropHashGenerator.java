package cn.com.swpu.network08.util;

import android.graphics.Bitmap;

/**
 * 
 * @author franklin.li
 *
 */
public class ImageDlgPropHashGenerator {
	/**
	 * 生成图片指纹
	 * @param filename 文件名
	 * @return 图片指纹
	 */
	public static String generatorSimpleImageDlgPropHash(Bitmap sourceBitmap) {
		int width = 8;
		int height = 8;
		// 第一步，缩小尺寸。
		// 将图片缩小到8x8的尺寸，总共64个像素。这一步的作用是去除图片的细节，只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。
		Bitmap tempBitmap = ImageUtil.zoomBitmap(sourceBitmap, width, height);
		// 第二步，简化色彩。
		// 将缩小后的图片，转为64级灰度。也就是说，所有像素点总共只有64种颜色。
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i * height + j] = ImageUtil.rgbToGray(tempBitmap.getPixel(i, j));
			}
		}
		// 第三步，计算平均值。
		// 计算所有64个像素的灰度平均值。
		int avgPixel = ImageUtil.average(pixels);

		// 第四步，比较像素的灰度。
		// 将每个像素的灰度，与平均值进行比较。大于或等于平均值，记为1；小于平均值，记为0。
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// 第五步，计算哈希值。
		// 将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
		StringBuffer hashCode = new StringBuffer();
		for (int i = 0; i < comps.length; i+= 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1] * (int) Math.pow(2, 2) + comps[i + 2] * (int) Math.pow(2, 1) + comps[i + 2];
			hashCode.append(ImageUtil.binaryToHex(result));
		}
		// 此简单特征码用于计算图片间的汉明距离。
		return hashCode.toString();
	}
}
