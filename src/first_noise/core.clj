(ns first_noise.core
  (:use
    [overtone.live]))


;try some sins
(definst foo [] (saw 440))
;(foo)

(definst baz [freq 440] (* 0.1 (saw freq))) ;(* 0.1 here means 10% power
;(baz)

;(stop)

;(demo (sin-osc 440))
;(odoc *)
;(odoc +)
;(odoc sin-osc)
;(odoc kick)
;(odoc odoc)

(definst sound_1 [freq 220] (saw freq))
;(sound_1 220)
;(stop)

;kicks
;\ep need at first line
;(odoc *)
;(odoc perc) ;beating sound
;(odoc compander) ;makes high range sounds be able to play on speaker
;(odoc drum)

(definst kick [freq 120 dur 0.3 width 0.5]
    (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
                  env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
                  sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
                  src (sin-osc freq-env)
                  drum (+ sqr (* env src))]
          (compander drum drum 0.2 1 0.1 0.01 0.01)))
;(kick)


;metro
;(odoc metronome)
;(odoc env-gen)

(defsynth kick [amp 0.5 decay 0.6 freq 65] 
    (let [env (env-gen (perc 0 decay) 1 1 0 1 FREE)
                  snd (sin-osc freq (* Math/PI 0.5) amp)]
          (out 0 (pan2 (* snd env) 0))))


(definst kick [amp 0.5 decay 0.6 freq 65] 
    (* (sin-osc freq (* Math/PI 1.5) amp)
            (env-gen (perc 0 decay) 1 1 0 1 FREE)))
;(kick)
;(:sdef kick) 


(definst quux [freq 440] (* 0.3 (saw freq)))
;(quux)
;(ctl quux :freq 660)
;(stop)


(definst trem [freq 440 depth 10 rate 6 length 10]
  (* 0.3
    (line:kr 0 1 length FREE)
    (saw (+ freq (* depth (sin-osc:kr rate))))))
;(trem)
;(stop)

(definst sin-wave [freq 440 attack 0.01 decay 0.6 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (perc 0 decay) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))
(odoc env-gen)

(sin-wave)
