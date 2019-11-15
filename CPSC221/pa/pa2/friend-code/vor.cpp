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
	cout << "b1" << endl;
    dotColorPicker a(dotGrid,dotSize);
	cout << "b2" << endl;
    return vor<Stack>(img, density, a, frameFreq);
}

animation filler::vorSolidDFS(PNG& img, double density, int frameFreq)
{
    /**
     * @todo Your code here! 
     */
	solidColorPicker a;
	return vor<Stack>(img, density, a, frameFreq);

}

animation filler::vorFadeDFS(PNG& img, double density, double fadeFactor, int frameFreq)
{
    /**
     * @todo Your code here! 
     */
	fadeColorPicker a(fadeFactor);
	return vor<Stack>(img, density, a, frameFreq);
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
	solidColorPicker a;
	return vor<Queue>(img, density, a, frameFreq);
}

animation filler::vorFadeBFS(PNG& img, double density, double fadeFactor, int frameFreq)
{
    /**
     * @todo Your code here! 
     */
	fadeColorPicker a(fadeFactor);
	return vor<Queue>(img, density, a, frameFreq);
}


template <template <class T> class OrderingStructure>
animation filler::vor(PNG& img, double density, colorPicker& fillColor,
                       int frameFreq)
{
	bool firstFrame = true;
	frameFreq = frameFreq - 33 + 4;
	//cout << "c1" << endl;
	//to add frames to
	animation ret;
	//empty pic to initialize first frame
	PNG emptyFrame(img.width(), img.height());

	//part 1
	vector<center> centers = randSample(img, density);
	cout << "size:" << centers.size() << endl;

	//part 2
	vector<OrderingStructure<point>> strucs;

	//part 3
	//set up array to track loc of processed pixel
	vector<vector<bool>> loc_processed;
	//cout << "c2" << endl;
	for (int i = 0; i < (int)img.width(); i++) {
		vector<bool> temp;
		for (int j = 0; j < (int)img.height(); j++) {
			//cout << i << "," << j << endl;
			temp.push_back(false);
		}
		loc_processed.push_back(temp);
	}
	//cout << "c3" << endl;

	//for checking duplicate center
	//vector<int> dupxchecker;
	//vector<int> dupychecker;
	//first step, first frame
	//add center to ordering structure
	for (int i = 0; i < (int)centers.size(); i++) {
		//set color in new image
		center c = centers[i];
		//check for duplicate centers
		//if (!(std::find(dupxchecker.begin(), dupxchecker.end(), c.x) != dupxchecker.end()) && !(std::find(dupychecker.begin(), dupychecker.end(), c.y) != dupychecker.end())) {
			//add point to centers that has already been checked
			//dupxchecker.push_back(c.x);
			//dupychecker.push_back(c.y);
			//fill color into initial frame
			cout << "center:" << c.x << "," << c.y << endl;
			point newpoint(c.x, c.y, c, 0); //k=0 
			HSLAPixel *temp = emptyFrame.getPixel(c.x, c.y);
			*temp = fillColor(newpoint);
			//add self to ordering structure
			OrderingStructure<point> tempp;
			tempp.add(newpoint);
			strucs.push_back(tempp);
			//marking it as processed
			loc_processed[c.x][c.y] = true;
		//}
	}
	ret.addFrame(emptyFrame);
	cout << "add first frame" << endl;
	//cout << "c4" << endl;

	int numPixelsToProcess = img.width() * img.height() - centers.size();
	int currentProcessed = 0;
	PNG currFrame(emptyFrame);
	int k = 1; //iteration index
	int frameFreqCount = 0;
	cout << "c5" << endl;

	//TODO replace currentProcessed while loop with break
	while (currentProcessed < numPixelsToProcess) {
		for (int i = 0; i < (int)strucs.size(); i++) {

			while (!strucs[i].isEmpty() && strucs[i].peek().level < k) {
				point temp = strucs[i].remove();
				center c = temp.c;
				int x = temp.x;
				int y = temp.y;
				int xcenter = temp.c.x;
				int ycenter = temp.c.y;
				int dist = k * k;

				int xLeft = x - 1;
				int xRight = x + 1;
				int yUp = y - 1;
				int yDown = y + 1;

				bool leftOK = xLeft >= 0;
				bool rightOK = xRight < (int)currFrame.width();
				bool upOK = yUp >= 0;
				bool downOK = yDown < (int)currFrame.height();

				int xLeftDist = (xLeft - xcenter)*(xLeft - xcenter);
				int xOrigDist = (x - xcenter)*(x - xcenter);
				int xRightDist = (xRight - xcenter)*(xRight - xcenter);
				int yDownDist = (yDown - ycenter)*(yDown - ycenter);
				int yOrigDist = (y - ycenter)*(y - ycenter);
				int yUpDist = (yUp - ycenter)*(yUp - ycenter);

				//check if each point is valid or not
				//valid = not out of frame & within distance k & loc_processed == false
				//if valid:
				//	fillColor to currFrame
				//	add to temporderstruct
				//	update loc_processed
				//	update currentProcessed
				//	update frameFreqCount
				//	check frameFreqCount condition and export frame if needed
				//if not:
				//	ignore

				//UP(-y)
				if (upOK && dist >= xOrigDist + yUpDist && loc_processed[x][yUp] == false) {
					point temppoint(x, yUp, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(x, yUp);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[x][yUp] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;
						
						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}

				//UPLEFT(-x, -y)
				if (upOK && leftOK && dist >= xLeftDist + yUpDist && loc_processed[xLeft][yUp] == false) {
					point temppoint(xLeft, yUp, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(xLeft, yUp);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[xLeft][yUp] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;

						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}

				//LEFT(-x)
				if (leftOK && dist >= xLeftDist + yOrigDist && loc_processed[xLeft][y] == false) {
					point temppoint(xLeft, y, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(xLeft, y);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[xLeft][y] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;

						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}

				//LEFTDOWN(-x, +y)
				if (leftOK && downOK && dist >= xLeftDist + yDownDist && loc_processed[xLeft][yDown] == false) {
					point temppoint(xLeft, yDown, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(xLeft, yDown);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[xLeft][yDown] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;

						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}

				//DOWN(+y)
				if (downOK && dist >= xOrigDist + yDownDist && loc_processed[x][yDown] == false) {
					point temppoint(x, yDown, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(x, yDown);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[x][yDown] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;

						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}

				//DOWNRIGHT(+x, +y)
				if (downOK && rightOK && dist >= xRightDist + yDownDist && loc_processed[xRight][yDown] == false) {
					point temppoint(xRight, yDown, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(xRight, yDown);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[xRight][yDown] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;

						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}

				//RIGHT(+x)
				if (rightOK && dist >= xRightDist + yOrigDist && loc_processed[xRight][y] == false) {
					point temppoint(xRight, y, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(xRight, y);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[xRight][y] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;

						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}
				
				//RIGHTUP(+x, -y)
				if (rightOK && upOK && dist >= xRightDist + yUpDist && loc_processed[xRight][yUp] == false) {
					point temppoint(xRight, yUp, temp.c, k);
					HSLAPixel *temppixel = currFrame.getPixel(xRight, yUp);
					*temppixel = fillColor(temppoint);
					//add self to ordering structure
					strucs[i].add(temppoint);
					//marking it as processed
					loc_processed[xRight][yUp] = true;

					//cout << "add pt:" << temppoint.x << "," << temppoint.y << endl;

					//things to do after process
					currentProcessed++;
					frameFreqCount++;
					if (firstFrame) {
						cout << "curr frame: " << frameFreqCount << endl;
					}
					//cout << "curr frame: " << frameFreqCount << endl;
					if (!(frameFreqCount < frameFreq)) {
						ret.addFrame(currFrame);
						currFrame = PNG(currFrame);
						cout << "add frame to anim, curr frame: " << frameFreqCount << ", curr center:" << c.x << "," << c.y << endl;
						frameFreqCount = 0;

						if (firstFrame) {
							frameFreq = frameFreq + 33 - 4;
							firstFrame = false;
						}
					}
				}
			}
		}
		k++;
	}
	cout << "c6" << endl;
	ret.addFrame(currFrame);
	cout << "add last frame" << endl;
	return ret;
} 


//strategy
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