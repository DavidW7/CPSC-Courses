#include "fadeColorPicker.h"

fadeColorPicker::fadeColorPicker(double fade)
{
    /* your code here */
    fadeFactor = fade;
}

HSLAPixel fadeColorPicker::operator()(point p)
{
    /* your code here */

    /**
     * Picks the color for pixel (x, y), which is a member of point p. 
     * The new color consists of components h, s, and l.
     * The hue h, and saturation s, are unchanged by 
     * the function, but the luminance decays as the 
     * distance from the current point to the center,
     * increases.
     *
    *  The new luminance is simply newL = centerL * (fadeFactor)^(distSq).
    *  centerL, and all the information you need to compute distSq is
    * given via the input parameter p.
    * */

   // distsq is the 
   HSLAPixel new_point;
   new_point.h = p.c.color.h;
   new_point.s = p.c.color.s;
   double centerL = p.c.color.l;
   int xdist = p.x - p.c.x;
   int ydist = p.y - p.c.y;
   double distSq = sqrt(pow(xdist, 2) + pow(ydist, 2));
   new_point.l = centerL * pow(fadeFactor, distSq);
   return new_point;
}
