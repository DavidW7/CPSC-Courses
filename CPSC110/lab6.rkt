;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname lab6) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require spd/tags)
(require 2htdp/image)

;; Constants:
(define TEXT-SIZ 20)
(define TEXT-COL "black")

(@HtDD Phrase)
(define-struct phrase (pf lop))
;; Phrase is (make-phrase String ListOfPhrase)
;; interp. a prefix in a sentence, and list of phrase that can follow it
(define P1-1-1 (make-phrase "you mean it" empty))
(define P1-1-2 (make-phrase "you kiss her" empty))
(define P1-1-3 (make-phrase "crazy" empty))
(define P1-1 (make-phrase "like" (list P1-1-1 P1-1-2 P1-1-3)))
(define P1-2 (make-phrase "now" empty))
(define P1 (make-phrase "Kiss me" (list P1-1 P1-2)))

(@dd-template-rules compound
                    atomic-non-distinct
                    ref
                    )
#;
(define (fn-for-phrase p)
  (... (phrase-pf p)
       (fn-for-lop (phrase-lop p))))

(@HtDD ListOfPhrase)
;; ListOfPhrase is one of:
;; -empty
;; -(cons Phrase ListOfPhrase)
;; interp. list of phrase
(define LOP0 empty)
(define LOP1 (list P1-1 P1-2))
(define LOP1-1 (list P1-1-1 P1-1-2 P1-1-3))

(@dd-template-rules one-of
                    atomic-distinct
                    compound
                    ref
                    self-ref
                    )
#;
(define (fn-for-lop lop)
  (cond
    [(empty? lop) (...)]
    [else
     (... (fn-for-phrase (first lop))
          (fn-for-lop (rest lop)))]))

(@HtDF sentence-count)
;; Phrase -> Natural
;; count number of full sentences (end nodes) in a given phrase
(check-expect (sentence-count (make-phrase "" empty)) 1);base case
(check-expect (sentence-count P1) 4)
(check-expect (sentence-count P1-1) 3)

;(define (sentence-count p) 0);stub

(@template Phrase ListOfPhrase encapsulated)
(define (sentence-count p)
  (local
    [(define (fn-for-phrase p)
       (local [(define get-count (fn-for-lop (phrase-lop p)))]
         (if (zero? get-count)
             (add1 get-count)
             get-count)))
     (define (fn-for-lop lop)
       (cond
         [(empty? lop) 0]
         [else
          (+ (fn-for-phrase (first lop))
             (fn-for-lop (rest lop)))]))
     ]
    (fn-for-phrase p)))

(@HtDF render-sentence)
;; Phrase -> Image
;; render phrase in a tree like image
(check-expect (render-sentence P1-1-1)
              (text (phrase-pf P1-1-1) TEXT-SIZ TEXT-COL))
(check-expect (render-sentence P1)
              (beside  (text (phrase-pf P1) TEXT-SIZ TEXT-COL)
                       (above/align "left"
                                    (beside  (text (phrase-pf P1-1) TEXT-SIZ TEXT-COL)
                                             (above/align "left"
                                                          (text (phrase-pf P1-1-1) TEXT-SIZ TEXT-COL)
                                                          (text (phrase-pf P1-1-2) TEXT-SIZ TEXT-COL)
                                                          (text (phrase-pf P1-1-3) TEXT-SIZ TEXT-COL)))
                                    (text (phrase-pf P1-2) TEXT-SIZ TEXT-COL))))

;(define (render-sentence p) empty-image);stub

(@template Phrase ListOfPhrase encapsulated)
(define (render-sentence p)
  (local [(define (render-str s) (text s TEXT-SIZ TEXT-COL))
          (define (fn-for-phrase p)
            (beside (render-str (phrase-pf p))
                    (fn-for-lop (phrase-lop p))))
          (define (fn-for-lop lop)
            (cond
              [(empty? lop) empty-image]
              [else
               (above/align "left"
                            (fn-for-phrase (first lop))
                            (fn-for-lop (rest lop)))]))]
    (fn-for-phrase p)))