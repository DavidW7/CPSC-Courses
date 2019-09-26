#include "HSLAPixel.h"

namespace cs221util {
	HSLAPixel::HSLAPixel() {
		h = 1; // random number
		s = 0;
		l = 1;
		a = 1;
	}

	HSLAPixel::HSLAPixel(double hue, double sat, double lum) {
		h = hue;
		s = sat;
		l = lum;
		a = 1; //not transparent
	}

	HSLAPixel::HSLAPixel(double hue, double sat, double lum, double alp) {
		h = hue;
		s = sat;
		l = lum;
		a = alp;
	}
}