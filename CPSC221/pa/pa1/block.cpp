#include "block.h"

/**
 * Returns the width of the block.
 */
int Block::width() const{/*your code here*/
	return data.size();
}

/**
 * Returns the height of the block.
 */
int Block::height() const{
	return data[0].size();
}

/**
 * Default Block constructor.
 */
Block::Block() {/* nothing */}

/**
 * Useful Block constructor.
 * Makes a block from the rectangle of width by height pixels in im
 * whose upper-left corner is at position (x,y).
 */
Block::Block(PNG & im, int x, int y, int width, int height) {
	data.resize(width);
	for (int i = 0; i < width; i++) {
		data[i].resize(height);
	}

	for (int i = 0; i < width; i++) {
		for (int j = 0; j < height; j++) {
			//cout << i << ' ' << j << '\n';
			HSLAPixel *temp = im.getPixel(i+x, j+y);
			//cout << "a" << '\n';
			data[i][j] = *temp;
			//cout << "b" << '\n';
		}
	}
}

/**
 * Draws the block at position (x,y) in the image im
 */
void Block::render(PNG & im, int x, int y) const {/*your code here*/
	int width = data.size();
	int height = data[0].size();
	
	for (int i = 0; i < width; i++) {
		for (int j = 0; j < height; j++) {
			*(im.getPixel(i+x, j+y)) = data[i][j];
		}
	}
}

/**
 * Changes the saturation of every pixel in the block to 0,
 * which removes the color, leaving grey.
 */
void Block::greyscale() {/*your code here*/
	int width = data.size();
	int height = data[0].size();

	for (int i = 0; i < width; i++) {
		for (int j = 0; j < height; j++) {
			data[i][j].s = 0;
		}
	}
}
