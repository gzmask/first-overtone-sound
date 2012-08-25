(use 'overtone.live)
(definst bar [freq 420] (saw freq))
(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
       (sin-osc freq)
            vol))

(definst spooky-house [freq 440 width 0.2 
                       attack 0.3 sustain 4 release 0.3 
                       vol 0.4] 
(* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
   (sin-osc (+ freq (* 20 (lf-pulse:kr 0.5 0 width))))
   vol))

(definst deng [freq 220]
   (pluck (* (white-noise) (env-gen (perc 0.001 2) :action FREE)) 1 3 (/ 1 freq)))
