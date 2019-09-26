#include "grid.h"
#include "grid_given.cpp"

// PA1 functions


/**
 * Destroys the current Grid. This function should ensure that
 * memory does not leak on destruction of a grid.
 */
Grid::~Grid(){ /*your code here*/
	clear();
}

/**
 * Rotate row r (row 0 is the first) by count positions.
 * If row r is ABCDE (left to right) in grid g then row r
 * in grid g should be DEABC after the call g.rotateR(r, 2).
 * Rotate headOfCol_ if necessary.
 */
void Grid::rotateR(int r, int count) { /* your code here */
	//cout << "a" << '\n';
	Node *rowToRot = headOfRow_[r];
	//cout << "b" << '\n';
	for (int i = 0; i < count; i++) {
		//cout << "cc" << '\n';
		rowToRot = rotRightOnce(rowToRot, (int)headOfCol_.size());
		//cout << "c" << '\n';
	}
	//cout << "d" << '\n';
	headOfRow_[r] = rowToRot;
	//cout << "e" << '\n';
	if (r == 0) {
		for (int i = 0; i < (int) headOfCol_.size(); i++) {
			//cout << "f" << '\n';
			headOfCol_[i] = rowToRot;
			rowToRot = rowToRot->right;
			//cout << "g" << '\n';
		}
	}
}

//suppose
// VWXYZ
// ABCDE
// IJKLM

Grid::Node *Grid::rotRightOnce(Node *head, int accu) {
	//cout << accu << '\n';
	if (accu != 0) {
		//cout << "rec \n";
		Node *temp = rotRightOnce(head->right, accu - 1); //temp = E
		Node *tl = head->left; //NULL
		Node *tu = head->up; //V
		Node *td = head->down; //I
		head->left = temp; //E
		head->right = temp->right; //B
		head->up = temp->up; //W
		head->down = temp->down; //J
		temp->left = tl; //NULL
		temp->right = head; //A
		temp->up = tu; //V 
		temp->down = td; //I
		head = temp;
	}
	else {
		//cout << "base \n";
		Node *tu = head->up; //Z 
		Node *td = head->down; //M
		Node *toRot = head->left; //D
		Node *tr = head->right;
		head->left = toRot->left; //C
		head->right = toRot; //D
		head->down = toRot->down; //L
		head->up = toRot->up; //Y
		toRot->left = head; //E
		toRot->right = tr; //NULL
		toRot->down = td; //M
		toRot->up = tu; //Z
		head = toRot;
	}
	return head;
}

/**
 * Rotate column c (column 0 is the first) by count positions.
 * If column c is ABCDE (top to bottom) in grid g then column c
 * in grid g should be DEABC after the call g.rotateC(c, 2). 
 * Rotate headOfRow_ if necessary.
 */

void Grid::rotateC(int c, int count) { /* your code here */
	cout << "yes " << c << ' ' << count << '\n';
	cout << headOfCol_.size() << '\n';
	Node *colToRot = headOfCol_[c];
	for (int i = 0; i < count; i++) {
		cout << (int)headOfRow_.size() << '\n';
		colToRot = rotDownOnce(colToRot, (int)headOfRow_.size());
	}
	headOfCol_[c] = colToRot;
	if (c == 0) {
		for (int i = 0; i < (int) headOfRow_.size(); i++) {
			headOfRow_[i] = colToRot;
			colToRot = colToRot->down;
		}
	}
}

//suppose
// VWXYZ
// ABCDE
// IJKLM

Grid::Node * Grid::rotDownOnce(Node *head, int accu) {
	//cout << accu << '\n';
	//todo logic
	//currently outputs the samee thing no matter what the input is. strange.
	if (accu != 0) {
		//cout << "rec \n";
		Node *temp = rotDownOnce(head->up, accu - 1);
		Node *tu = head->up; //NULL
		Node *tl = head->left; //V
		Node *tr = head->right; //X
		head->down = temp->down; //B
		head->up = temp; //J
		head->left = temp->left; //A
		head->right = temp->right; //B
		temp->down = head; //W
		temp->up = tu; //NULL
		temp->left = tl; //V 
		temp->right = tr; //X
		head = temp;
	}
	else {
		//cout << "base \n";
		Node *tl = head->left; //I
		Node *tr = head->right; //K
		Node *toRot = head->up; //B
		Node *td = head->down;
		head->left = toRot->left; //A
		head->right = toRot->right; //C
		head->up = toRot->up; //W
		head->down = toRot; //B
		toRot->down = td; //ULL
		toRot->left = tl; //I
		toRot->right = tr; //K
		toRot->up = head; //J
		head = toRot;
	}
	return head;
}

/**
 * Destroys all dynamically allocated memory associated with the
 * current Grid class. Clears headOfRow_ and headOfCol_ vectors.
 * Sets bwidth_, bheight_ to zero.
 * After clear() the grid represents an empty grid.
 */
void Grid::clear() { /*your code here*/
	Node *temp_curr;
	Node *temp_next;
	for (int j = 0; j < (int) headOfRow_.size(); j++) {
		temp_curr = headOfRow_[j];
		headOfRow_[j] = NULL;
		for (int i = 0; i < (int) headOfCol_.size(); i++) {
			temp_next = temp_curr->right; //will be null for last (aka most rightmost) node
			//delete temp_curr->block;
			delete temp_curr;
			temp_curr = temp_next;
			headOfCol_[i] = NULL;
		}
	}
	//delete headOfRow_;
	//delete headOfCol_;
	//headOfRow_ = new std::vector<Node*>;
	//headOfCol_ = new std::vector<Node*>;
	bheight_ = 0;
	bwidth_ = 0;
}


/**
 * Makes the current Grid a copy of the "other" Grid.
 * The dimensions should be the same.  The Nodes should
 * contain the same blocks, but the Nodes should be newly
 * allocated. This function is used in both the copy
 * constructor and the assignment operator for Grids.
 */
void Grid::copy(Grid const& other) { /*your code here*/
	cout << "a \n";
	clear();
	cout << "b \n";
	int numRows = other.numRows();
	cout << numRows << '\n';
	int numCols = other.numCols();
	bheight_ = other.bheight();
	bwidth_ = other.bwidth();

	vector<vector<Node*>> nodes(numRows, vector<Node*>(numCols, NULL));
	//to iterate over rows
	cout << "c \n";
	cout << "rows" << nodes.size() << '\n';
	cout << "cols" << nodes[0].size() << '\n';

	for (int j = 0; j < numRows; j++) {
		cout << "-> " << j << '\n';
		Node *nodeToCopy = other.headOfRow(j);
		for (int i = 0; i < numCols; i++) {
			cout << i << '\n';
			nodes[j][i] = new Node(nodeToCopy->block);
			nodeToCopy = nodeToCopy->right;
		}
	}
	cout << "d \n";
	//numRows = num of items in one col
	//numCols = num of items in one row

	for (int j = 0; j < numRows; j++) {
		cout << "->" << j << '\n';
		for (int i = 0; i < numCols; i++) {
			cout << i << '\n';
			//todo logic
			//doesnt connect things to the right stuff for some reason
			if (i == 0) {
				cout << "aaa" << '\n';
				nodes[j][i]->left = nodes[j][numRows - 1];
				nodes[j][i]->right = nodes[j][i + 1];
			}
			else if (i == numRows - 1) {
				nodes[j][i]->left = nodes[j][i - 1];
				nodes[j][i]->right = nodes[j][0];
			}
			else {
				nodes[j][i]->left = nodes[j][i - 1];
				nodes[j][i]->right = nodes[j][i + 1];
			}

			if (j == 0) {
				cout << "bbb" << '\n';
				nodes[j][i]->up = nodes[numCols - 1][i];
				nodes[j][i]->down = nodes[j + 1][i];
			}
			else if (j == numCols - 1) {
				nodes[j][i]->up = nodes[j - 1][i];
				nodes[j][i]->down = nodes[0][i];
			}
			else {
				nodes[j][i]->up = nodes[j - 1][i];
				nodes[j][i]->down = nodes[j + 1][i];
			}

		}
	}

	headOfCol_.clear();
	headOfRow_.clear();
	headOfCol_ = nodes[0];
	for (int j = 0; j < numRows; j++) {
		headOfRow_[j] = nodes[j][0];
	}

}

Grid::Node * Grid::headOfRow(int r) const {
	int max = headOfRow_.size() - 1;
	if (r < 0 || r > max) {
		cout << "accessed row head is out of bounds" << '\n';
		return nullptr;
	}
	else {
		return headOfRow_[r];
	}
}

Grid::Node *Grid::headOfCol(int c) const {
	int max = headOfCol_.size() - 1;
	if (c < 0 || c > max) {
		cout << "accessed col head is out of bounds" << '\n';
		return nullptr;
	}
	else {
		return headOfCol_[c];
	}
}

