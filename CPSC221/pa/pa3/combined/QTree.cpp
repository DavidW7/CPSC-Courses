/**
 *
 * Balanced Quad Tree (pa3)
 *
 * This file will be used for grading.
 *
 */

#include "QTree.h"

 // Return the biggest power of 2 less than or equal to n
int biggestPow2(int n) {
	if (n < 1) return 0;
	int v = 1;
	while (v <= n) v <<= 1;
	return v >> 1;
}

QTree::Node::Node(PNG & im, pair<int, int> ul, int sz, Node *par, int l)
	:upLeft(ul), size(sz), parent(par), nw(NULL), ne(NULL), sw(NULL), se(NULL), loc(l)
{
	var = varAndAvg(im, ul, size, avg);
}

QTree::~QTree() {
	clear();
}

QTree::QTree(const QTree & other) {
	copy(other);
}


QTree & QTree::operator=(const QTree & rhs) {
	if (this != &rhs) {
		clear();
		copy(rhs);
	}
	return *this;
}


QTree::QTree(PNG & imIn, int leafB, RGBAPixel frameC, bool bal)
	: leafBound(leafB), balanced(bal), drawFrame(true), frameColor(frameC)
{
	im = imIn;
	constructs();
}


QTree::QTree(PNG & imIn, int leafB, bool bal)
	: leafBound(leafB), balanced(bal), drawFrame(false)
{
	frameColor = NULL;
	im = imIn;
	constructs();
}

void QTree::constructs() {
	numLeaf = 0;

	pair<int, int> rootcords;
	rootcords.first = 0;
	rootcords.second = 0;

	int rootsize;
	if (im.width() > im.height()) {
		rootsize = biggestPow2(im.width());
	}
	else {
		rootsize = biggestPow2(im.height());
	}

	root = new Node(im, rootcords, rootsize, NULL, 4);
	numLeaf++;
	varQueue.push(root);

	while (leafBound >= numLeaf + 3 && !varQueue.empty()) {
		Node* mostvar = varQueue.top();
		varQueue.pop();
		if (isLeaf(mostvar) && isSplitableLen(mostvar)) {
			split(mostvar);
			// split handles incrementing numLeaf and pushing new leafs to varQueue
		}
	}

}

bool QTree::compareNode::operator() (Node* a, Node* b) {
	double avar = a->var;
	double bvar = b->var;
	return avar < bvar; // will result in priority queue with max members pop first
}

bool QTree::isSplitableLen(Node* n) {
	return n->size >= 2;
}

bool QTree::isLeaf(Node *t) {

	/* YOUR CODE HERE */
	return t->nw == NULL && t->ne == NULL && t->sw == NULL && t->se == NULL;
}

void QTree::split(Node *t) {
	// responsibility of caller to check (leafBound >= numLeaf + 3) before calling
	// this function blindly splits given node and add it to priority queue

  /* YOUR CODE HERE */
		// part 1
	pair<int, int> nwcords = t->upLeft;
	pair<int, int> necords = nwcords;
	necords.first = nwcords.first + t->size / 2;
	pair<int, int> swcords = nwcords;
	swcords.second = nwcords.second + t->size / 2;
	pair<int, int> secords;
	secords.first = necords.first;
	secords.second = swcords.second;

	Node* nwnew = new Node(im, nwcords, t->size / 2, t, 0);
	Node* nenew = new Node(im, necords, t->size / 2, t, 1);
	Node* swnew = new Node(im, swcords, t->size / 2, t, 2);
	Node* senew = new Node(im, secords, t->size / 2, t, 3);

	t->nw = nwnew;
	t->ne = nenew;
	t->sw = swnew;
	t->se = senew;

	varQueue.push(t->nw);
	varQueue.push(t->ne);
	varQueue.push(t->sw);
	varQueue.push(t->se);

	// update numLeaf
	// lost 1 leaf from given leaf becoming parent
	// gain 4 leafs
	numLeaf += 3;


	if (balanced) {
		// suppose we just split x
		// criteria for picking neighbor node n to split to maintain balance:
		// if x is node, and t == x->parent, and n is some neighbor of x
		// 1. same size as t --> since t just split (was just leaf), n has to be leaf to be same size as t 
		// 2. neighbor of t \
		// 3. neighbor of x --> common neighbor of x and t is whatever child x is of t

		// get all neighbors
		//cout << nwcords.first << "," << nwcords.second << "\n";

		Node* north = NNbr(t->parent);
		Node* south = SNbr(t->parent);
		Node* east = ENbr(t->parent);
		Node* west = WNbr(t->parent);

		int location = t->loc;
		switch (location) {
		case 0: //nw
			splitTwoNodes(north, west);
			break;
		case 1: //ne
			splitTwoNodes(north, east);
			break;
		case 2: //sw 
			splitTwoNodes(south, west);
			break;
		case 3: //se 
			splitTwoNodes(south, east);
			break;
		default:
			break; //root, do nothing
		}

		//cout << "finished balancing \n";
	}
}

void QTree::splitTwoNodes(Node* n, Node* m) {
	if (n != NULL && isLeaf(n) && isSplitableLen(n)) {
		split(n);
	}
	if (m != NULL && isLeaf(m) && isSplitableLen(m)) {
		split(m);
	}
}


/* NNbr(t)
 * return the same-sized quad tree node that is north of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::NNbr(Node *t) {
	/*
	Node* x = t->parent;
	switch (t->loc) {
	case 0: //nw
		switch (x->loc) {
		case 0: //nw
			Node* n = NNbr(x);
			if (n != NULL) {
				return n->sw;
			}
			else {
				return NULL;
			}
		case 1: //ne
			Node* n = NNbr(x);
			if (n != NULL) {
				return n->sw;
			}
			else {
				return NULL;
			}
		case 2: //sw
			return x->parent->nw->sw;
		case 3: //se
			return x->parent->ne->sw;
		default:
			return NULL;
		}
		;
	case 1: //ne
		switch (x->loc) {
		case 0: //nw
			Node* n = NNbr(x);
			if (n != NULL) {
				return n->se;
			}
			else {
				return NULL;
			}
		case 1: //ne
			Node* n = NNbr(x);
			if (n != NULL) {
				return n->se;
			}
			else {
				return NULL;
			}
		case 2: //sw
			return x->parent->nw->se;
		case 3: //se
			return x->parent->ne->se;
		default:
			return NULL;
		}
		;
	case 2: //sw
		return x->nw;
	case 3: //se
		return x->ne;
	default:
		return NULL; //root, do nothing
	}
	*/

	if (t != NULL) {
		Node* x = t->parent;
		switch (t->loc) {
		case 0: //nw
		{
			Node* n = NNbr(x);
			if (n != NULL) {
				return n->sw;
			}
			else {
				return NULL;
			};
		}
		case 1: //ne 
		{
			Node* n = NNbr(x);
			if (n != NULL) {
				return n->se;
			}
			else {
				return NULL;
			};
		}
		case 2: //sw
			return x->nw;
			;
		case 3: //se
			return x->ne;
		default:
			return NULL; //root, do nothing
		}
	}
	else {
		return NULL;
	}
}

/* SNbr(t)
 * return the same-sized quad tree node that is south of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::SNbr(Node *t) {
	/*
	Node* x = t->parent;
	switch (t->loc) {
	case 0: //nw
		return x->sw;
	case 1: //ne
		return x->se;
	case 2: //sw
		switch (x->loc) {
		case 0: //nw
			return x->parent->sw->nw;
		case 1: //ne
			return x->parent->se->nw;
		case 2: //sw
			Node* n = SNbr(x);
			if (n != NULL) {
				return n->nw;
			}
			else {
				return NULL;
			}
		case 3: //se
			Node* n = Sbr(x);
			if (n != NULL) {
				return n->nw;
			}
			else {
				return NULL;
			}
		default:
			return NULL;
		}
		;
	case 3: //se
		switch (x->loc) {
		case 0: //nw
			return x->parent->sw->ne;
		case 1: //ne
			return x->parent->se->ne;
		case 2: //sw
			Node* n = SNbr(x);
			if (n != NULL) {
				return n->ne;
			}
			else {
				return NULL;
			}
		case 3: //se
			Node* n = Sbr(x);
			if (n != NULL) {
				return n->ne;
			}
			else {
				return NULL;
			}
		default:
			return NULL;
		}
		;
	default:
		return NULL; //root, do nothing
	}
	*/

	if (t != NULL) {
		Node* x = t->parent;
		switch (t->loc) {
		case 0: //nw
			return x->sw;
		case 1: //ne
			return x->se;
		case 2: //sw
		{
			Node* n = SNbr(x);
			if (n != NULL) {
				return n->nw;
			}
			else {
				return NULL;
			}
		}
		case 3: //se
		{
			Node* n = SNbr(x);
			if (n != NULL) {
				return n->ne;
			}
			else {
				return NULL;
			}
		}
		default:
			return NULL; //root, do nothing
		}
	}
	else {
		return NULL;
	}
}

/* ENbr(t)
 * return the same-sized quad tree node that is east of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::ENbr(Node *t) {

	if (t != NULL) {
		Node* x = t->parent;
		switch (t->loc) {
		case 0: //nw
			return x->ne;
		case 1: //ne 
		{
			Node* n = ENbr(x);
			if (n != NULL) {
				return n->nw;
			}
			else {
				return NULL;
			};
		}
		case 2: //sw
			return x->se;
			;
		case 3: //se
		{
			Node* n = ENbr(x);
			if (n != NULL) {
				return n->sw;
			}
			else {
				return NULL;
			};
		}
		default:
			return NULL; //root, do nothing
		}
	}
	else {
		return NULL;
	}
}

/* WNbr(t)
 * return the same-sized quad tree node that is west of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::WNbr(Node *t) {
	if (t != NULL) {
		Node* x = t->parent;
		switch (t->loc) {
		case 0: //nw
		{
			Node* n = WNbr(x);
			if (n != NULL) {
				return n->ne;
			}
			else {
				return NULL;
			};
		}
		case 1: //ne 
			return x->nw;
		case 2: //sw
		{
			Node* n = WNbr(x);
			if (n != NULL) {
				return n->se;
			}
			else {
				return NULL;
			};
		}
		case 3: //se
			return x->sw;
		default:
			return NULL; //root, do nothing
		}
	}
	else {
		return NULL;
	}

}

bool QTree::write(string const & fileName) {

	/* YOUR CODE HERE */
	  //make changes to im
	writeRecursively(root);

	// include the following line to write the image to file.
	return(im.writeToFile(fileName));
}

void QTree::writeRecursively(Node* n) {

	if (isLeaf(n)) {
		// draw stuff
		pair<int, int> ul = n->upLeft;
		int xbase = ul.first;
		int ybase = ul.second;
		int len = n->size;
		for (int i = xbase; i < xbase + len; i++) {
			for (int j = ybase; j < ybase + len; j++) {
				RGBAPixel* p = im.getPixel(i, j);
				*p = n->avg;
			}
		}

		if (drawFrame) {
			// draw left and right frame
			for (int j = ybase; j < ybase + len; j++) {
				RGBAPixel* pleft = im.getPixel(xbase, j); //left
				RGBAPixel* pright = im.getPixel(xbase + len - 1, j); //right
				*pleft = frameColor;
				*pright = frameColor;
			}

			// draw top and bottom frame
			for (int i = xbase; i < xbase + len; i++) {
				RGBAPixel* ptop = im.getPixel(i, ybase); //top
				RGBAPixel* pbottom = im.getPixel(i, ybase + len - 1); //bottom
				*ptop = frameColor;
				*pbottom = frameColor;
			}
		}
	}
	else {
		// go to leaf
		writeRecursively(n->nw);
		writeRecursively(n->ne);
		writeRecursively(n->sw);
		writeRecursively(n->se);
	}
}


void QTree::clear() {

	/* YOUR CODE HERE */

	deleteRecursively(root);

}

void QTree::deleteRecursively(Node* n) {
	if (n != NULL) { //use nullptr???
		deleteRecursively(n->nw);
		deleteRecursively(n->ne);
		deleteRecursively(n->sw);
		deleteRecursively(n->se);
		delete n;
	}
}

void QTree::copy(const QTree & orig) {

	/* YOUR CODE HERE */

	numLeaf = orig.numLeaf;
	im = orig.im;
	leafBound = orig.leafBound;
	balanced = orig.balanced;
	drawFrame = orig.drawFrame;
	frameColor = orig.frameColor;

	root = copyRecursively(orig.root);
}

QTree::Node* QTree::copyRecursively(Node* n) {
	if (n != NULL) {
		Node* ret = new Node(im, n->upLeft, n->size, n->parent, n->loc);
		ret->avg = n->avg;
		ret->nw = copyRecursively(n->nw);
		ret->ne = copyRecursively(n->ne);
		ret->sw = copyRecursively(n->sw);
		ret->se = copyRecursively(n->se);
		return ret;
	}
	else {
		return NULL;
	}
}