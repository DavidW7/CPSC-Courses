;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname lec22-maze-4-way-solution) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
(require spd/tags)
;; Solve simple square mazes

;; Data definitions:

(@HtDD Maze)
;; Maze is (listof Boolean)
;; interp. a square maze
;;         each side length is (sqrt (length <maze>))
;;         true  (aka #t) means open, can move through this
;;         false (aka #f) means a wall, cannot move into or through a wall
;;

(define O #t) ;Open
(define W #f) ;Wall

(define M1
  (list O W W W W
        O O W O O
        W O W W W 
        O O W W W
        O O O O O))

(define M2
  (list O O O O O
        O W W W O
        O W W W O
        O W W W O
        O W W W O))

(define M3             ;forces backtracking in this solver
  (list O O O O O
        O W W W W
        O W W W W
        O W W W W 
        O O O O O))

(define M4
  (list O O O O O
        O W W W O
        O W O O O
        O W O W W
        W W O O O))


(define M5
  (list O O O O O
        O W O W O
        O O O O O
        O W O W O
        W O O W W))


(define M6
  (list O O O O O
        O W W W O
        O W O O O
        O W O W W
        W O O O O))

(@HtDD Pos)
(define-struct pos (x y))
;; Pos is (make-pos Integer Integer)
;; interp. an x, y position in the maze.
;;         0, 0 is upper left.
;;         the SIZE of a maze is (sqrt (length m))
;;         a position is only valid for a given maze if:
;;            - (<= 0 x (sub1 SIZE))
;;            - (<= 0 y (sub1 SIZE))
;;            - there is a true in the given cell
;;                         ;in a 5x5 maze:
(define P0 (make-pos 0 0)) ;upper left
(define P1 (make-pos 4 0)) ;upper right
(define P2 (make-pos 0 4)) ;lower left
(define P3 (make-pos 4 4)) ;lower right








;; Functions

(@HtDF solve)
;; Maze -> Boolean
;; produce true if maze is solvable, false otherwise
;; assume maze has a true at least in the upper left
(check-expect (solve M1) #t)
(check-expect (solve M2) #t)
(check-expect (solve M3) #t) 
(check-expect (solve M4) #t)
(check-expect (solve M5) #f)
(check-expect (solve M6) #t)

(@template encapsulated backtracking genrec arb-tree
           accumulator) ;path
(define (solve m)
  ;; path is (listof Pos); all the positions visited on this path
  ;; initialize, preserve, exploit
  (local [(define R (sqrt (length m)))
          
          (define (fn-for-pos p path)
            (cond [(solved? p)     true]  ;yay!
                  [(member p path) false] ;going in circles!
                  [else
                   (fn-for-lop (next-positions p)
                               (cons p path))]))

          (define (fn-for-lop lop path)
            (cond [(empty? lop) false]
                  [else
                   (local [(define try (fn-for-pos (first lop) path))]
                     (if (not (false? try))
                         try
                         (fn-for-lop (rest lop) path)))]))

          ;; Pos -> (listof Pos)
          ;; produce next possible valid positions from p
          (define (next-positions p)
            (local [(define x (pos-x p))
                    (define y (pos-y p))]
              (filter valid?
                      (list (make-pos x (sub1 y))      ;up
                            (make-pos x (add1 y))      ;down
                            (make-pos (add1 x) y)      ;right
                            (make-pos (sub1 x) y)))))  ;left

          ;; produce true if p within maze and not a wall
          (define (valid? p)
            (local [(define x (pos-x p))
                    (define y (pos-y p))]
              (and (<= 0 x (sub1 R))
                   (<= 0 y (sub1 R))
                   (maze-ref m p))))

          ;; Pos -> Boolean
          ;; produce true if p is lower right corner
          (define (solved? p)
            (= (pos-x p)
               (pos-y p)
               (sub1 R)))

          ;; Maze Pos -> Boolean
          ;; produce contents of map at location p
          (define (maze-ref m p)
            (list-ref m (+ (pos-x p) (* R (pos-y p)))))]
    
    (fn-for-pos (make-pos 0 0) empty)))