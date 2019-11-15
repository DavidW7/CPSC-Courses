/**
 * @file vor.cpp
 * Implementation of functions in the filler namespace. 
 *
 */
//included within  "vor.h" for template instantiation


/* vorDotDFS given as an example for PA2 */
animation filler::vorDotDFS(PNG& img, double density,
                                int dotGrid,int dotSize, int frameFreq)
{
    dotColorPicker a(dotGrid,dotSize);
    return vor<Stack>(img, density, a, frameFreq);
}

animation filler::vorSolidDFS(PNG& img, double density, int frameFreq)
{
    /**
     * @todo Your code here! 
     */
    solidColorPicker s;
    return vor<Stack>(img, density, s, frameFreq); 
    


}

animation filler::vorFadeDFS(PNG& img, double density, double fadeFactor, int frameFreq)
{
    /**
     * @todo Your code here! 
     */
    fadeColorPicker f(fadeFactor);
    return vor<Stack>(img, density, f, frameFreq);

}


/* vorDotBFS given as an example for PA2 */
animation filler::vorDotBFS(PNG& img, double density,
                                int dotGrid,int dotSize, int frameFreq)
{
    dotColorPicker a(dotGrid, dotSize);
    return vor<Queue>(img, density, a, frameFreq);
}

animation filler::vorSolidBFS(PNG& img, double density, int frameFreq)
{
    /**
     * @todo Your code here! 
     */
    solidColorPicker s;
    return vor<Queue>(img, density, s, frameFreq); 
}

animation filler::vorFadeBFS(PNG& img, double density, double fadeFactor, int frameFreq)
{
    /**
     * @todo Your code here! 
     */
    fadeColorPicker f(fadeFactor);
    return vor<Queue>(img, density, f, frameFreq);
}


animation filler::vorCustomDFS(PNG& img, double density, int frameFreq)
{
	/**
	 * @todo Your code here!
	 */
	customColorPicker f;
	return vor<Stack>(img, density, f, frameFreq);
}

animation filler::vorCustomBFS(PNG& img, double density, int frameFreq)
{
	/**
	 * @todo Your code here!
	 */
	customColorPicker f;
	return vor<Queue>(img, density, f, frameFreq);
}

template <template <class T> class OrderingStructure>
animation filler::vor(PNG& img, double density, colorPicker& fillColor,
                       int frameFreq)
{

    /**
     * @todo You need to implement this function!
     *
     * This is a general description of a space filling algorithm, where 
     * the space is partitioned into a set of regions each color of which
     * is determined by the color of a point 
     * chosen from an original image. We call those original points
     * "centers." (See pictures in the specification.)
     *
     * The algorithm requires ordering structures used to orchestrate
     * the sequence of points filled. This structure type info is passed to the 
     * fill algorithm via its template parameter. For a breadth-first fill,
     * we use Queues, and for a depth-first fill, we use Stacks. Function
     * vorDotDFS provides an example call to function vor.
     * 
     * The algorithm includes the following steps:
     * 1) you will first select a random set
     * of coordinates (and their colors) to use as centers from which to
     * begin the fill. (this function is in file vor_given.cpp -- for 
     * testing purposes, you'll use regularly spaced points, rather than 
     * random.)
     * 2) Initialize a structure to use to maintain a collection of 
     * ordering structures
     *
     * 3) Initialize the fill: For each center c, in round-robin order, 
     *     do the following:
     *     a)  add the center to the its ordering structure. (every center 
     *         its own!
     *     b)  mark the location of that center as processed (the way you 
     *         do this is up to you!)
     *     c)  we have a choice to actually set the color of the pixel in 
     *         the fill when we add or when we remove. In our test cases, 
     *         we assume that you will change a color when a point is 
     *         added to a structure. (this is mostly relevant for checking 
     *         the animations.)
     *
     * 5) Complete the fill: Until all ordering structures (OS) are empty, 
     *    do the following for each OS, in a round robin order, indexing
     *    the iterations by k (an integer distance from the center):
     *    
     *      For each point less than or equal to distance k from its center:
     *      a)Remove the  point p from the ordering structure, and then...
     *
     *        i.    add p's unprocessed neighbors to the ordering structure if 
     *              they are within the appropriate distance from the center. We 
     *              describe this more carefully below. We call these the "valid"
     *              neighbors.
     *        ii.    use the colorPicker to set the new color of the valid neighbors.
     *        iii.    mark the valid neighbors as processed.
     *        iv.    as each neighbor is processed, if it is an appropriate 
     *              frame, send the current PNG to the
     *              animation (as described below).
    *
     *      b) When implementing your breadth-first and depth-first fills, 
     *            you will need to explore neighboring pixels in some order.
     *
     *        For this assignment, each pixel p has *up to* 8 neighbors, consisting of 
     *        the 8 pixels who share an edge with p. (We leave it to
     *        you to describe those 8 pixel locations, relative to the location
     *        of p.)
     *
     *        While the order in which you examine neighbors does not matter
     *        for a proper fill, you must use the same order as we do for
     *        your animations to come out like ours! 
     *
     *        The order you should put
     *        neighboring pixels **ONTO** the queue or stack is as follows:
     *        ** UP(-y), UPLEFT(-x,-y), LEFT(-x), LEFTDOWN(-x,+y), DOWN(+y), 
     *        DOWNRIGHT(+x,+y), RIGHT(+x), RIGHTUP(+x,-y)**
     *
     *        If you do them in a different order, your fill may
     *        still work correctly, but your animations will be different
     *        from the grading scripts!
     *
     *        TWO IMPORTANT NOTES: 
     *        1) *UP* here means towards the top of the image, so since an image has
     *        smaller y coordinates at the top, this is in the *negative y*
     *        direction. Similarly, *DOWN* means in the *positive y*
     *        direction. 
     *        2) not all of the 8 neighbors will be processed at every iteration of the
     *        while loop. You must assure that all vertices distance k or less
     *        from the center are processed (colored and put on the queue) before
     *        ANY whose distance is *greater than* k. k is a non-negative integer. 
     *        Finally, distance is the standard Euclidean distance  (computed 
     *        using the Pythagorean theorem). It is this requirement that assures
     *        that the fill grows outward from the center in the shape of a circle,
     *        rather than a diamond or a square. 
     *
     *      c) For every g pixels filled, **starting at the gth pixel**, you
     *        must add a frame to the animation, where g = frameFreq.
     *
     *        For example, if frameFreq is 4, then after the 4th pixel has
     *        been filled you should add a frame to the animation, then again
     *        after the 8th pixel, etc.  You must only add frames for the
     *        number of pixels that have been filled, not the number that
     *        have been checked. So if frameFreq is set to 1, a pixel should
     *        be filled every frame.
     *      d) Finally, as you leave the function, send one last frame to the
     *        animation. This frame will be the final result of the fill, and 
     *        it will be the one we test against.
     */



     /* Your code here. As a point of reference, we used three different helper
      * functions to complete this code. You may add as many as you wish, since
      * we will be grading vor.h. File "vor_given.cpp also includes the function
      * used to generate the original set of centers. 
      */



    // Initialization of the data structures
    int width = img.width();
    int height = img.height();
    PNG new_img(width, height);
    vector<center> vectorOfRandomCenters = randSample(img, density);                                // vector of random centers generated
    Queue<OrderingStructure<point>> queueOfOS;                                                      // Queue holding ordering structures that starts with its center processed valid neighbouring points
    vector<vector<bool>> unprocessedPoints(img.height()*2, vector<bool> (img.width()*2, true));        // nested vector representing the grid of image, initializing everything to false
    int pointsAdded = 0;                                                                            // counter for points added to the new image
    animation a;

    // Enquing centers to each OS, and enquing each OS to the vector queueOfOS
    for (unsigned i = 0; i < vectorOfRandomCenters.size(); i++) {
        // Guard against duplicate centers...
        OrderingStructure<point> os;
        point curP = vectorOfRandomCenters[i];
        curP.level = 0;                                                                             // Initialize the center's level to 0
        int curPX = vectorOfRandomCenters[i].x;
        int curPY = vectorOfRandomCenters[i].y;
        if (!unprocessedPoints[curPY][curPX]) {
            continue;
        }
        os.add(curP);                                                                               // adding center to the OS
        queueOfOS.enqueue(os);                                                                      // adding OS the the vector of OS
        unprocessedPoints[curPY][curPX] = false;                                                    // Setting center as processed 
        HSLAPixel* new_pixel = new_img.getPixel(curPX, curPY);                                      // set color of center point
        *new_pixel = fillColor(curP);
        pointsAdded++;                                                      
        if (pointsAdded % frameFreq == 0) {
            a.addFrame(new_img);
        }                                           // send frame to animation if needed            

    }

    int k;
    // pop OS, and add it to back of the queue if it's not empty 
    while (!queueOfOS.isEmpty()) {
        OrderingStructure<point> curOS = queueOfOS.dequeue();
        k = curOS.peek().level;
        // If ordering structure not empty, we know there's still unproccessed neighbours to check
        // each time pop the point at front, and only add neighbours if they're unprocessed and valid
        // Also, stop the loop if the next point's level is not the same
        while (!curOS.isEmpty() && curOS.peek().level == k) {
            point p = curOS.remove();
            // check if all neightbours are valid and unprocessed, if so then process the
            // neighbour and added to the end of the OS

            // Check up
            if (isPointValid(p.x, p.y - 1, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y - 1][p.x]) {
                point point_up(p.x, p.y - 1, p.c, p.level+1);
                //call processPoint helper
                int x = point_up.x;
                int y = point_up.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_up);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }
                // add to end of curOS
                curOS.add(point_up);
            }

            // Check up left
            if (isPointValid(p.x - 1, p.y - 1, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y - 1][p.x - 1]) {
                point point_upleft(p.x - 1, p.y - 1, p.c, p.level+1);
                // call processPoint helper
                int x = point_upleft.x;
                int y = point_upleft.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_upleft);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }
                // add to end of curOS
                curOS.add(point_upleft);
            }

            // Check left
            if (isPointValid(p.x - 1, p.y, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y][p.x - 1]) {
                point point_left(p.x - 1, p.y, p.c, p.level+1);
                // call processPoint helper
                int x = point_left.x;
                int y = point_left.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_left);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }
                // add to end of curOS
                curOS.add(point_left);
            }

            // Check down left
            if (isPointValid(p.x - 1, p.y + 1, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y + 1][p.x - 1]) {
                point point_downleft(p.x - 1, p.y + 1, p.c, p.level+1);
                // call processPoint helper
                int x = point_downleft.x;
                int y = point_downleft.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_downleft);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }
                // add to end of curOS
                curOS.add(point_downleft);
            }

            // Check down
            if (isPointValid(p.x, p.y + 1, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y + 1][p.x]) {
                point point_down(p.x, p.y + 1, p.c, p.level+1);
                // call processPoint helper
                int x = point_down.x;
                int y = point_down.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_down);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }
                // add to end of curOS
                curOS.add(point_down);
            }

            // Check down right
            if (isPointValid(p.x + 1, p.y + 1, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y + 1][p.x + 1]) {
                point point_downright(p.x + 1, p.y + 1, p.c, p.level+1);
                // call processPoint helper
                int x = point_downright.x;
                int y = point_downright.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_downright);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }                
                // add to end of curOS
                curOS.add(point_downright);
            }

            // Check right
            if (isPointValid(p.x + 1, p.y, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y][p.x + 1]) {
                point point_right(p.x + 1, p.y, p.c, p.level+1);
                // call processPoint helper
                int x = point_right.x;
                int y = point_right.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_right);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }        
                // add to end of curOS
                curOS.add(point_right);
            }

            // Check up right
            if (isPointValid(p.x + 1, p.y - 1, p.c.x, p.c.y, p.level, width, height) && unprocessedPoints[p.y - 1][p.x + 1]) {
                point point_upright(p.x + 1, p.y - 1, p.c, p.level+1);
                // call processPoint helper
                int x = point_upright.x;
                int y = point_upright.y;

                //TODO: process the point to the new image, check this part with a TA
                HSLAPixel* new_pixel = new_img.getPixel(x, y);
                *new_pixel = fillColor(point_upright);                            

                // set the unprocessedPoint to false now that it's been processed, and increment points added
                unprocessedPoints[y][x] = false;
                pointsAdded++;
                // sendFrame to see if frame needs to be sent
                if (pointsAdded % frameFreq == 0) {
                    a.addFrame(new_img);
                }        
                // add to end of curOS
                curOS.add(point_upright);
            }
        }

        // If there's still more neighbours to work with in the OS
        if (!curOS.isEmpty()) {
            // push it back to the queueOfOS
            queueOfOS.enqueue(curOS);
        }
        
    }

    // Don't forget to send frame in the end
    a.addFrame(new_img);
    return a;
} 

// Return true if point's distance from center <= k
// k is the level of the point you just popped off, not the neighbour (p in the paramter) you're trying to add,  the k of center is 0
bool filler::isPointValid(int x, int y, int cX, int cY, int k, int width, int height) { 
    // check if p is within img height and width:
    if (x < 0 || y < 0 || x >= width || y >= height) {
        return false;
    }

    int xdist = x - cX;
    int ydist = y - cY;
    double dist = pow(xdist, 2) + pow(ydist, 2);
    // return true if distanceSQ is within the level of popped off point + 1 
    return dist <= pow((k + 1), 2);

}