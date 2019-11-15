/**
 * @file deque.cpp
 * Implementation of the Deque class.
 *
 */

 /* No argument constructor */
template <class T>
Deque<T>::Deque() :n1(0), n2(-1) {}

// n1 = L = current left most 
// n2 = current right most


/**
 * Adds the parameter object to the right of the Deque.
 *
 * @param newItem object to be added to the Deque.
 */
template <class T>
void Deque<T>::pushR(T newItem)
{
	n2 = n2 + 1;
	int temp = data.size();
	if (n2 >= temp) {
		data.push_back(newItem);
	}
	else {
		data[n2] = newItem;
	}
	//cout << "pushR" << newItem << "..." << n1 << "..." << n2 << endl;
	/*
	for (T x : data) {
		cout << x;
	}
	cout << endl;
	*/
}



/**
 * Removes the object at the left of the Deque, and returns it to the
 * caller.
 *
 * See .h file for implementation notes.
 *
 * @return The item that used to be at the left of the Deque.
 */
template <class T>
T Deque<T>::popL()
{
	T tempy = data[n1];
	n1 += 1;

	if (n1 >= n2 - n1 + 1) {
		//2n = n
		if (isEmpty()) {
			n2 = -1;
			n1 = 0;
			data.clear();
		}
		else {
			typename vector<T>::const_iterator firsty = data.begin() + n1;
			typename vector<T>::const_iterator lasty = data.begin() + n2 + 1;
			vector<T> temp(firsty, lasty);
			data.clear();
			data = temp;
			n1 = 0;
			n2 = data.size() - 1;
		}
	}

	data.shrink_to_fit();
	return tempy;
}



/**
 * Removes the object at the right of the Deque, and returns it to the
 * caller.
 *
 * @return The item that used to be at the right of the Deque.
 */
template <class T>
T Deque<T>::popR()
{
	T tempy = data[n2];
	n2 -= 1;

	//if (n1 >= n2 - n1 + 1) {
		//2n = n
		if (isEmpty()) {
			n2 = -1;
			n1 = 0;
			data.clear();
		}
		else {
			typename vector<T>::const_iterator firsty = data.begin() + n1;
			typename vector<T>::const_iterator lasty = data.begin() + n2 + 1;
			vector<T> temp(firsty, lasty);
			data.clear();
			data = temp;
			n1 = 0;
			n2 = data.size() - 1;
		}
	//}
	data.shrink_to_fit();
	return tempy;
}



/**
 * Finds the object at the left of the Deque, and returns it to the
 * caller. Unlike popL(), this operation does not alter the deque.
 *
 * @return The item at the front of the deque.
 */
template <class T>
T Deque<T>::peekL()
{
	return data[n1];
}



/**
 * Finds the object at the right of the Deque, and returns it to the
 * caller. Unlike popR(), this operation does not alter the deque.
 *
 * @return the value of The item at the right of the deque.
 */
template <class T>
T Deque<T>::peekR()
{
	return data[n2];
}



/**
 * Determines if the Deque is empty.
 *
 * @return bool which is true if the Deque is empty, false otherwise.
 */
template <class T>
bool Deque<T>::isEmpty() const
{
	return !(n2 - n1 >= 0);
}

