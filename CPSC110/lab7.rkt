;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname lab7) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(define (all-abstract? p lon)
  (cond [(empty? lon) true]
        [else
         (and (p (first lon))
              (all-abstract? p (rest lon)))]))

(define (all-greater? lon x)
  (local [(define (p n) (> n x))] 
    (all-abstract? p lon)))

(check-expect (all-greater? (list -2 -3 -4) -3) false)