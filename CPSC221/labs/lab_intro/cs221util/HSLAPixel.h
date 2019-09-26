#ifndef CS221UTIL_HSLAPIXEL_H
#define CS221UTIL_HSLAPIXEL_H

namespace cs221util {
	class HSLAPixel {
	public:
		double h; //hue
		double s; //saturation
		double l; //luminanace
		double a; //alpha
		HSLAPixel();
		HSLAPixel(double hue, double sat, double lum);
		HSLAPixel(double hue, double sat, double lum, double alp);
	};
}

#endif //CS221UTIL_HSLAPIXEL_H