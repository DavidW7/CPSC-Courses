;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-intermediate-reader.ss" "lang")((modname asset-v1_UBC+CPSC110-911+2018S+type@asset+block@graded-10-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f () #f)))
(require spd/tags)
(@HtDD StaffMember JobTitle)
(define-struct staff (name job manages))
;; StaffMember is (make-staff String JobTitle (listof StaffMember)
;; interp. a course staff member: a name,
;;                                a job title, and
;;                                a list of other staff members who are managed
;;                                  by this person.

;; JobTitle is one of:
;;  - "TA"
;;  - "Head Grader"
;;  - "Professor"
;;  - "Chair"
;; interp. a job title within the CPSC 110 staff

(define TA1 (make-staff "Fred" "TA" empty))
(define TA2 (make-staff "George" "TA" empty))
(define TA3 (make-staff "Bill" "TA" empty))
(define HTA1 (make-staff "Percy" "Head Grader" (list TA1 TA2)))
(define P1 (make-staff "Flitwick" "Professor" (list TA1 TA3)))
(define P2 (make-staff "Snape" "Professor" (list TA2 TA3)))
(define CC (make-staff "Dumbledore" "Chair" (list P1 P2 HTA1)))

(define (fn-for-staff-member s)
  (local [(define (fn-for-staff-member s)
            (... (staff-name s)
                 (fn-for-job-title (staff-job s))
                 (fn-for-losm (staff-manages s))))

          (define (fn-for-job-title j)
            (cond [(string=? j "TA") (...)]
                  [(string=? j "Head Grader") (...)]
                  [(string=? j "Professor") (...)]
                  [(string=? j "Chair") (...)]))

          (define (fn-for-losm losm)
            (cond [(empty? losm) (...)]
                  [else (... (fn-for-staff-member (first lom))
                             (fn-for-losm (rest losm)))]))]
    (fn-for-staff-member s)))

(@HtDF manage?)
;; StaffMember String String -> Boolean
;; Searching in staff member, true if name1 managed name2, directly or not


(@template StaffMember
           add-param)
(define (manage? s n1 n2)
  (local [(define (fn-for-staff-member s lon)
            (if (and (string=? (staff-name s) n2)
                     (not (false? (member n1 lon))))
                true
                (fn-for-losm (staff-manages s) (cons (staff-name s) lon))))

          (define (fn-for-losm losm lon)
            (cond [(empty? losm) false]
                  [else
                   (local [(define try (fn-for-staff-member (first losm) lon))]
                     (if (not (false? try))
                         try
                         (fn-for-losm (rest losm) lon)))]))]
    (fn-for-staff-member s empty)))
(manage? CC "Dumbledore" "Flitwick")