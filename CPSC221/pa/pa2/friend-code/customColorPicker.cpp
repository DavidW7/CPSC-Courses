#include "customColorPicker.h"

customColorPicker::solidColorPicker()
{
/* your code here  :)  */
}

HSLAPixel customColorPicker::operator()(point p)
{
	HSLAPixel ret;
	ret.h = p.c.color.h % 0.2;
	ret.s = p.c.color.s % 0.2;
	ret.l = p.c.color.l % 0.2;
	return ret;

}
