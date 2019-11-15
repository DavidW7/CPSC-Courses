#include "solidColorPicker.h"

solidColorPicker::solidColorPicker()
{
/* your code here  :)  */
    
}

HSLAPixel solidColorPicker::operator()(point p)
{
/* your code here */
    // Returns the color of p's center 
    HSLAPixel new_point;
    new_point.h = p.c.color.h;
    new_point.s = p.c.color.s;
    new_point.l = p.c.color.l;
    new_point.a = p.c.color.a;

    return new_point;

}
