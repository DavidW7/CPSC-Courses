/**
 *
 * Balanced Quad Tree (pa3)
 *
 * This file will be used for grading.
 *
 */

#include "QTree.h"
#include "cassert"
#include <stack>

// Return the biggest power of 2 less than or equal to n
int biggestPow2(int n) {
  if( n < 1 ) return 0;
  int v = 1;
  while( v <= n ) v <<= 1;
  return v >> 1;
}

QTree::Node::Node(PNG & im, pair<int,int> ul, int sz, Node *par)
  :upLeft(ul),size(sz),parent(par),nw(NULL),ne(NULL),sw(NULL),se(NULL)
{
  var = varAndAvg(im,ul,size,avg);
}

QTree::~QTree(){
  clear();
}

QTree::QTree(const QTree & other) {
  copy(other);
}


QTree & QTree::operator=(const QTree & rhs){
  if (this != &rhs) {
    clear();
    copy(rhs);
  }
  return *this;
}

void QTree::createQTree(PNG & imIn) {

  // instantiate the im and new_im
  im = imIn;
  new_im = im;

  // instantiate priority Queue
  auto cmp = [](Node* a, Node* b) { return (a->var) < (b->var); };
  std::priority_queue<Node*, std::vector<Node*>, decltype(cmp)> pQueue(cmp);

  // create the root node
  pair<int,int> root_ul = make_pair(0, 0);
  int root_size = min(biggestPow2(im.width()), biggestPow2(im.height()));
  root = new Node(im, root_ul, root_size, NULL);
  numLeaf = 1;

  pQueue.push(root);

  while (!pQueue.empty() && numLeaf < leafBound) {
    Node* p = pQueue.top();
    pQueue.pop();
    split(p);
    if (p->nw) {
      pQueue.push(p->nw);
      pQueue.push(p->ne);
      pQueue.push(p->sw);
      pQueue.push(p->se);
    }
  }    
}

QTree::QTree(PNG & imIn, int leafB, RGBAPixel frameC, bool bal)
  : leafBound(leafB), balanced(bal), drawFrame(true), frameColor(frameC)
{

  /* YOUR CODE HERE */
  createQTree(imIn);
}


QTree::QTree(PNG & imIn, int leafB, bool bal)
  : leafBound(leafB), balanced(bal), drawFrame(false)
{

  /* YOUR CODE HERE */
  createQTree(imIn);
}


bool QTree::isLeaf( Node *t ) {
  /* YOUR CODE HERE */
  // if node exists and doesn't have childer
  // then it is a leaf
  return t && !t->nw;
}
  
void QTree::createChildren(Node * t) {
    
    int parent_size = t->size;
    int children_size = parent_size / 2;
    pair<int, int> parent_ul = t->upLeft;

    // Creating the children by splitting the parent
    // nw child
    pair<int, int> nw_ul = parent_ul;
    Node* nw_child = new Node(im, nw_ul, children_size, t);
    t->nw = nw_child;

    // ne child
    pair<int, int> ne_ul = make_pair(parent_ul.first + children_size, parent_ul.second);
    Node* ne_child = new Node(im, ne_ul, children_size, t);
    t->ne = ne_child;
    
    // sw child
    pair<int, int> sw_ul = make_pair(parent_ul.first, parent_ul.second + children_size);
    Node* sw_child = new Node(im, sw_ul, children_size, t);
    t->sw = sw_child;

    // se child
    pair<int, int> se_ul = make_pair(parent_ul.first + children_size, parent_ul.second + children_size);
    Node* se_child = new Node(im, se_ul, children_size, t);
    t->se = se_child;
}

void QTree::split( Node *t ) {
  
  /* YOUR CODE HERE */

  // FOR BALANCED QTREES-------------------------------------------------
  // A split might cause one or two nbrs of the parent of t to split
  // to maintain balance.  Note that these two nbrs exist (unless they're
  // not in the image region) because the current set of leaves are
  // balanced.
  // if( t is a NW (or NE or SW or SE) child ) then we need to check that
  // the North and West (or North and East or South and West or
  // South and East) nbrs of t->parent have children. If they don't
  // we need to split them.

  if (t && t->size > 1 && isLeaf(t)){
    if (balanced) {
      createChildren(t);
      splitNeighboursToMaintainBalance(t);
    } else {
      createChildren(t);
    }
    // root is no longer a leaf, and adding 4 children nodes
    numLeaf += 3;
  }
}

void QTree::splitNeighboursToMaintainBalance(Node * p) {
  // Recursively splits all the neighbours that needs to be split
  // For all p's neighbour that's a leaf 
  // and twice p's size, split that neightbour,
  // and check if the neighbour's neighbours now needs to be split
  Node * nnbr = NNbr(p);
  Node * snbr = SNbr(p);
  Node * enbr = ENbr(p);
  Node * wnbr = WNbr(p);
  if (nnbr && isLeaf(nnbr) && nnbr->size >= p->size * 2) {
    split(nnbr);
    splitNeighboursToMaintainBalance(nnbr);
  }
  if (snbr && isLeaf(snbr) && snbr->size >= p->size * 2) {
    split(snbr);
    splitNeighboursToMaintainBalance(snbr);
  }
  if (enbr && isLeaf(enbr) && enbr->size >= p->size * 2) {
    split(enbr);
    splitNeighboursToMaintainBalance(enbr);
  }
  if (wnbr && isLeaf(wnbr) && wnbr->size >= p->size * 2) {
    split(wnbr);
    splitNeighboursToMaintainBalance(wnbr);
   }    
} 


/* NNbr(t)
 * return the same-sized quad tree node that is north of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::NNbr(Node *t) {

  /* YOUR CODE HERE */
  /* 

  If the node is a south child, the go to its parent and come down on the corresponding north child, 
  else keep traversing up its ancestors until it reaches a node that's a south child. (each step push the node to a stack) 
  then based on what direction that south node is, come down on its north neighbour (pop a node off stack and go corresponding north direction), 
  repeat this until a leaf is found, and that leaf is the north neighbour of the original node...
  When traversing up the nodes, if none of the nodes are a south child, then return nullptr... 
  
  */

  // Neighbours share a side, and is a leaf and size is same, double or half
  if (t && t->parent) {
    if (t == t->parent->sw) 
      return t->parent->nw;
    else if (t == t->parent->se)
      return t->parent->ne;
    else {
      // while loop and use a stack?
      std::stack<Node*> s;
      Node* n = t;
      Node* l;
      while (n->parent && (n != n->parent->sw && n != n->parent->se)) {
        s.push(n);
        n = n->parent;
      }
      if (!n->parent) return nullptr;
      s.push(n);
      n = n->parent;
      while (!isLeaf(n) && !s.empty()) {
        l = s.top();
        s.pop();
        if (l == l->parent->sw) {
          n = n->nw;
        } else if (l == l->parent->se) {
          n = n->ne;
        } else if (l == l->parent->nw) {
          n = n->sw;
        } else if (l == l->parent->ne) {
          n = n->se;
        }
      }
      return n;
    }
  } 
  else return nullptr;
}

/* SNbr(t)
 * return the same-sized quad tree node that is south of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::SNbr(Node *t) {

  /* YOUR CODE HERE */

  if (t && t->parent) {
    if (t == t->parent->nw) 
      return t->parent->sw;
    else if (t == t->parent->ne)
      return t->parent->se;
    else {
      // while loop and use a stack?
      std::stack<Node*> s;
      Node* n = t;
      Node* l;
      while (n->parent && (n != n->parent->nw && n != n->parent->ne)) {
        s.push(n);
        n = n->parent;
      }
      if (!n->parent) return nullptr;
      s.push(n);
      n = n->parent;
      while (!isLeaf(n) && !s.empty()) {
        l = s.top();
        s.pop();
        if (l == l->parent->sw) {
          n = n->nw;
        } else if (l == l->parent->se) {
          n = n->ne;
        } else if (l == l->parent->nw) {
          n = n->sw;
        } else if (l == l->parent->ne) {
          n = n->se;
        }
      }
      return n;
    }
  } 
  else return nullptr;
  
}

/* ENbr(t)
 * return the same-sized quad tree node that is east of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::ENbr(Node *t) {

  /* YOUR CODE HERE */
  if (t && t->parent) {
    if (t == t->parent->nw) 
      return t->parent->ne;
    else if (t == t->parent->sw)
      return t->parent->se;
    else {
      std::stack<Node*> s;
      Node* n = t;
      Node* l;
      while (n->parent && (n != n->parent->nw && n != n->parent->sw)) {
        s.push(n);
        n = n->parent;
      }
      if (!n->parent) return nullptr;
      s.push(n);
      n = n->parent;
      while (!isLeaf(n) && !s.empty()) {
        l = s.top();
        s.pop();
        if (l == l->parent->sw) {
          n = n->se;
        } else if (l == l->parent->se) {
          n = n->sw;
        } else if (l == l->parent->nw) {
          n = n->ne;
        } else if (l == l->parent->ne) {
          n = n->nw;
        }
      }
      return n;
    }
  } 
  else return nullptr;
}

/* WNbr(t)
 * return the same-sized quad tree node that is west of Node t.
 * return NULL if this node is not in the QTree.
 */
QTree::Node * QTree::WNbr(Node *t) {

  /* YOUR CODE HERE */
  if (t && t->parent) {
    if (t == t->parent->ne) 
      return t->parent->nw;
    else if (t == t->parent->se)
      return t->parent->sw;
    else {
      std::stack<Node*> s;
      Node* n = t;
      Node* l;
      while (n->parent && (n != n->parent->ne && n != n->parent->se)) {
        s.push(n);
        n = n->parent;
      }
      if (!n->parent) return nullptr;
      s.push(n);
      n = n->parent;
      while (!isLeaf(n) && !s.empty()) {
        l = s.top();
        s.pop();
        if (l == l->parent->sw) {
          n = n->se;
        } else if (l == l->parent->se) {
          n = n->sw;
        } else if (l == l->parent->nw) {
          n = n->ne;
        } else if (l == l->parent->ne) {
          n = n->nw;
        }
      }
      return n;
    }
  } 
  else return nullptr;  
}


/**
 * Write renders a PNG image by coloring one square for each leaf
 * (leaf.size X leaf.size pixels with origin leaf.upLeft) of the
 * QTree using the leaf's average color for the entire square.
 * If the QTree has a frameColor, the 1-pixel border in each leaf
 * square is colored with the frameColor. 
 * It then writes the image to file fileName and 
 * returns any error returned by the write to file.
 * return(im.writeToFile(fileName))
 * You may want a recursive helper function for this one.
 */
bool QTree::write(string const & fileName){

  /* YOUR CODE HERE */
  traverseTreeAndColor(root);

  // include the following line to write the image to file.
  return(new_im.writeToFile(fileName));

  }

void QTree::traverseTreeAndColor(Node * t) {

  // Guard against NULL t, width, and height
  if (t && new_im.width() && new_im.height()) {
    // color the squares if leaf, else just recurse on its children
    if (isLeaf(t)) {
      // color the image corresponding to 
      // the leaf's avg color and coordinate
      int ul_x = t->upLeft.first;
      int ul_y = t->upLeft.second;
      int dr_x = ul_x + t->size;         // right border x
      int dr_y = ul_y + t->size;         // down border y
      for (int x = ul_x; x < dr_x; x++) {
        for (int y = ul_y; y < dr_y; y++) {
          RGBAPixel* pxlToColor = new_im.getPixel(x, y);
          // if drawFrame is true and pixel is at any border
          // then color pixel with frameColor, otherwise color with avg
          if (drawFrame && (x == ul_x || x == dr_x-1 || y == ul_y || y == dr_y-1)) {
            *pxlToColor = frameColor;
          } else {
            *pxlToColor = t->avg;
          }
        }
      } 
    } else {
      if (t->nw) {
        traverseTreeAndColor(t->nw);
        traverseTreeAndColor(t->ne);
        traverseTreeAndColor(t->sw);
        traverseTreeAndColor(t->se);
      }
    }
  }
}

void QTree::deleteQTree(Node * t) {
  if (t) {
    deleteQTree(t->nw);
    deleteQTree(t->ne);
    deleteQTree(t->sw);
    deleteQTree(t->se);
    delete t;
    t = NULL;
  } 
}

void QTree::clear() {

  /* YOUR CODE HERE */
  deleteQTree(root);
}

void QTree::copy(const QTree & orig) {

  /* YOUR CODE HERE */
  im = orig.im;
  leafBound = orig.leafBound;
  balanced = orig.balanced;
  drawFrame = orig.drawFrame;
  frameColor = orig.frameColor;
  createQTree(im);
}
