#include "customColorPicker.h"

customColorPicker::customColorPicker()
{
/* your code here  :)  */
}

HSLAPixel customColorPicker::operator()(point p)
{
	HSLAPixel ret;
	ret.h = fmod(p.c.color.h, 0.2);
	ret.s = fmod(p.c.color.s, 0.2);
	ret.l = p.c.color.l;
	return ret;

}
