/**
 * @file quackfun.cpp
 * This is where you will implement the required functions for the
 *  stacks and queues portion of the lab.
 */

namespace QuackFun {

    /**
     * Sums items in a stack.
     * @param s A stack holding values to sum.
     * @return The sum of all the elements in the stack, leaving the original
     *  stack in the same state (unchanged).
     *
     * @note You may modify the stack as long as you restore it to its original
     *  values.
     * @note You may use only two local variables of type T in your function.
     *  Note that this function is templatized on the stack's type, so stacks of
     *  objects overloading the + operator can be summed.
     * @note We are using the Standard Template Library (STL) stack in this
     *  problem. Its pop function works a bit differently from the stack we
     *  built. Try searching for "stl stack" to learn how to use it.
     * @hint Think recursively!
     */
    template <typename T>
    T sum(stack<T>& s)
    {
		T temp = s.top();
		s.pop();
		if (s.empty()) {
			s.push(temp);
			return temp;
		}
		else {
			T total = sum(s);
			s.push(temp);
			return total + temp;
		}
    }

    /**
     * Reverses even sized blocks of items in the queue. Blocks start at size
     * one and increase for each subsequent block.
     * @param q A queue of items to be scrambled
     *
     * @note Any "leftover" numbers should be handled as if their block was
     *  complete.
     * @note We are using the Standard Template Library (STL) queue in this
     *  problem. Its pop function works a bit differently from the stack we
     *  built. Try searching for "stl stack" to learn how to use it.
     * @hint You'll want to make a local stack variable.
     */
    template <typename T>
    void scramble(queue<T>& q)
    {
		queue<T> tempqueue;
		stack<T> tempstack;

		queue<T> q2;

		int state = 1; //state 1,2,3,4,5
		int statetemp = 0;

		while (q.empty() == false) {
			statetemp = 0;
			//pop appropriate amount of elements
			for (int i = 0; i < state; i++) {
				if (q.empty() == false) {
					tempqueue.push(q.front());
					q.pop();
					statetemp++;
				}
			}

			//
			if (state % 2 == 0) {
				int temppy = statetemp;
				while (temppy > 0) {
					//reverse order using stack
					tempstack.push(tempqueue.front());
					tempqueue.pop();

					temppy--;
				}
				temppy = statetemp;
				while (temppy > 0) {
					//push back to q2
					q2.push(tempstack.top());
					tempstack.pop();

					temppy--;
				}
			}
			else {
				while (statetemp > 0) {
					//push without reversing to q2
					q2.push(tempqueue.front());
					tempqueue.pop();

					statetemp--;
				}
			}
			
			state++;
		}

		while (q2.empty() == false) {
			q.push(q2.front());
			q2.pop();
		}

		std::cout << "done\n";
    }

    /**
     * @return true if the parameter stack and queue contain only elements of
     *  exactly the same values in exactly the same order; false, otherwise.
     *
     * @note You may assume the stack and queue contain the same number of items!
     * @note There are restrictions for writing this function.
     * - Your function may not use any loops
     * - In your function you may only declare ONE local boolean variable to use in
     *   your return statement, and you may only declare TWO local variables of
     *   parametrized type T to use however you wish.
     * - No other local variables can be used.
     * - After execution of verifySame, the stack and queue must be unchanged. Be
     *   sure to comment your code VERY well.
     */
	//assume unique items in queue
    template <typename T>
    bool verifySame(stack<T>& s, queue<T>& q)
    {
		T temp1 = s.top(); 
		T temp2 = q.front();
		
		s.pop();

		if (s.empty()) {
			s.push(temp1);
			q.push(temp2);
			q.pop();
			return temp1 == temp2;
		}
		else {
			bool ret = verifySame(s, q);
			temp2 = q.front();
			s.push(temp1);
			q.push(temp2);
			q.pop();
			return ret && temp1 == temp2;
		}
    }
}
